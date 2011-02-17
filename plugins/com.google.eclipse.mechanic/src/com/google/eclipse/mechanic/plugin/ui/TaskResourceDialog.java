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

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog for specifying the location of a task.
 */
public class TaskResourceDialog extends InputDialog {

  private static final int ID_BROWSE_BUTTON = 100;

  public TaskResourceDialog(Shell shell, boolean allowUrls) {
    super(shell, "Task source",
        (allowUrls ? "Enter a local directory or URL" : "Enter a local directory"),
        "", new TaskResourceValidator(allowUrls));
  }

  @Override
  protected void buttonPressed(int buttonId) {
    if (buttonId == ID_BROWSE_BUTTON) {
      getFromDirectory();
    } else {
      super.buttonPressed(buttonId);
    }
  }

  private void getFromDirectory() {
    DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
    dialog.setMessage("Task source directories");

    String currentValue = getText().getText();
    if (currentValue != null && new File(currentValue).isDirectory()) {
      dialog.setFilterPath(currentValue);
    }
    String newValue = dialog.open();
    if (newValue != null) {
      getText().setText(newValue);
    }
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, ID_BROWSE_BUTTON,
        "Browse Filesystem...",
        false);
    super.createButtonsForButtonBar(parent);
  }


  @Override
  protected Control createDialogArea(Composite parent) {
    Control dialogArea = super.createDialogArea(parent);

    return dialogArea;
  }

  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    TaskResourceDialog urlDialog = new TaskResourceDialog(shell, true);
    urlDialog.open();
    while (display.readAndDispatch()) {
      display.sleep();
    }
  }
}
