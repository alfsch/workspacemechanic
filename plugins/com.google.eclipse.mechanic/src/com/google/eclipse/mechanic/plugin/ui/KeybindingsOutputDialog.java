/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import org.eclipse.swt.widgets.Shell;

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
    shell.setText("Export Keyboard Preferences");
  }

  @Override
  protected void okPressed() {
    if (!isReady()) {
      return; // Should never happen, since we disable OK when not ready
    }

    writeEpfFile();
  }

  private void writeEpfFile() {
    
  }
}
