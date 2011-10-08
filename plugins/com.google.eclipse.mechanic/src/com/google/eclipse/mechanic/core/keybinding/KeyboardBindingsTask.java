/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.keys.IBindingService;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.CompositeTask;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * Configures keyboard preferences for a task.
 *
 * @author zorzella@google.com
 * @author konigsberg@google.com
 */
class KeyboardBindingsTask extends CompositeTask {

  public static final String KBA_ENABLE_REMOVE_PROP_NAME = "KBA_ENABLE_REMOVE";

  static final boolean ENABLE_EXP_REM() {
    return System.getProperty(KBA_ENABLE_REMOVE_PROP_NAME, "true").equals("true");
  }

  private final MechanicLog log;
  private final IWorkbench workbench;
  private final ICommandService commandService;
  private final IBindingService bindingService;
  private final KeyBindingsTask task;

  public KeyboardBindingsTask(KeyBindingsTask task) {
    this(
        MechanicLog.getDefault(),
        PlatformUI.getWorkbench(),
        (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class),
        (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class),
        task);
  }
  
  KeyboardBindingsTask(
      MechanicLog log,
      IWorkbench workbench,
      ICommandService commandService,
      IBindingService bindingService,
      KeyBindingsTask task) {
    this.log = log;
    this.workbench = workbench;
    this.commandService = commandService;
    this.bindingService = bindingService;
    this.task = Preconditions.checkNotNull(task);
  }

  public String getDescription() {
    Set<String> addedBindings = calculateReadableAddedBindings(Action.ADD);
    Set<String> removedBindings = calculateReadableAddedBindings(Action.REMOVE);
    
    StringBuilder result = new StringBuilder();
    
    if (addedBindings.size() > 0) {
      result.append("Add these bindings:\n" +
    		"\n" +
        Joiner.on("\n").join(addedBindings) + "\n\n");
    }
    
    if (removedBindings.size() > 0) {
      result.append("Remove these bindings:\n" +
          "\n" +
          Joiner.on("\n").join(removedBindings) + "\n\n");
    }

    // TODO assert added or removed > 0?

    return result.toString();
  }

  private final Function<Binding, String> bindingToReadableStringTransformFunction = new Function<Binding, String>() {
    
    public String apply(Binding b) {
      try {
        return b.getTriggerSequence().format() + " : " + b.getParameterizedCommand().getName();
      } catch (NotDefinedException e) {
        log.logError(e);
        throw new RuntimeException(e);
      } catch (RuntimeException e) {
        log.logError(e);
        throw e;
      }
    }
  };
  
  private Set<String> calculateReadableAddedBindings(Action action) {
    Set<String> result = Sets.newHashSet();
    for(KbaChangeSet changeSet : task.getKeyBindingsChangeSetsWith(action)) {
      result.addAll(Lists.newArrayList(
          Iterables.transform(doEvaluate(changeSet).keyBindings.addedBindings, bindingToReadableStringTransformFunction)));
      result.addAll(Lists.newArrayList(
          Iterables.transform(doEvaluate(changeSet).keyBindings.removedBindings, bindingToReadableStringTransformFunction)));
    }
    return result;
  }

  public String getTitle() {
    return "Keyboard binding fixes: " + this.task.getMetadata().description;
  }

  public boolean evaluate() {
    boolean dirty = false;
    // If "dirty" is set to true, it means we made some modification that
    // we still need to persist.
    for(KbaChangeSet changeSet : task.getKeyBindingsChangeSets()) {
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

    switch (changeSet.getAction()) {
    case ADD:
      modifyBindingsForAddChangeSet(changeSet, bindings, scheme);
      break;
    case REMOVE:
      modifyBindingsForRemoveChangeSet(changeSet, bindings, scheme);
      break;
    default:
      throw new UnsupportedOperationException();  
    }

    return new EvaluationResult(scheme, bindings);
  }

  private void modifyBindingsForRemoveChangeSet(final KbaChangeSet changeSet,
      final KeyBindings bindings, final Scheme scheme) {
    if (!ENABLE_EXP_REM()) {
      return;
    }
    for (KbaBinding toRemove : changeSet.getBindingList()) {
      Command commandToRemove;
      try {
        commandToRemove = commandService.getCommand(toRemove.getCid());
      } catch (RuntimeException e) {
        log.logError(e);
        throw e;
      }
      KeySequence triggerSequence;
      try {
        triggerSequence = KeySequence.getInstance(toRemove.getKeySequence());
      } catch (ParseException e) {
        log.logError(e, "Invalid key sequence: %s", toRemove.getKeySequence());
        throw new RuntimeException(e);
      }
      // Removing a system binding means one of:
      // 1. if it's a user binding, remove it
      // 2. if it's a system binding, create a null-command user binding doppleganger
      bindings.removeBindingIfPresent(
          scheme, 
          changeSet.getPlatform(), 
          changeSet.getContextId(), 
          triggerSequence, 
          commandToRemove, 
          toRemove.getParameters());
      // If our remove binding is against the "null" platform, it should apply
      // to all platforms. The only one that matters is the current platform
      if (changeSet.getPlatform() == null) {
        bindings.removeBindingIfPresent(
            scheme, 
            SWT.getPlatform(), 
            changeSet.getContextId(), 
            triggerSequence, 
            commandToRemove, 
            toRemove.getParameters());
      }
    }
  }

  private void modifyBindingsForAddChangeSet(final KbaChangeSet changeSet,
      final KeyBindings bindings, final Scheme scheme) {
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
        log.logError(e, "Invalid key sequence: %s", toAdd.getKeySequence());
        throw new RuntimeException(e);
      }

      bindings.addIfNotPresent(
          scheme, 
          changeSet.getPlatform(), 
          changeSet.getContextId(), 
          triggerSequence,
          parameterizedCommandToAdd);
    }
  }

  public void run() {
    for(KbaChangeSet changeSet : task.getKeyBindingsChangeSets()) {
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
    return Objects.equal(this.task, that.task);
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(this.task);
  }

  @Override
  public String toString() {
    return task.toString();
  }
}
