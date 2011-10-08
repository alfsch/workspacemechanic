/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.core.keybinding.KbaBootstrapper;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * The dialog to obtain properties for outputting a .kbd task file.
 */
public class KeybindingsOutputDialog extends BaseOutputDialog {
  /**
   * Create an output dialog for the given shell.
   * 
   * @param parentShell the shell to create this dialog in.
   */
  public KeybindingsOutputDialog(Shell parentShell) {
    super(parentShell, "kbd");
  }

  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText("Export Keyboard Bindings");
  }

  @Override
  protected void okPressed() {
    Preconditions.checkState(isValid());

    IPath location = getValidOutputLocation();
    if (location == null) {
      return;
    }

    try {
      new KbaBootstrapper().evaluate(location);
      super.okPressed(); // Closes the dialog and returns an OK result
    } catch (IOException e) {
      MechanicLog.getDefault().logError(e, "Error while writing %s", location);
      MessageDialog.openError(super.getParentShell(), "Unable to write Keyboard bindings file.",
          e.getMessage());
    }
  }
}
