/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.KbaBindingList;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsManualFormatter.BindingType;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * Dumps existing keyboard bindings to a bootstrap file.
 */
public final class KbaBootstrapper {

  private final IBindingService bindingService;
  private final MechanicLog log;
  private final String currentPlatform;

  public KbaBootstrapper() {
    this(MechanicLog.getDefault(),
        (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class),
        SWT.getPlatform());
  }
  
  KbaBootstrapper(
      final MechanicLog log,
      final IBindingService bindingService,
      final String currentPlatform) {
    this.log = log;
    this.bindingService = bindingService;
    this.currentPlatform = currentPlatform;
  }

  private static final Predicate<EclBinding> ACCEPT_SYSTEM_BINDINGS_FILTER = new Predicate<EclBinding>() {
    public boolean apply(EclBinding b) {
      if (b.getType() != BindingType.SYSTEM) {
        return false;
      }
      if (!b.hasCommand()) {
        // Uh? I have no idea what these mean, but they exist -- they are system
        // bindings to no command.
        return false;
      }      
      return true;
    }
  };

  private static final Predicate<EclBinding> ACCEPT_USER_BINDINGS_FILTER = new Predicate<EclBinding>() {
    public boolean apply(EclBinding b) {
      if (b.getType() != BindingType.USER) {
        return false;
      }
      if (!b.hasCommand()) {
        // TODO: support removing commands
        if (!KeyboardBindingsTask.ENABLE_EXP_REM()) {
          return false;
        }
      }
      return true;
    }
  };

  public void evaluate(IPath outputLocation,
      String description) throws FileNotFoundException, IOException {
    Iterable<EclBinding> allBindings = EclBinding.from(Arrays.asList(bindingService.getBindings()));

    // Because of the weird way Eclipse stores removed system bindings, we
    // first need to build the entire system bindings map, before we build the
    // user bindings map -- to build the user bindings map, we need an instance
    // of the system bindings map.
    Map<KbaChangeSetQualifier, KbaChangeSet> systemBindings =
        buildSystemBindingsMap(Iterables.filter(allBindings, ACCEPT_SYSTEM_BINDINGS_FILTER));
    
    Map<KbaChangeSetQualifier, KbaChangeSet> userBindings =
        buildUserBindingsMap(currentPlatform, systemBindings,
            Iterables.filter(allBindings, ACCEPT_USER_BINDINGS_FILTER),
            log);
    
    new KeyBindingsManualFormatter(userBindings, systemBindings)
        .dumpBindingsToFile(outputLocation, description);
  }

  private static KbaBinding bindingToRemoveKbaBinding(
      final String currentPlatform,
      final Map<KbaChangeSetQualifier, KbaChangeSet> systemBindingsMap,
      final EclBinding binding,
      final MechanicLog log) {
    if (binding.hasCommand()) {
      throw new IllegalStateException();
    }
    
    final KbaChangeSetQualifier q = binding.with(Action.REMOVE);
        
    KbaBinding doppleGanger = findDoppleganger(q.platform, systemBindingsMap, q, binding);
    if (doppleGanger == null) {
      // Sigh... If the "rem" binding's platform is null, it means apply to all
      // platforms. So, if we can't find a doppleganger for "null", we should
      // also look for a doppleganger with the current platform.
      if (q.platform == null) {
        doppleGanger = findDoppleganger(currentPlatform, systemBindingsMap, q, binding);
      }
      if (doppleGanger == null) {
        log.log(IStatus.ERROR, "doppleganger is null");
      }
    }
    
    return doppleGanger;
  }
  
  /**
   * To remove a system binding, in Eclipse, one creates a user binding with
   * the same scheme/platform/key sequence, and a null command.
   * 
   * <p>This method, given a null-command, rem user binding {@code q/rem}, finds and
   * returns the equivalent system binding.
   */
  private static KbaBinding findDoppleganger(
      String platform,
      Map<KbaChangeSetQualifier, KbaChangeSet> systemBindingsMap,
      KbaChangeSetQualifier q,
      EclBinding rem) {

    KbaChangeSetQualifier equivalentAdd = new KbaChangeSetQualifier(
        q.scheme, platform, q.context, Action.ADD.toString());
    
    KbaChangeSet kbaChangeSet = systemBindingsMap.get(equivalentAdd);
    if (kbaChangeSet == null) {
      // It seems we're trying to remove a system key binding that simply does
      // not exist. Possibly this removing was recorded against an earlier
      // version of Eclipse (when the binding existed). In any case, just return
      // null.
      return null;
    }
    for (KbaBinding binding : kbaChangeSet.getBindingList()) {
      String keySequence = rem.getKeySequence();
      if (binding.getKeySequence().equals(keySequence)) {
        return new KbaBinding(
            binding.getKeySequence(),
            binding.getCid(),
            binding.getParameters());
      }
    }
    return null;
  }

  static Map<KbaChangeSetQualifier, KbaChangeSet> buildUserBindingsMap(
      final String currentPlatform,
      final Map<KbaChangeSetQualifier, KbaChangeSet> systemBindingsMap,
      Iterable<EclBinding> bindingList,
      MechanicLog log) {
    
    // Naughty eclipse's bindings are not uniquefied
    bindingList = Sets.newHashSet(bindingList);
    
    Map<KbaChangeSetQualifier, KbaChangeSet> result = Maps.newHashMap();
    for (final EclBinding b : bindingList) {
      
      if (!b.hasCommand()) {
        if (!KeyboardBindingsTask.ENABLE_EXP_REM()) {
          continue;
        }
        
        KbaBinding kbaBinding = bindingToRemoveKbaBinding(currentPlatform, systemBindingsMap, b, log);
        if (kbaBinding == null) {
          // It seems the user has configured a certain (system) binding to be
          // removed, but no such system binding exists at the moment. I guess
          // it could be that the user configured it in an Eclipse instance
          // where there was a plugin that registered that system binding, and
          // this plugin is not part of this install. We'll just ignore these.
          continue;
        }
        
        KbaChangeSetQualifier q = b.with(Action.REMOVE);
        KbaChangeSet kbaChangeSetForQ = result.get(q);
        if (kbaChangeSetForQ == null) {
          kbaChangeSetForQ = new KbaChangeSet(q, Lists.newArrayList(kbaBinding));
        } else {
          kbaChangeSetForQ = add(kbaChangeSetForQ, kbaBinding);
        }
        
        result.put(q, kbaChangeSetForQ);
        
      } else {
        insertIntoMapAnAddBinding(result, b);
      }
    }
    result = purgeCycles(result);
    return ImmutableMap.copyOf(result);
  }

  /**
   * Take a deep breath: let's say the user removes a system binding; then the
   * user creates the exact same binding again (instead of choosing to "restore"
   * the system binding). What Eclipse does behind the scenes (yuck!) is to
   * create this aforementioned user binding as well as a user binding to remove
   * the system binding.
   * 
   * <p>Spouting those two to the kbd file would cause a cycle -- the mechanic
   * will prompt the user to apply one and the other in alternate passes (if
   * the user chooses to keep applying the fix).
   * 
   * <p>Note that this is <em>not</em> the only way to achieve a cycle, but it
   * is the only way we'll generate a single .kbd with a cycle in it. A cycle
   * can also happen with, say, two .kbd files. But this is the most likely way
   * for the user to make this sort of mistake.
   * 
   * <p>What this method does is to return a modified version of the given
   * {@code source} -- it finds these cycles (where a removed system binding is
   * also present as a user binding) and removes them from the returned result.
   */
  static Map<KbaChangeSetQualifier, KbaChangeSet> purgeCycles(
      final Map<KbaChangeSetQualifier, KbaChangeSet> source) {
    
    final Map<KbaChangeSetQualifier, KbaChangeSet> result = Maps.newHashMap();
    
    for (final KbaChangeSetQualifier q : source.keySet()) {
      final KbaChangeSet sourceKbaChangeSet = source.get(q);
      // For each REMOVE sections...
      if (q.getAction() == Action.REMOVE) {
        final KbaChangeSetQualifier doppleganger =
            new KbaChangeSetQualifier(q.scheme, q.platform, q.context, Action.ADD);
        // ... that have a corresponding ADD section (i.e. same scheme/platform/context),
        // referred to here as doppleganger, ...
        if (source.containsKey(doppleganger)) {
          final List<KbaBinding> toPurge = Lists.newArrayList();
          // ... we iterate over the rem bindings ...
          for (final KbaBinding remBinding: sourceKbaChangeSet.getBindingList()) {
            // ... and try to find an exact match in the doppleganger ...
            if (source.get(doppleganger).getBindingList().contains(remBinding)) {
              // ... if we find one, we mark the original rem binding as a binding
              // to be removed.
              toPurge.add(remBinding);
            }
          }
          // ... finally, after iterating over all the rem bindings, if the
          // list of bindings to purge is not empty ...
          if (!toPurge.isEmpty()) {
            final List<KbaBinding> temp = Lists.newArrayList();
            temp.addAll(sourceKbaChangeSet.getBindingList());
            temp.removeAll(toPurge);
            // ... we create a new KbaChangeSet that is equal to the original one,
            // except with the "toPurge" bindinging removed ...
            KbaChangeSet replacementChangeSet = new KbaChangeSet(q, temp);
            result.put(q, replacementChangeSet);
            // ... and we restart the master loop ...
            continue;
          }
        }
      }
      
      // ... under *all* other circumstances (if q is not a REM, if no doppleganger
      // found, or if no matching add/rem bindings exist), we add the original
      // KbaChangeSet to the result. If no cycles exist (which should be most
      // of the time, this statement will be reached for every iteration in the
      // loop.
      result.put(q, sourceKbaChangeSet);
    }
    return result;
  }

  static Map<KbaChangeSetQualifier, KbaChangeSet> buildSystemBindingsMap(
      Iterable<EclBinding> bindingList) {

    // Naughty eclipse's bindings are not uniquefied
    bindingList = Sets.newHashSet(bindingList);
    
    Map<KbaChangeSetQualifier, KbaChangeSet> result = Maps.newHashMap();
    for (final EclBinding b : bindingList) {
      insertIntoMapAnAddBinding(result, b);
    }
    return ImmutableMap.copyOf(result);
  }

  private static void insertIntoMapAnAddBinding(
      Map<KbaChangeSetQualifier, KbaChangeSet> result, final EclBinding b) {
    KbaChangeSetQualifier q = b.with(Action.ADD);
    KbaBinding kbaBinding = b.toKbaBinding();
    KbaChangeSet kbaChangeSetForQ = result.get(q);
    if (kbaChangeSetForQ == null) {
      kbaChangeSetForQ = new KbaChangeSet(q, Lists.newArrayList(kbaBinding));
    } else {
      kbaChangeSetForQ = add(kbaChangeSetForQ, kbaBinding);
    }
    
    result.put(q, kbaChangeSetForQ);
  }

  private static KbaChangeSet add(KbaChangeSet orig,
      KbaBinding toAddToOrig) {
    KbaBindingList bindingList = new KbaBindingList(concat(orig.getBindingList(), toAddToOrig));
    KbaChangeSet result =
        new KbaChangeSet(orig.getSchemeId(), orig.getPlatform(), orig.getContextId(), orig.getActionLabel(), bindingList);
    return result;
  }

  @SafeVarargs
  private static <T> Iterable<T> concat(ImmutableList<T> list, T... toConcat) {
    List<T> result = Lists.newArrayList(list);
    for (T t : toConcat) {
      // Naughtly Eclipse 
//      if (!result.contains(t)) {
        result.add(t);
//      }
    }
    return result;
  }
  
//  private static <T> Iterable<T> concat(Iterable<T> iterable, T... toConcat) {
//    return Iterables.concat(iterable, Lists.newArrayList(toConcat));
//  }
}