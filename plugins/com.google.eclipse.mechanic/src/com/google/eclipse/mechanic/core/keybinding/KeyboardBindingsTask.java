/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.base.Objects;
import com.google.eclipse.mechanic.CompositeTask;
import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

import org.eclipse.core.commands.Command;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.keys.IBindingService;

import java.io.IOException;

/**
 * Configures keyboard preferences for an audit.
 *
 * @author zorzella@google.com
 * @author konigsberg@google.com
 */
class KeyboardBindingsTask extends CompositeTask {

  // TODO: temporarily disabled -- this is still under dev
  private final boolean enabled;
  private final MechanicLog log;
  private final IWorkbench workbench;
  private final ICommandService commandService;
  private final IBindingService bindingService;
  private final KeyBindingsAudit audit;

  public KeyboardBindingsTask(KeyBindingsAudit audit) {
    this(
        System.getProperty("KEYBOARD_MECHANIC_ENABLED", "false").equals("true"),
        MechanicLog.getDefault(),
        PlatformUI.getWorkbench(),
        (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class),
        (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class),
        audit);
  }
  
  KeyboardBindingsTask(
      boolean enabled,
      MechanicLog log,
      IWorkbench workbench,
      ICommandService commandService,
      IBindingService bindingService,
      KeyBindingsAudit audit) {
    this.enabled = enabled;
    this.log = log;
    this.workbench = workbench;
    this.commandService = commandService;
    this.bindingService = bindingService;
    this.audit = Util.checkNotNull(audit);
  }

  public String getDescription() {
    return this.audit.getMetadata().description;
  }

  public String getTitle() {
    return "Keyboard binding fixes: " + this.audit.getMetadata().shortDescription;
  }

  public boolean evaluate() {
    if (!enabled) {
      return true;
    }

    boolean dirty = false;
    // If "dirty" is set to true, it means we made some modification that
    // we still need to persist.
    for(KbaChangeSet changeSet : audit.getKeyBindingsChangeSets()) {
      dirty = dirty || doEvaluate(changeSet).isDirty;
    }
    
    return !dirty;
  }

  private static final class EvaluationResult {

    private final boolean isDirty;
    private final Scheme scheme;
    private final KeyBindings keyBindings;

    public EvaluationResult(
        final boolean isDirty,
        final Scheme scheme,
        final KeyBindings keyBindings) {
      this.isDirty = isDirty;
      this.scheme = scheme;
      this.keyBindings = keyBindings;
    }
  }
  
  private EvaluationResult doEvaluate(
      final KbaChangeSet changeSet) {

    boolean dirty = false;
    
    final KeyBindings bindings = new KeyBindings(bindingService.getBindings());

    final Scheme scheme = bindingService.getScheme(changeSet.getSchemeId());

    for (KbaBinding toAdd : changeSet.getBindingList()) {
      Command commandToAdd = commandService.getCommand(toAdd.getCid());
      if (!commandToAdd.isDefined()) {
        log.logWarning("Command '" + toAdd.getCid() + "' does not exist.");
        continue;
      }
      KeySequence triggerSequence;
      try {
        triggerSequence = KeySequence.getInstance(toAdd.getKeySequence());
      } catch (ParseException e) {
        //TODO(zorzella): how to issue an error to the user that there was an
        //invalid key sequence?
        throw new RuntimeException(e);
      }

      if (bindings.addIfNotPresent(
          scheme, 
          changeSet.getPlatform(), 
          changeSet.getContextId(), 
          triggerSequence, 
          commandToAdd, 
          toAdd.getParameters())) {
        dirty = true;
      }
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
      if (bindings.removeBindingIfPresent(
          scheme, 
          changeSet.getPlatform(), 
          changeSet.getContextId(), 
          triggerSequence, 
          commandToRemove, 
          toRemove.getParameters())) {
        dirty = true;
      }
    }

    return new EvaluationResult(dirty, scheme, bindings);
  }

  public void run() {
    if (!enabled) {
      return;
    }
    
    for(KbaChangeSet changeSet : audit.getKeyBindingsChangeSets()) {
      final EvaluationResult result = doEvaluate(changeSet);
      // If there was any modification, persist it
      if (result.isDirty) {
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
