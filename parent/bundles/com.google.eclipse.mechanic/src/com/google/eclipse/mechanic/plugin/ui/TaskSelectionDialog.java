/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.util.Collection;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.dialogs.ListDialog;

import com.google.eclipse.mechanic.MechanicService;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * Allows the user to pick a single Task from a list of Tasks.
 * 
 * TODO(smckay): We should pretty this up to use a view similar to the
 * MechanicDialog.
 */
public final class TaskSelectionDialog extends ListDialog {

  public TaskSelectionDialog(Shell parent, String title,
      Collection<Task> items) {
    super(parent);
    setInput(items);
    setContentProvider(ArrayContentProvider.getInstance());
    setLabelProvider(new DefaultLabelProvider());
    setWidthInChars(80);
    setTitle(title);
  }

  @Override
  protected int getTableStyle() {
    return SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION;
  }


  private TableColumn newTableColumn(Table table, String name, int width) {
    TableColumn tc = new TableColumn(table, SWT.NONE, 0);
    tc.setResizable(true);
    tc.setText(name);
    tc.setWidth(width);
    return tc;
  }

  @Override
  protected Control createDialogArea(Composite container) {
    Control area = super.createDialogArea(container);
    TableViewer tableViewer = getTableViewer();
    Table table = tableViewer.getTable();
    tableViewer.setLabelProvider(new DefaultCellLabelProvider());
    ColumnViewerToolTipSupport.enableFor(tableViewer, ToolTip.NO_RECREATE); 
    table.setHeaderVisible(true);
    newTableColumn(table, "Description", 200);
    newTableColumn(table, "ID", 200);
    newTableColumn(table, "Name", 200);
    tableViewer.refresh();
    return area;
  }

  private static class DefaultCellLabelProvider extends CellLabelProvider {
    @Override
    public void update(ViewerCell cell) {
      Object element = cell.getElement();
      int columnIndex = cell.getColumnIndex();
      cell.setText(getColumnText((Task) element, columnIndex));
    }

    @Override
    public String getToolTipText(Object element) {
      Task item = (Task) element;
      return item.getTitle() + "\n" + item.getDescription() + "\n\nID:" + item.getId();
    }

    public String getColumnText(Task item, int columnIndex) {
      switch(columnIndex) {
      case 0:
        return item.getTitle();
      case 1:
        return item.getId();
      case 2:
        return item.getDescription();
      default:
        return "" + columnIndex;
      }
    }
  }

  @Override
  protected void createButtonsForButtonBar(final Composite parent) {
    createButton(parent, IDialogConstants.PROCEED_ID, "Repair all tasks", true);
    super.createButtonsForButtonBar(parent);
  }

  @Override
  protected void buttonPressed(final int buttonId) {
    super.buttonPressed(buttonId);
    if (buttonId == IDialogConstants.PROCEED_ID) {
      MechanicLog.getDefault().logInfo("Repairing all tasks");
      for (final Task task : MechanicService.getInstance().getAllKnownTasks()) {
        task.getRepairAction().run();
      }
      okPressed();
    }
  }
	
  /**
   * Serializes Tasks for use in user facing dialogs.
   */
  private static class DefaultLabelProvider extends LabelProvider implements ITableLabelProvider {

    @Override
    public boolean isLabelProperty(Object element, String property) {
      return false;
    }

    public Image getColumnImage(Object element, int columnIndex) {
      return null;
    }

    public String getColumnText(Object element, int columnIndex) {
      Task item = ((Task) element);
      switch(columnIndex) {
      case 0:
        return item.getTitle();
      case 1:
        return item.getId();
      case 2:
        return item.getDescription();
      default:
        return "" + columnIndex;
      }
    }
  }
}
