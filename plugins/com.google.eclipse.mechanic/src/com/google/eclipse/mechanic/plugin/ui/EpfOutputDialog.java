/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.google.eclipse.mechanic.internal.EpfFileModel;
import com.google.eclipse.mechanic.internal.TaskType;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * The dialog to obtain properties for outputting an EPF file.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public class EpfOutputDialog extends Dialog {

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

  private Map<String, String> preferences;
  private String title = "";
  private String description = "";
  private String savedFileLocation = "";
  private int taskType;
  private ITableLabelProvider labelProvider = new EPFOutputLabelProvider();
  private Set<String> selectedKeys;
  private boolean willVerifyOverwrite = true;

  /**
   * Create an output dialog for the given shell.
   * 
   * @param parentShell the shell to create this dialog in.
   * @param preferences the set of preferences that can be added to the
   *        resulting EPF file. The dialog allows the user to select a subset to
   *        export.
   */
  public EpfOutputDialog(Shell parentShell, Map<String, String> preferences) {
    super(parentShell);
    this.preferences = new HashMap<String, String>(preferences);
    this.selectedKeys = preferences.keySet();
  }

  @Override
  protected boolean isResizable() {
    return true;
  }
  
  @Override
  protected Point getInitialSize() {
    Point p =  super.getInitialSize();
    
    return new Point(p.x, p.y * 3 / 2);
  }
  
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    super.createButtonsForButtonBar(parent);
    
    // Initial values for the dialog aren't valid, so set the button to 
    // be disabled initially
    getButton(IDialogConstants.OK_ID).setEnabled(false); 
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    final Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(3, false);
    container.setLayout(layout);

    // Add title field
    Label titleLabel = new Label(container, SWT.BEGINNING);
    titleLabel.setText("Title:");
    final Text titleText = new Text(container, SWT.SINGLE | SWT.BORDER);
    titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    titleText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        title = titleText.getText();
        validate();
      }
    });

    // Add description field
    Label descriptionLabel = new Label(container, SWT.BEGINNING);
    descriptionLabel.setText("Description:");
    final Text descriptionText = new Text(container, SWT.SINGLE | SWT.BORDER);
    descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    descriptionText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        description = descriptionText.getText();
        validate();
      }
    });

    // Add task type field
    Label taskTypeLabel = new Label(container, SWT.BEGINNING);
    taskTypeLabel.setText("Task Type:");
    final Combo taskTypeCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
    taskTypeCombo.setItems(new String[] {"Last Mod", "Reconcile"});
    taskTypeCombo.select(0);
    taskTypeCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
    taskTypeCombo.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        taskType = taskTypeCombo.getSelectionIndex();
        validate();
      }
    });

    // Add saved file location
    Label savedLocationLabel = new Label(container, SWT.BEGINNING);
    savedLocationLabel.setText("Saved File Location:");
    final Text savedLocationText = new Text(container, SWT.SINGLE | SWT.BORDER);
    savedLocationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    savedLocationText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        savedFileLocation = savedLocationText.getText();
        validate();
        willVerifyOverwrite = true;
      }
    });
    Button browseButton = new Button(container, SWT.PUSH);
    browseButton.setText("Browse...");
    browseButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
    browseButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        doFileDialog();
        validate();
      }

      public void widgetDefaultSelected(SelectionEvent e) {
        doFileDialog();
        validate();
      }

      private void doFileDialog() {
        FileDialog fd = new FileDialog(container.getShell(), SWT.SAVE);
        // TODO: initialize dialog with filename. It's not as simple as
        // setFilename() unfortunately.
        fd.setOverwrite(true);
        fd.setFilterExtensions(new String[] { "*.epf" });
        String file = fd.open();
        if (file != null) {
          savedLocationText.setText(file);
        }
        willVerifyOverwrite = false; // Should override the listener on the text box
      }
    });

    // Add preferences table
    Label savedPreferencesLabel = new Label(container, SWT.BEGINNING);
    savedPreferencesLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.LEFT, true, false, 3, 1));
    savedPreferencesLabel.setText("Saved Preferences:");

    Composite tableContainer = new Composite(container, SWT.NONE);
    GridData tableLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
    tableContainer.setLayoutData(tableLayoutData);

    TableColumnLayout columnLayout = new TableColumnLayout();
    tableContainer.setLayout(columnLayout);

    final CheckboxTableViewer acceptedPreferences =
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

    return container;
  }
  
  private void validate() {
    this.getButton(IDialogConstants.OK_ID).setEnabled(isReady());
  }
  
  private boolean isReady() {
    if (title.length() == 0) {
      return false;
    }
    
    if (description.length() == 0) {
      return false;
    }
    
    if (savedFileLocation.length() == 0) {
      return false;
    }
    
    if (selectedKeys.size() == 0) {
      return false;
    }

    File destFile = new File(savedFileLocation);

    if (destFile.exists()) {
      if (!destFile.isFile()) {
        return false;
      }
      
      if (!destFile.canWrite()) {
        return false;
      }
    }

    File parentDir = destFile.getParentFile();

    if (parentDir == null) {
      return false;
    }

    if (!parentDir.exists()) {
      return false;
    }
    
    if (!parentDir.isDirectory()) {
      return false;
    }
    
    return parentDir.canWrite(); // Can we create a file in this directory?
  }
  
  @Override
  protected void okPressed() {
    if (!isReady()) {
      return; // Should never happen, since we disable OK when not ready
    }

    IPath destinationPath = new Path(savedFileLocation);

    String fileExtension = destinationPath.getFileExtension();
    if (!"epf".equals(fileExtension)) {
      String message = String.format("The file \"%s\" does not have an .epf extension. Add it?", destinationPath);
      if (MessageDialog.openQuestion(this.getShell(), "Add .epf to filename?", message)) {
        destinationPath = destinationPath.addFileExtension("epf");
        willVerifyOverwrite = true;
      }
    }
    // If the user picked a file via the dialog and that file exists, then they've already been 
    // asked if the file should be overwritten, and we shouldn't ask again. If not, then we need 
    // to check if the file exists and, if so, ask them now.
    if (willVerifyOverwrite) {
      if (destinationPath.toFile().exists()) {
        String message = String.format("The file \"%s\" will be overwritten. Is this OK?", destinationPath);
        if (!MessageDialog.openQuestion(this.getShell(), "Overwrite?", message)) {
          return;
        }
      }
    }
    
    // Save the preferences into an EPF file
    EpfFileModel epfFile = new EpfFileModel(
        title, 
        description, 
        getTaskType());
    
    Map<String, String> savedPreferences = getSavedPreferences();
    
    for (Map.Entry<String, String> entry : savedPreferences.entrySet()) {
      epfFile.addElement(entry.getKey(), entry.getValue());
    }

    try {
      epfFile.writeFile(destinationPath);
      super.okPressed(); // Closes the dialog and returns an OK result
    } catch (IOException e) {
      MechanicLog.getDefault().logError(e, "Error while writing %s", destinationPath);
    }
  }

  /**
   * Get task type provided by the user. Only valid after the dialog has been
   * run.
   */
  private TaskType getTaskType() {
    switch (taskType) {
      case 0:
        return TaskType.LASTMOD;

      case 1:
        return TaskType.RECONCILE;

      default:
        return null;
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
