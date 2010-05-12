/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import com.google.eclipse.mechanic.Task;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

import java.util.List;

/**
 * Allows the user to pick a single Task from a list of Tasks.
 * 
 * TODO(smckay): We should pretty this up to use a view similar to the
 * MechanicDialog.
 * 
 * @author smckay@google.com (Steve McKay)
 */
public final class TaskSelectionDialog extends ListDialog {

  public TaskSelectionDialog(Shell parent, List<Task> items) {
    super(parent);
    setInput(items);
    setContentProvider(new ContentProvider());
    setLabelProvider(new LabelProvider());
    setTitle("Select a Task to block");
  }

  /**
   * Provides access to a list of Tasks.
   */
  private static class ContentProvider implements IStructuredContentProvider {

    @SuppressWarnings("unchecked")
    public Object[] getElements(Object input) {
      return ((List<Task>) input).toArray();
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
    public void dispose() {}
  }

  /**
   * Serializes Tasks for use in user facing dialogs.
   */
  private static class LabelProvider implements ILabelProvider {

    public String getText(Object element) {
      Task item = ((Task) element);
      return String.format("%s (%s)", item.getTitle(), item.getId());
    }

    public boolean isLabelProperty(Object element, String property) {
      return false;
    }

    public Image getImage(Object element) {
      return null;
    }

    public void dispose() {}
    public void addListener(ILabelProviderListener listener) {}
    public void removeListener(ILabelProviderListener listener) {}    
  }
}
