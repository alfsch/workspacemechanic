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

import com.google.eclipse.mechanic.internal.ResourceTaskProviderParser;

/**
 * An editor that lets the user select either a URL resource or
 * directory path. Adding a path uses a filesystem-aware
 * dialog box while the URL editor is a plain text box.
 */
public class DirectoryOrUrlEditor extends ListEditor {

  private static final ResourceTaskProviderParser parser = new ResourceTaskProviderParser();

  public DirectoryOrUrlEditor(String name, String labelText, Composite parent) {
    init(name, labelText);
    if (parent != null) {
      // For testing.
      createControl(parent);
    }
  }


  @Override
  protected String getNewInputObject() {
    TaskResourceDialog dialog = new TaskResourceDialog(getShell(), true);
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
