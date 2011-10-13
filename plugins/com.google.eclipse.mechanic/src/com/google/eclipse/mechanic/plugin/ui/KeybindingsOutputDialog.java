/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
    super(parentShell, "kbd", Component.DESCRIPTION);
  }

  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText("Export Keyboard Bindings");
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Control area = super.createDialogArea(parent);
    setDescription("Keyboard bindings for " + System.getProperty("user.name"));
    setLocation(tempDir().getAbsolutePath());
    return area;
  }
  
  @Override
  protected String getDescriptionFieldLabel() {
    return "Description (no quotes or backslashes):";
  }

  @Override
  protected boolean isValid() {
    if (!super.isValid()) {
      return false;
    }
    if (getDescription().contains("'") || getDescription().contains("\\")) {
      return false;
    }
    return true;
  }
  
  private static File tempDir() {
    String dirName = System.getProperty("java.io.tmpdir");
    File file = new File(dirName, "workspace-mechanic-bindings.kbd");
    return file;
  }

  @Override
  protected void okPressed() {
    Preconditions.checkState(isValid());

    IPath location = getValidOutputLocation();
    if (location == null) {
      return;
    }

    try {
      new KbaBootstrapper().evaluate(
          location,
          getDescription());
      super.okPressed(); // Closes the dialog and returns an OK result
    } catch (IOException e) {
      MechanicLog.getDefault().logError(e, "Error while writing %s", location);
      MessageDialog.openError(super.getParentShell(), "Unable to write Keyboard bindings file.",
          e.getMessage());
    }
  }

  @Override
  protected String getDialogSettingsSection() {
    return "KeyboardBindingsOuptutSetting";
  }
}
