/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.google.eclipse.mechanic.MechanicService;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.internal.Util;
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
// TODO(smckay): add a dialog close listener, reschedule mechanic if the
// sleep time has changed.
public class MechanicPreferencePage extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage {

  private final Shell shell;

  /*
   * Tasks that are known to the TaskService and have are not not in the
   * set of already blocked tasks. As new tasks are added to the set of
   * blocked tasks they are removed from this list.
   */
  private final List<Task> unblockedTasks;

  private TaskIdsListEditor blockedEditor;

  public MechanicPreferencePage() {
    super(GRID);
    shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    setPreferenceStore(MechanicPlugin.getDefault().getPreferenceStore());

    unblockedTasks = Util.newArrayList();
    unblockedTasks.addAll(MechanicService.getInstance().getAllKnownTasks());

    // remove tasks that are already blocked
    for (String id : MechanicPreferences.getBlockedTaskIds()) {
      removeKnownTaskById(id);
    }
  }

  /**
   * Add edit fields to the preference page.
   */
  @Override
  public void createFieldEditors() {
    addField(newMinimumRangeFieldEditor(
        MechanicPreferences.SLEEPAGE_PREF,
        "Task scan frequency:",
        MechanicPreferences.MINIMUM_SLEEP_SECONDS,
        "Task scan frequency",
        getFieldEditorParent()));

    addField(new DirectoryOrUrlEditor(MechanicPreferences.DIRS_PREF,
        "Task source directories:", getFieldEditorParent()));

    blockedEditor = new TaskIdsListEditor(MechanicPreferences.BLOCKED_PREF,
        "Blocked tasks:", "Blocked tasks",
        getFieldEditorParent());

    addField(blockedEditor);

    addField(new BooleanFieldEditor(MechanicPreferences.SHOW_POPUP_PREF,
        "Show popup when tasks fail", getFieldEditorParent()));
  }

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

  private class TaskIdsListEditor extends PathEditor {

    /**
     * Creates a path field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param dirChooserLabelText the label text displayed for the
     *     directory chooser
     * @param parent the parent of the field editor's control
     */
    public TaskIdsListEditor(String name, String labelText,
        String dirChooserLabelText, Composite parent) {
      super(name, labelText, dirChooserLabelText, parent);
    }

    @Override
    protected String getNewInputObject() {

      TaskSelectionDialog dlg = new TaskSelectionDialog(
          shell, unblockedTasks);

      // blocks until the user click OK or CANCEL
      dlg.open();

      if (dlg.getReturnCode() == TaskSelectionDialog.OK) {
        Object[] result = dlg.getResult();
        if (result.length > 0) {
          String id = ((Task) result[0]).getId();
          removeKnownTaskById(id);
          return id;
        }
      }
      return null;
    }
  }

  /**
   * Removes the task with the corresponding id from the list of known
   * tasks, if it exists in the set, else does nothing. If the list
   * contains multiple instances of an Task with the same id, then
   * only the first task is removed.
   */
  private void removeKnownTaskById(String id) {

    for (int i = 0; i < unblockedTasks.size(); i++) {
      if (unblockedTasks.get(i).getId().equals(id)) {
        unblockedTasks.remove(i);
        return;
      }
    }
  }
}
