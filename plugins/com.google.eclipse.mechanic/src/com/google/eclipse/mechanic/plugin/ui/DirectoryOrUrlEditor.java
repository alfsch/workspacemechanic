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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.swt.widgets.Composite;

import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

/**
 * An editor that lets the user select either a URL resource or
 * directory path. Adding a path uses a filesystem-aware
 * dialog box while the URL editor is a plain text box.
 */
public class DirectoryOrUrlEditor extends PathEditor {

  public DirectoryOrUrlEditor(String name, String labelText,
      String dirChooserLabelText, Composite parent) {
    super(name, labelText, dirChooserLabelText, parent);
  }


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
    String[] array = super.parseString(stringList);
    for (int i = 0; i < array.length; i++) {
      array[i] = MechanicPreferences.doVariableSubstitution(array[i]);
    }
    return array;
  }

  @Override
  protected String createList(String[] items) {
    StringBuilder path = new StringBuilder("");
    String pathSeparator = "";
    for (String item : items) {
      if (item != null) {
        path.append(pathSeparator);
        path.append(item);
        pathSeparator = File.pathSeparator;
      }
    }
    return path.toString();
  }
}
