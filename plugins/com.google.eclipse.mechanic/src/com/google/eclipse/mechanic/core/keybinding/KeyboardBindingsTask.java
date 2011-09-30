/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.CompositeTask;
import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.keys.IBindingService;

import java.io.IOException;
import java.util.Set;

/**
 * Configures keyboard preferences for an audit.
 *
 * @author zorzella@google.com
 * @author konigsberg@google.com
 */
class KeyboardBindingsTask extends CompositeTask {

  public static final String KBA_ENABLE_EXPERIMENTAL_REMOVE_PROP_NAME = "KBA_ENABLE_EXPERIMENTAL_REMOVE";

  static final boolean ENABLE_EXP_REM() {
    return System.getProperty(KBA_ENABLE_EXPERIMENTAL_REMOVE_PROP_NAME, "false").equals("true");
  }
  
  private final MechanicLog log;
  private final IWorkbench workbench;
  private final ICommandService commandService;
  private final IBindingService bindingService;
  private final KeyBindingsAudit audit;

  public KeyboardBindingsTask(KeyBindingsAudit audit) {
    this(
        MechanicLog.getDefault(),
        PlatformUI.getWorkbench(),
        (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class),
        (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class),
        audit);
  }
  
  KeyboardBindingsTask(
      MechanicLog log,
      IWorkbench workbench,
      ICommandService commandService,
      IBindingService bindingService,
      KeyBindingsAudit audit) {
    this.log = log;
    this.workbench = workbench;
    this.commandService = commandService;
    this.bindingService = bindingService;
    this.audit = Util.checkNotNull(audit);
  }

  public String getDescription() {
    Set<String> addedBindings = Sets.newHashSet();
    for(KbaChangeSet changeSet : audit.getKeyBindingsChangeSets()) {
      Function<Binding, String> function = new Function<Binding, String>() {
        
        public String apply(Binding b) {
          try {
            return b.getTriggerSequence().format() + " : " + b.getParameterizedCommand().getName();
          } catch (NotDefinedException e) {
            throw new RuntimeException(e);
          }
        }
      };
      addedBindings.addAll(Lists.newArrayList(
          Iterables.transform(doEvaluate(changeSet).keyBindings.addedBindings, function)));
    }
    
    return "Add these bindings:\n" +
    		"\n" +
        Joiner.on("\n").join(addedBindings);
  }

  public String getTitle() {
    return "Keyboard binding fixes: " + this.audit.getMetadata().description;
  }

  public boolean evaluate() {
    boolean dirty = false;
    // If "dirty" is set to true, it means we made some modification that
    // we still need to persist.
    for(KbaChangeSet changeSet : audit.getKeyBindingsChangeSets()) {
      dirty = dirty || doEvaluate(changeSet).keyBindings.isDirty();
    }
    
    return !dirty;
  }

  private static final class EvaluationResult {

    private final Scheme scheme;
    private final KeyBindings keyBindings;
    

    public EvaluationResult(
        final Scheme scheme,
        final KeyBindings keyBindings) {
      this.scheme = scheme;
      this.keyBindings = keyBindings;
    }
  }
  
  private EvaluationResult doEvaluate(
      final KbaChangeSet changeSet) {

    final KeyBindings bindings = new KeyBindings(bindingService.getBindings());

    final Scheme scheme = bindingService.getScheme(changeSet.getSchemeId());

    for (KbaBinding toAdd : changeSet.getBindingList()) {
      Command commandToAdd = commandService.getCommand(toAdd.getCid());
      if (!commandToAdd.isDefined()) {
        log.logWarning("Command '" + toAdd.getCid() + "' does not exist. Skipping.");
        continue;
      }
      ParameterizedCommand parameterizedCommandToAdd =
          ParameterizedCommand.generateCommand(commandToAdd, toAdd.getParameters());

      KeySequence triggerSequence;
      try {
        triggerSequence = KeySequence.getInstance(toAdd.getKeySequence());
      } catch (ParseException e) {
        //TODO(zorzella): how to issue an error to the user that there was an
        //invalid key sequence?
        throw new RuntimeException(e);
      }

      bindings.addIfNotPresent(
          scheme, 
          changeSet.getPlatform(), 
          changeSet.getContextId(), 
          triggerSequence,
          parameterizedCommandToAdd);
    }

    // for (KeyBindingSpec toRemove : changeSet.toRemove()) {
    if (false) {
      KbaBinding toRemove = null;
      // TODO(zorzella): removing command is currently totally broken. This code
      // was written with the idea that we would have a cid to work with, and
      // the JSON parsing was written with the idea that "rem"s would not take a
      // command. We should likely support both: if "rem" has a command, we'd
      // specifically look for it. If it does not, we should look for an Eclipse
      // (system) keybinding for that.
      Command commandToRemove;
      try {
        commandToRemove = commandService.getCommand(toRemove.getCid());
      } catch (RuntimeException e) {
        //TODO(zorzella): how to issue an error to the user that there was an
        //invalid commmand?
        throw e;
      }
      KeySequence triggerSequence;
      try {
        triggerSequence = KeySequence.getInstance(toRemove.getKeySequence());
      } catch (ParseException e) {
        //TODO(zorzella): how to issue an error to the user that there was an
        //invalid key sequence?
        throw new RuntimeException(e);
      }
      bindings.removeBindingIfPresent(
          scheme, 
          changeSet.getPlatform(), 
          changeSet.getContextId(), 
          triggerSequence, 
          commandToRemove, 
          toRemove.getParameters());
    }

    return new EvaluationResult(scheme, bindings);
  }

  public void run() {
    for(KbaChangeSet changeSet : audit.getKeyBindingsChangeSets()) {
      final EvaluationResult result = doEvaluate(changeSet);
      // If there was any modification, persist it
      if (result.keyBindings.isDirty()) {
        workbench.getDisplay().syncExec(new Runnable() {
          public void run() {
            try {
              bindingService.savePreferences(result.scheme, result.keyBindings.toArray());
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });
      }
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KeyboardBindingsTask)) {
      return false;
    }
    KeyboardBindingsTask that = (KeyboardBindingsTask)obj;
    return Objects.equal(this.audit, that.audit);
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(this.audit);
  }
}
