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
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Strings;
import com.google.eclipse.mechanic.internal.TaskType;

/**
 * The dialog to obtain properties for outputting a task file.
 */
public abstract class BaseOutputDialog extends Dialog {

  enum Component {
    TITLE,
    DESCRIPTION,
    TASK_TYPE
  }

  private final String extension;
  private String title = "";
  private String description = "";
  private int taskType;
  private String savedFileLocation = "";
  private boolean willVerifyOverwrite = true;
  private Set<Component> components;

  /**
   * Create an output dialog for the given shell.
   * 
   * @param parentShell the shell to create this dialog in.
   * @param extension the file type extension.
   * @param components the set of task parameter components to show.
   * If empty, show all components.
   */
  public BaseOutputDialog(Shell parentShell, String extension,
      Component... components) {
    super(parentShell);
    this.extension = extension;
    if (components.length == 0) {
      this.components = EnumSet.allOf(Component.class);
    } else {
      this.components = EnumSet.noneOf(Component.class);
      this.components.addAll(Arrays.asList(components));
    }
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
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
  }

  @Override
  protected final Control createDialogArea(Composite parent) {
    final Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(3, false);
    container.setLayout(layout);

    // Add title field
    if (components.contains(Component.TITLE)) {
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
    }

    // Add description field
    if (components.contains(Component.DESCRIPTION)) {
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
    }

    // Add task type field
    if (components.contains(Component.TASK_TYPE)) {
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
    }

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

    addChildConfiguration(container);

    return container;
  }

  protected void addChildConfiguration(Composite parent) {
  }

  protected void validate() {
    this.getButton(IDialogConstants.OK_ID).setEnabled(isValid());
  }

  /**
   * Return true if the dialog is valid.
   */
  protected boolean isValid() {
    if (components.contains(Component.TITLE) && title.isEmpty()) {
      return false;
    }
    
    if (components.contains(Component.DESCRIPTION) && description.isEmpty()) {
      return false;
    }
    
    if (savedFileLocation.isEmpty()) {
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

  protected IPath getValidOutputLocation() {
    IPath destinationLocation = new Path(savedFileLocation);

    if (!Strings.isNullOrEmpty(extension)) {
      
    String fileExtension = destinationLocation.getFileExtension();
      if (!extension.equals(fileExtension)) {
        String message = String.format("The file \"%s\" does not have a .%s extension. Add it?", destinationLocation, extension);
        if (MessageDialog.openQuestion(this.getShell(), "Add ." + extension + " to filename?", message)) {
          destinationLocation = destinationLocation.addFileExtension(extension);
          willVerifyOverwrite = true;
        }
      }
    }
    // If the user picked a file via the dialog and that file exists, then they've already been 
    // asked if the file should be overwritten, and we shouldn't ask again. If not, then we need 
    // to check if the file exists and, if so, ask them now.
    if (willVerifyOverwrite) {
      if (destinationLocation.toFile().exists()) {
        String message = String.format("The file \"%s\" will be overwritten. Is this OK?", destinationLocation);
        if (!MessageDialog.openQuestion(this.getShell(), "Overwrite?", message)) {
          return null;
        }
      }
    }
    return destinationLocation;
  }

  protected String getTitle() {
    return title;
  }

  protected String getDescription() {
    return description;
  }
  /**
   * Get task type provided by the user. Only valid after the dialog has been
   * run.
   */
  protected TaskType getTaskType() {
    switch (taskType) {
      case 0:
        return TaskType.LASTMOD;

      case 1:
        return TaskType.RECONCILE;

      default:
        return null;
    }
  }
}
