/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.eclipse.mechanic.CompositeTask;
import com.google.eclipse.mechanic.internal.Util;

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
public class KeyboardBindingsTask extends CompositeTask {

  // TODO: temporary, which still under dev
  private static final boolean ENABLED = 
    System.getProperty("KEYBOARD_MECHANIC_ENABLED", "false").equals("true");

  private final KeyBindingsModel model;

  public KeyboardBindingsTask(KeyBindingsModel model) {
    this.model = Util.checkNotNull(model);
  }

  public String getDescription() {
    return "Ensures certain keybindings exist, and ensures that others don't.";
  }

  public String getTitle() {
    return "Keyboard diagnotics";
  }

  public boolean evaluate() {
    if (!ENABLED) {
      return true;
    }

    //TODO(zorzella): can we get workbench and commandService once, rather than
    //at every evaluate?
    IWorkbench workbench = PlatformUI.getWorkbench();
    ICommandService commandService = (ICommandService) workbench.getService(ICommandService.class);
    final IBindingService bindingService =
        (IBindingService) workbench.getService(IBindingService.class);

    boolean dirty = false;
    // If "dirty" is set to true, it means we made some modification that
    // we still need to persist.
    for(KeyBindingChangeSet changeSet : model.getKeyBindingsChangeSets()) {
      dirty = dirty || doEvaluate(workbench, commandService, bindingService, changeSet);
    }
    
    return !dirty;
  }

  private boolean doEvaluate(
      IWorkbench workbench, 
      ICommandService commandService,
      final IBindingService bindingService, 
      KeyBindingChangeSet changes) {

    boolean dirty = false;
    
    final KeyBindings bindings = new KeyBindings(bindingService.getBindings());

    final Scheme scheme = bindingService.getScheme(changes.getSchemeId());

    for (KeyBindingSpec toAdd : changes.toAdd()) {
      Command commandToAdd = commandService.getCommand(toAdd.getCid());
      //TODO(zorzella): if command == null, log and ignore
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
          changes.getPlatform(), 
          changes.getContextId(), 
          triggerSequence, 
          commandToAdd, 
          toAdd.getParameters())) {
        dirty = true;
      }
    }

    for (KeyBindingSpec toRemove : changes.toRemove()) {
      Command commandToRemove = commandService.getCommand(toRemove.getCid());
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
          changes.getPlatform(), 
          changes.getContextId(), 
          triggerSequence, 
          commandToRemove, 
          toRemove.getParameters())) {
        dirty = true;
      }
    }

    //TODO(zorzella): this last step is the repair action, and should be called
    //in the run method instead.
    
    // If there was any modification, persist it
    if (dirty) {
      workbench.getDisplay().syncExec(new Runnable() {
        public void run() {
          try {
            bindingService.savePreferences(scheme, bindings.toArray());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      });
    }
    return dirty;
  }

  public void run() {
    if (!ENABLED) {
      return;
    }
    // TODO(zorzella): fix
  }
}
