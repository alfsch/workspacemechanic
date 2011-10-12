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
import org.eclipse.jface.dialogs.IDialogSettings;
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

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.eclipse.mechanic.internal.TaskType;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

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
  private boolean willVerifyOverwrite = true;
  private Set<Component> components;

  private Text titleText;
  private Text descriptionText;
  private Combo taskTypeCombo;
  private Text savedLocationText;

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

  // TODO: zorzella says: I don't think this overload is doing anything at all.
  // I refactored it to stop growing upon every invocation, but the whole bit
  // about "new Point(p.x, p.y * 3 / 2);" which, on face value, seems like an
  // attempt to make the dialog taller than its otherwise default, seems to
  // simply be having no effect.
  @Override
  protected Point getInitialSize() {
    // We check if the dialog has been resized by the user...
    if (getDialogBoundsSettings() == null) {
      // ... if not, we create a dialog twice the default size
      Point p =  super.getInitialSize();
      return new Point(p.x, p.y * 3 / 2);
    } else {
      // ... if it has been resized, we use that size, otherwise
      // every time we open the dialog it grows...
      return super.getInitialSize();
    }
    // ... there probably is a much better way of doing this, btw.
  }

  @Override
  protected IDialogSettings getDialogBoundsSettings() {
    String dialogSettingsSection = getDialogSettingsSection();
    if (dialogSettingsSection == null) {
      return null;
    }
    IDialogSettings settings = MechanicPlugin.getDefault().getDialogSettings();
    IDialogSettings section = settings.getSection(dialogSettingsSection);
    if (section == null) section = settings.addNewSection(dialogSettingsSection);
    return section;
  }

  /**
   * Override to describe the dialog settings section.
   */
  protected String getDialogSettingsSection() {
    return null;
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

  protected void createLabel(Composite parent, String text) {
    Label titleLabel = new Label(parent, SWT.BEGINNING);
    titleLabel.setText(text);
  }


  private final ModifyListener validateOnChange = new ModifyListener() {
    public void modifyText(ModifyEvent e) {
      validate();
    }
  };

  protected Text createTextBox(Composite parent) {
    Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    text.addModifyListener(validateOnChange);
    return text;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    final Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(3, false);
    container.setLayout(layout);

    if (components.contains(Component.TITLE)) {
      createLabel(container, "Title:");
      titleText = createTextBox(container);
      titleText.setEnabled(components.contains(Component.TITLE));
    }

    if (components.contains(Component.DESCRIPTION)) {
      createLabel(container, "Description:");
      descriptionText = createTextBox(container);
      descriptionText.setEnabled(components.contains(Component.DESCRIPTION));
    }

    if (components.contains(Component.TASK_TYPE)) {
      createLabel(container, "Task Type:");
      taskTypeCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
      // Ensuring the order hasn't changed, as we rely on that.
      Preconditions.checkState(TaskType.values()[0] == TaskType.LASTMOD);
      taskTypeCombo.setItems(new String[] {"Last Mod", "Reconcile"});
      taskTypeCombo.select(0);
      taskTypeCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
      taskTypeCombo.addModifyListener(validateOnChange);
      taskTypeCombo.setEnabled(components.contains(Component.TASK_TYPE));
    }

    // Add saved file location
    createLabel(container, "Saved File Location:");
    savedLocationText = createTextBox(container);
    savedLocationText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        willVerifyOverwrite = true;
      }});

    // TODO: this button belongs in the same line as the textBox above
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
        String location = getLocation();
        if (location != null && location.trim().length() > 0) {
          fd.setFileName(location);
        }
        fd.setOverwrite(true);
        fd.setFilterExtensions(new String[] { "*." + extension });
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
    Button b = this.getButton(IDialogConstants.OK_ID);
    // Can be null when called from createDailogArea. Honestly if someone refactors this,
    // remove this mistake.
    if (b != null) {
      boolean valid = isValid();
      b.setEnabled(valid);
    }
  }

  /**
   * Return true if the dialog is valid.
   */
  protected boolean isValid() {
    if (components.contains(Component.TITLE) && getTitle().length() == 0) {
      return false;
    }
    
    if (components.contains(Component.DESCRIPTION) && getDescription().length() == 0) {
      return false;
    }
    
    if (getLocation().length() == 0) {
      return false;
    }

    File destFile = new File(getLocation());

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
    IPath destinationLocation = new Path(getLocation());

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

  public String getTitle() {
    return titleText.getText();
  }

  public void setTitle(String text) {
    titleText.setText(text);
  }

  public String getDescription() {
    return descriptionText.getText();
  }

  public void setDescription(String text) {
    descriptionText.setText(text);
  }

  void setSavedLocation(String path) {
    savedLocationText.setText(path);
  }

  /**
   * Get task type provided by the user. Only valid after the dialog has been
   * created.
   */
  public TaskType getTaskType() {
    return TaskType.values()[taskTypeCombo.getSelectionIndex()];
  }

  public void setTaskType(TaskType taskType) {
    taskTypeCombo.select(Arrays.asList(TaskType.values()).indexOf(taskType));
  }

  public String getLocation() {
    return savedLocationText.getText();
  }

  public void setLocation(String text) {
    savedLocationText.setText(text);
  }
}
