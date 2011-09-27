// Copyright 2011 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.collect.Multimap;
import com.google.eclipse.mechanic.CompositeTask;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

import org.eclipse.jface.bindings.Binding;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

final class KbaBoostrapper extends CompositeTask {

  private final IBindingService bindingService;
  private final MechanicLog log;
  private final File file;

  KbaBoostrapper(final File file) {
    this(MechanicLog.getDefault(),
        (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class),
        file);
  }
  
  KbaBoostrapper(
      final MechanicLog log,
      final IBindingService bindingService,
      final File file) {
    this.log = log;
    this.bindingService = bindingService;
    this.file = file;
  }


  public String getTitle() {
    return "Keyboard auditor bootstrap task";
  }

  public String getDescription() {
    return
        "This task will never trigger a 'fix' -- it just assists in bootstrapping the Keyboard Bindings Audit task. See TODO";
  }

  public boolean evaluate() {
    List<Binding> ub = new ArrayList<Binding>();
    List<Binding> sb = new ArrayList<Binding>();

    for (Binding binding : bindingService.getBindings()) {
      if (binding.getType() == Binding.USER) {
        ub.add(binding);
      } else if (binding.getType() == Binding.SYSTEM) {
        sb.add(binding);
      } else {
        throw new UnsupportedOperationException("Unexpected binding type: " + binding.getType());
      }
    }
    Multimap<KbaChangeSetQualifier, Binding> userBindingsMap = KeyBindings.buildQualifierToBindingMap(ub);
    Multimap<KbaChangeSetQualifier, Binding> systemBindingsMap = KeyBindings.buildQualifierToBindingMap(sb);

    new KeyBindingsManualFormatter(log, userBindingsMap, systemBindingsMap).dumpBindingsToFile();

    file.delete();
    
    return true;
  }

  public void run() {}
}