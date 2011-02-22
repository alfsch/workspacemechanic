/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;

import com.google.eclipse.mechanic.internal.TaskSourceParser;

/**
 * An editor that lets the user select either a URL resource or
 * directory path. Adding a path uses a filesystem-aware
 * dialog box while the URL editor is a plain text box.
 */
public class DirectoryOrUrlEditor extends ListEditor {

  private static final TaskSourceParser parser = new TaskSourceParser();

  public DirectoryOrUrlEditor(String name, String labelText, Composite parent) {
    init(name, labelText);
    if (parent != null) {
      // For testing.
      createControl(parent);
    }
  }

  // TODO(konigsberg): When the set of task directories is empty
  // and Add... is pressed, default to ${home}/.eclipse/mechanic perhaps.

  @Override
  protected String getNewInputObject() {
    // Important: this "false" means the dialog doesn't permit URLs.
    // Until this editor can properly store URLs along with paths,
    // this should remain off.
    // (Case in point, Windows uses ':' as the path separator. Do the math.)
    TaskResourceDialog dialog = new TaskResourceDialog(getShell(), false);
    return (dialog.open() == Dialog.OK) ? dialog.getValue() : null;
  }

  @Override
  protected String[] parseString(String stringList) {
    return parser.parse(stringList);
  }

  @Override
  protected String createList(String[] items) {
    return parser.unparse(items);
  }
}
