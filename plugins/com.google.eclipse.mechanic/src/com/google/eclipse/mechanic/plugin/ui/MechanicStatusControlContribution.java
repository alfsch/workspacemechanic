/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.ui;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import com.google.eclipse.mechanic.IMechanicService;
import com.google.eclipse.mechanic.MechanicService;
import com.google.eclipse.mechanic.MechanicStatus;
import com.google.eclipse.mechanic.RepairDecisionProvider;
import com.google.eclipse.mechanic.IStatusChangeListener;
import com.google.eclipse.mechanic.StatusChangedEvent;
import com.google.eclipse.mechanic.core.recorder.ChangeCollector;
import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

/**
 * Widget that appears in the status bar.
 */
public class MechanicStatusControlContribution extends WorkbenchWindowControlContribution {

  private static final MechanicLog log = MechanicLog.getDefault();

  // various actions used in our context menu...
  private static final Action prefsAction =
      new OpenPreferencesAction("com.google.eclipse.mechanic.plugin.ui.MechanicPreferencePage");

  private static final Action helpAction = createHelpAction();

  private final Action invokeScannerAction = new InvokeScannerAction();

  private final Action repairAction = new InvokeRepairManagerAction();

  private final StopAction stopAction = new StopAction();

  private final StartAction startAction = new StartAction();

  private IAction startRecordingAction = new StartRecordingAction();

  private IAction stopRecordingAction = new StopRecordingAction();

  private IAction showAllTasksAction = new ShowAllTasksAction();

  private final IMechanicService service = MechanicService.getInstance();

  private final IStatusChangeListener statusListener;

  private MechanicStatus status;
  private Label label;
  private Map<DisplayStatus, Image> images;

  public MechanicStatusControlContribution() {
    // to be registered in our initialize method, and disposed of with us
    statusListener = new IStatusChangeListener() {
      public void statusChanged(StatusChangedEvent e) {
        setMechanicStatus(e.getStatus());
        updateDisplay();
      }
    };
  }

  private static Action createHelpAction() {
    String helpUrl = MechanicPreferences.getHelpUrl();
    try {
      return new OpenUrlAction(helpUrl, "Help...");
    } catch (RuntimeException e) {
      log.logError(e, 
          "Could not initialize help action for URL %s: %s", helpUrl, e.getMessage());
      return null;
    }
  }

  @Override
  protected Control createControl(Composite parent) {
    initImageCache();

    label = new Label(parent, SWT.CENTER);
    label.setSize(22, 22);
    label.setEnabled(false);

    RowLayout layout = new RowLayout(SWT.HORIZONTAL | SWT.VERTICAL | SWT.BORDER);
    layout.wrap = false;
    parent.setLayout(layout);

    setMechanicStatus(MechanicStatus.STOPPED);

    // observe mouse events
    registerClickHandlers();

    createContextMenu(label);

    // observe TaskService status changes
    service.addTaskStatusChangeListener(statusListener);

    // TODO(smckay): figure out why updateDisplay must be called here and now
    // or else, the widget contents (label) will *never* get displayed.
    updateDisplay();
    return label;
  }

  /**
   * Initializes the image cache.
   */
  private void initImageCache() {
    images = Util.newHashMap();

    for (DisplayStatus ds : DisplayStatus.values()) {
      String path = String.format("icons/%s.png", ds.name().toLowerCase());
      ImageDescriptor desc = MechanicPlugin.getImageDescriptor(path);
      Image image = desc.createImage();
      images.put(ds, image);
    }
  }

  private void updateDisplay() {
    /*
     * Don't do anything if the workbench is closed. Without this check we were
     * causing stack traces when workbench is closing.
     */
    if (this.getWorkbenchWindow().getWorkbench().isClosing()) {
      return;
    }

    if (!label.isDisposed()) {
      DisplayStatus ds = getDisplayStatus();

      // as this may be the first update we've received, we enable our labels
      label.setEnabled(true);
      label.setImage(images.get(ds));
      label.setToolTipText(ds.toString());
    }
  }

  private void setMechanicStatus(MechanicStatus status) {
    this.status = status;
  }

  private void registerClickHandlers() {

    MouseListener listener = new MouseListener() {

      public void mouseDoubleClick(MouseEvent e) {

        // if the conditions are right, rerun the service
        if (!service.isStopped() && status != MechanicStatus.FAILED && status != MechanicStatus.UPDATING) {
          invokeScannerAction.run();
        }
      }

      public void mouseUp(MouseEvent e) {

        // if the status is failed, we work with the user to repair failing
        // actions.
        if (!service.isStopped() && status == MechanicStatus.FAILED) {
          repairAction.run();
        }
      }

      // ignored
      public void mouseDown(MouseEvent e) {
      }

    };

    label.addMouseListener(listener);
  }

  /**
   * Re-maps the current TaskStatus to a display status. DisplayStatus provides
   * UI friendly serialization of the state.
   */
  private DisplayStatus getDisplayStatus() {
    switch (status) {
      case PASSED:
        return DisplayStatus.PASSED;
      case FAILED:
        return DisplayStatus.FAILED;
      case UPDATING:
        return DisplayStatus.UPDATING;
      case STOPPED:
        return DisplayStatus.STOPPED;
    }
    throw new RuntimeException("Unhandled status value: " + status.name());
  }

  @Override
  public void dispose() {

    label.dispose();

    for (Image image : images.values()) {
      image.dispose();
    }

    // un-register any Task Service listeners
    service.removeTaskStatusChangeListener(statusListener);
  }

  /**
   * Creates and registers a new popup menu on the supplied control.
   * 
   * <p>
   * When the menu is about to be shown the
   * {@link #fillContextMenu(IMenuManager)} method will be called.
   */
  private void createContextMenu(Control control) {
    MenuManager mgr = new MenuManager("#PopupMenu");
    mgr.setRemoveAllWhenShown(true);
    mgr.addMenuListener(new IMenuListener() {
      public void menuAboutToShow(IMenuManager manager) {
        fillContextMenu(manager);
      }
    });

    Menu menu = mgr.createContextMenu(control);
    control.setMenu(menu);
  }

  /**
   * Fills the context menu with menu items.
   */
  private void fillContextMenu(IMenuManager menu) {

    // add the individual menu items
    if (service.isStopped()) {
      menu.add(startAction);
    } else {
      menu.add(invokeScannerAction);
      if (status == MechanicStatus.FAILED) {
        menu.add(repairAction);
      }
      menu.add(stopAction);
    }

    menu.add(new Separator());

    IMenuManager recordingMenu = new MenuManager("Preference Recorder");
    recordingMenu.add(startRecordingAction);
    recordingMenu.add(stopRecordingAction);

    menu.add(recordingMenu);

    menu.add(showAllTasksAction);

    menu.add(new Separator());
    menu.add(prefsAction);
    if (helpAction != null) {
      menu.add(helpAction);
    }

    // an obligatory menu item that allows other code to add items to
    // out context menu.
    menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  /**
   * Representation of the current state of the mechanic.
   */
  public enum DisplayStatus {

    PASSED("All tasks passed. Double click to re-check."), FAILED(new Object() {
      @Override
      public String toString() {
        return String.format("%d failing tasks. Click to fix.",
            MechanicService.getInstance().getFailingItemCount());
      }
    }), UPDATING("Updating..."), STOPPED("Service is stopped.");

    private final Object describer;

    private DisplayStatus(Object describer) {
      this.describer = describer;
    }

    @Override
    public String toString() {
      return describer.toString();
    }
  }

  /**
   * Action that causes the mechanic to recheck all tasks.
   */
  private final class InvokeScannerAction extends Action {

    public InvokeScannerAction() {
      setText("Check All");
    }

    @Override
    public void run() {
      invokeMechanic();
    }

    /**
     * Runs the mechanic immediately.
     */
    private void invokeMechanic() {
      service.start();
    }
  }

  /**
   * Action that causes the mechanic to re-check all tasks.
   */
  private final class InvokeRepairManagerAction extends Action {

    public InvokeRepairManagerAction() {
      setText("Apply Fixes...");
    }

    @Override
    public void run() {
      invokeRepairManager();
    }

    /**
     * Prompts the user for which failing actions they should repair, then
     * repairs them.
     */
    private void invokeRepairManager() {
      RepairDecisionProvider cpro =
          new UserChoiceDecisionProvider(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
      // TODO(konigsberg): Replace with calls to runRepairManager?
      service.getRepairManager(cpro).run();
    }
  }

  /**
   * Action that starts the mechanic service.
   */
  private final class StartAction extends Action {

    public StartAction() {
      setText("Start Service");
    }

    @Override
    public void run() {
      start();
    }

    /**
     * Stops the service.
     */
    private void start() {
      service.start();
    }
  }

  /**
   * Action that stops the mechanic service.
   */
  private final class StopAction extends Action {

    public StopAction() {
      setText("Stop Service");
    }

    @Override
    public void run() {
      stop();
    }

    /**
     * Stops the service.
     */
    private void stop() {
      service.stop();
    }
  }

  /**
   * Action to start recording preferences.
   */
  public class StartRecordingAction extends Action {

    public StartRecordingAction() {
      super("Start Recording");
    }

    @Override
    public void run() {
      try {
        // Update menu items
        this.setEnabled(false);
        stopRecordingAction.setEnabled(true);

        MechanicPlugin.getDefault().getPreferenceRecordingService().startRecording();
      } catch (CoreException e) {
        MechanicPlugin.getDefault().getLog().log(e.getStatus());
      }
    }
  }

  /**
   * Action to stop recording preferences and as the user how to re
   */
  public class StopRecordingAction extends Action {
    public StopRecordingAction() {
      super("Stop Recording...");
      this.setEnabled(false);
    }

    @Override
    public void run() {
      try {
        // Stop recording, and collect results
        ChangeCollector collector = new ChangeCollector();
        MechanicPlugin.getDefault().getPreferenceRecordingService().endRecording(collector);

        // Change states of the menu items
        this.setEnabled(false);
        startRecordingAction.setEnabled(true);

        // Display dialog to get obtain properties of the saved task file
        Shell parentShell = Display.getCurrent().getActiveShell();
        EpfOutputDialog dialog = new EpfOutputDialog(parentShell, 
            collector.getPreferences());
        dialog.open();
      } catch (CoreException e) {
        MechanicLog.getDefault().logError(e);
      }
    }
  }

  /**
   * Action that displays all existing tasks.
   */
  private final class ShowAllTasksAction extends Action {

    public ShowAllTasksAction() {
      setText("Show all tasks");
    }

    @Override
    public void run() {
      // Display dialog to get obtain properties of the saved task file
      Shell parentShell = Display.getCurrent().getActiveShell();
      TaskSelectionDialog dialog = new TaskSelectionDialog(parentShell,
          "All known tasks",
          MechanicService.getInstance().getAllKnownTasks());
      dialog.setAddCancelButton(false);
      dialog.open();
    }
  }

}