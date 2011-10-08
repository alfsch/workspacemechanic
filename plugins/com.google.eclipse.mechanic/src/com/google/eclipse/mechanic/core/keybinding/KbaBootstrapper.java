// Copyright 2011 Google Inc. All Rights Reserved.

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

  public void evaluate(IPath outputLocation) throws FileNotFoundException, IOException {
    doEvaluate(EclBinding.from(Arrays.asList(bindingService.getBindings())), outputLocation);
  }
  
  public void doEvaluate(final Iterable<EclBinding> allBindings, IPath outputLocation)
      throws FileNotFoundException, IOException {
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
    
    new KeyBindingsManualFormatter(userBindings, systemBindings).dumpBindingsToFile(outputLocation);
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
    return ImmutableMap.copyOf(result);
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