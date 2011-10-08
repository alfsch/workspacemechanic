/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.internal.EpfFileModel;
import com.google.eclipse.mechanic.internal.EpfFileModelWriter;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * The dialog to obtain properties for outputting an EPF file.
 */
public class EpfOutputDialog extends BaseOutputDialog {

  /**
   * Provides labels and colors for cells to the saved preferences table. Handles
   * null values specially to make them visually distinct and easy to read.
   */
  private class EPFOutputLabelProvider extends LabelProvider 
      implements ITableLabelProvider, ITableColorProvider {
    public Image getColumnImage(Object element, int columnIndex) {
      return null;
    }

    public String getColumnText(Object element, int columnIndex) {
      String key = (String) element;
      switch (columnIndex) {
        case 0:
          return key;

        case 1:
          String value = preferences.get(key);
          return value != null ? value : "(null)";

        default:
          return null;
      }
    }

    public Color getBackground(Object element, int columnIndex) {
      return getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
    }

    public Color getForeground(Object element, int columnIndex) {
      String key = (String) element;
      int systemTextColor;
      if (columnIndex == 1 && preferences.get(key) == null) {
        systemTextColor = SWT.COLOR_DARK_GRAY;
      } else {
        systemTextColor = SWT.COLOR_BLACK;
      }
      return getShell().getDisplay().getSystemColor(systemTextColor);
    }
  }

  private final ImmutableMap<String, String> preferences;
  private ITableLabelProvider labelProvider = new EPFOutputLabelProvider();
  private Set<String> selectedKeys;
  private CheckboxTableViewer acceptedPreferences;

  /**
   * Create an output dialog for the given shell.
   * 
   * @param parentShell the shell to create this dialog in.
   * @param preferences the set of preferences that can be added to the
   *        resulting EPF file. The dialog allows the user to select a subset to
   *        export.
   */
  public EpfOutputDialog(Shell parentShell, Map<String, String> preferences) {
    super(parentShell, "epf");
    this.preferences = ImmutableMap.copyOf(preferences);
    this.selectedKeys = Sets.newHashSet(preferences.keySet());
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.SELECT_ALL_ID, "Select All", false);
    createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Deselect All", false);

    super.createButtonsForButtonBar(parent);
  }

  
  @Override
  protected void buttonPressed(int buttonId) {
    super.buttonPressed(buttonId);
    if (buttonId == IDialogConstants.SELECT_ALL_ID) {
      acceptedPreferences.setAllChecked(true);
      selectedKeys.clear();
      selectedKeys.addAll(preferences.keySet());
      validate();
    }
    if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
      acceptedPreferences.setAllChecked(false);
      selectedKeys.clear();
      validate();
    }
  }

  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText("Preference Recorder");
  }

  @Override
  protected void addChildConfiguration(Composite parent) {
    // Add preferences table
    Label savedPreferencesLabel = new Label(parent, SWT.BEGINNING);
    savedPreferencesLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.LEFT, true, false, 3, 1));
    savedPreferencesLabel.setText("Saved Preferences:");

    Composite tableContainer = new Composite(parent, SWT.NONE);
    GridData tableLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
    tableContainer.setLayoutData(tableLayoutData);

    TableColumnLayout columnLayout = new TableColumnLayout();
    tableContainer.setLayout(columnLayout);

    acceptedPreferences =
        CheckboxTableViewer.newCheckList(tableContainer, SWT.SINGLE | SWT.FULL_SELECTION);
    Table acceptedPreferencesTable = acceptedPreferences.getTable();
    acceptedPreferencesTable.setHeaderVisible(true);
    acceptedPreferencesTable.setLinesVisible(true);

    // Setup table columns
    TableColumn keyColumn = new TableColumn(acceptedPreferencesTable, SWT.LEFT);
    keyColumn.setText("Key");
    columnLayout.setColumnData(keyColumn, new ColumnWeightData(1, 300, true));

    TableColumn valueColumn = new TableColumn(acceptedPreferencesTable, SWT.LEFT);
    valueColumn.setText("Value");
    columnLayout.setColumnData(valueColumn, new ColumnWeightData(1, 100, true));

    acceptedPreferences.setLabelProvider(labelProvider);
    acceptedPreferences.setContentProvider(new ArrayContentProvider());
    acceptedPreferences.setInput(preferences.keySet().toArray());

    acceptedPreferences.setAllChecked(true);
    acceptedPreferences.addCheckStateListener(new ICheckStateListener() {
      public void checkStateChanged(CheckStateChangedEvent event) {
        Set<String> newSelectedKeys = new HashSet<String>();
        for (Object obj : acceptedPreferences.getCheckedElements()) {
          newSelectedKeys.add((String) obj);
        }

        selectedKeys = newSelectedKeys;
        validate();
      }
    });

    tableContainer.layout();
  }

  @Override
  protected boolean isReady() {
    return super.isReady() && !selectedKeys.isEmpty();
  }
  
  @Override
  protected void okPressed() {
    if (!isReady()) {
      return; // Should never happen, since we disable OK when not ready
    }

    writeEpfFile();
  }

  private void writeEpfFile() {
    IPath location = getValidOutputLocation();
    if (location == null) {
      return;
    }
    
    // Save the preferences into an EPF file
    EpfFileModel epfFile = new EpfFileModel(getTitle(), getDescription(), getTaskType());
    
    Map<String, String> savedPreferences = getSavedPreferences();
    
    for (Map.Entry<String, String> entry : savedPreferences.entrySet()) {
      epfFile.addElement(entry.getKey(), entry.getValue());
    }

    try {
      EpfFileModelWriter.write(epfFile, location);
      super.okPressed(); // Closes the dialog and returns an OK result
    } catch (IOException e) {
      MechanicLog.getDefault().logError(e, "Error while writing %s", location);
    }
  }

  /**
   * Get the preferences selected by the user to export. Only valid after the
   * dialog has been run.
   */
  private Map<String, String> getSavedPreferences() {
    Map<String, String> result = new HashMap<String, String>();
    for (String key : selectedKeys) {
      result.put(key, preferences.get(key));
    }
    return result;
  }
}
