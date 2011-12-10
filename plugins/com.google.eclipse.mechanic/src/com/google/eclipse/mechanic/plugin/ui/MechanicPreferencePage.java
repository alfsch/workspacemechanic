/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.google.common.collect.Lists;
import com.google.eclipse.mechanic.MechanicService;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.internal.BlockedTaskIdsParser;
import com.google.eclipse.mechanic.internal.TaskByTitleComparator;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

/**
 * Mechanic preferences page.
 *
 * <ul>
 * <li>There are some obvious flaws in the current implementation of Task
 * blocking. Notably, tasks are blocked by id, and when presenting a list
 * of blocked tasks to a user, an instance of the block Task may not
 * be available to provide a human readable description of the Task.</li>
 *
 * <li>There seems to be no easy way to hook into the standard editor to add
 * a listener for when something is removed from list. This means that tasks
 * once removed from the list of blocked tasks, will not appear in the list
 * of available tasks.</li>
 * </ul>
 *
 * @author smckay@google.com (Steve McKay)
 */
public class MechanicPreferencePage extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage {

  private static final BlockedTaskIdsParser blockedTaskParser = new BlockedTaskIdsParser();
  
  private final Shell shell;

  /*
   * Tasks that are known to the TaskService and have are not not in the
   * set of already blocked tasks. As new tasks are added to the set of
   * blocked tasks they are removed from this list.
   */
  private TaskIdsListEditor blockedEditor;

  public MechanicPreferencePage() {
    super(GRID);
    shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    setPreferenceStore(MechanicPlugin.getDefault().getPreferenceStore());

    
  }

  /**
   * Add edit fields to the preference page.
   */
  @Override
  public void createFieldEditors() {
    addField(newMinimumRangeFieldEditor(
        MechanicPreferences.SLEEPAGE_PREF,
        "Task scan frequency (seconds):",
        MechanicPreferences.MINIMUM_SLEEP_SECONDS,
        "Task scan frequency",
        getFieldEditorParent()));

    addField(new DirectoryOrUrlEditor(MechanicPreferences.DIRS_PREF,
        "Task sources:", getFieldEditorParent()));

    blockedEditor = new TaskIdsListEditor(MechanicPreferences.BLOCKED_PREF,
        "Blocked tasks:", getFieldEditorParent());

    addField(blockedEditor);

    addField(new BooleanFieldEditor(MechanicPreferences.SHOW_POPUP_PREF,
        "Show popup when tasks fail", getFieldEditorParent()));

//    addCacheFields(getFieldEditorParent());
  }

//  private void addCacheFields(final Composite parent) {
//    final BooleanEditor cacheContentField = new BooleanEditor(MechanicPreferences.CACHE_URI_CONTENT_PREF,
//        "Enable web content cache", parent);
//
//    addField(cacheContentField);
//
//    final IntegerFieldEditor cacheAgeField = newMinimumRangeFieldEditor(
//        MechanicPreferences.CACHE_URI_AGE_HOURS_PREF,
//        "Cache entry lifetime (hours)",
//        0,
//        "Cache entry lifetime",
//        parent);
//
//    addField(cacheAgeField);
//
//    final Button clearCacheButton = new Button(parent, SWT.PUSH);
//    clearCacheButton.setText("Clear cache");
//    clearCacheButton.addSelectionListener(new SelectionAdapter() {
//      @Override
//      public void widgetSelected(SelectionEvent e) {
//        clearCacheButton.setEnabled(false);
//        try {
//          UriCaches.clear();
//        } finally {
//          clearCacheButton.setEnabled(true);
//        }
//      }
//    });
//
//    SelectionAdapter selectionChangeListener = new SelectionAdapter() {
//      @Override
//      public void widgetSelected(SelectionEvent e) {
//        cacheAgeField.setEnabled(cacheContentField.getBooleanValue(), parent);
//        clearCacheButton.setEnabled(cacheContentField.getBooleanValue());
//      }
//    };
//
//    cacheContentField.getControl().addSelectionListener(selectionChangeListener);
//
//    // triggers setting the field values.
//    selectionChangeListener.widgetSelected(null);
//  }

  /*
   * Create an integer field editor with a minimum value.
   */
  private IntegerFieldEditor newMinimumRangeFieldEditor(String name, String labelText,
      int minimumValue, String errorMessagePrefix, Composite parent) {

    IntegerFieldEditor editor = new IntegerFieldEditor(name, labelText, parent);
    editor.setValidRange(minimumValue, Integer.MAX_VALUE);
    // Overriding the error message defined by "setValidRange, ".
    editor.setErrorMessage(errorMessagePrefix + " must be no less than " + minimumValue);

    return editor;
  }

  public void init(IWorkbench workbench) {}

  /**
   * Inner class of the list editor displaying blocked tasks and giving user the ability 
   * to add unblocked tasks to the list or remove a previously blocked task, making it 
   * available to workspace mechanic
   */
  private class TaskIdsListEditor extends ListEditor {

    /**
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public TaskIdsListEditor(String name, String labelText, Composite parent) {
      init(name, labelText);
      if (parent != null) {
        createControl(parent);
      }
    }

    @Override
    protected String getNewInputObject() {

      TaskSelectionDialog dlg = new TaskSelectionDialog(
          shell, "Select a Task to block", makeUnblockedTaskList());

      // blocks until the user click OK or CANCEL
      dlg.open();

      if (dlg.getReturnCode() == TaskSelectionDialog.OK) {
        Object[] result = dlg.getResult();
        if (result.length > 0) {
          String taskId = ((Task) result[0]).getId();
          return taskId;
        }
      }
      return null;
    }
    
    private List<Task> makeUnblockedTaskList() {
      List<Task> unblockedTasks = Lists.newArrayList();
      unblockedTasks.addAll(MechanicService.getInstance().getAllKnownTasks());

      // remove tasks that are already blocked
      for (String id : MechanicPreferences.getBlockedTaskIds()) {
        removeTaskById(unblockedTasks, id);
      }
      Collections.sort(unblockedTasks, TaskByTitleComparator.getInstance());
      return unblockedTasks;
    }
    
    @Override
    protected String[] parseString(String stringList) {
      return blockedTaskParser.parse(stringList);
    }

    @Override
    protected String createList(String[] items) {
      return blockedTaskParser.unparse(items);
    }
  }

  /**
   * Removes the task with the corresponding id from the given list of
   * tasks, if it exists in the set, else does nothing. If the list
   * contains multiple instances of an Task with the same id, then
   * only the first task is removed.
   */
  private void removeTaskById(List<Task> tasks, String id) {
    for (int i = 0; i < tasks.size(); i++) {
      if (tasks.get(i).getId().equals(id)) {
        tasks.remove(i);
        return;
      }
    }
  }

  /**
   * Exposes the checkbox.
   */
  static class BooleanEditor extends BooleanFieldEditor {

    private final Composite parent;

    public BooleanEditor(String name, String label, Composite parent) {
      super(name, label, parent);
      this.parent = parent;
    }

    public Button getControl() {
      return super.getChangeControl(parent);
    }
  }
}
