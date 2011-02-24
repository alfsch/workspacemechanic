/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.swt.widgets.Display;

import com.google.eclipse.mechanic.internal.RootTaskScanner;
import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

/**
 * MechanicService provides basic services like scanning for and execution of
 * {@link Task}s. The service also provides hooks for other components to
 * interact with the service, display service information and invoke the basic
 * services.
 *
 * <p>To be of any use at all the service needs to be started when Eclipse
 * starts. This can easily be done using the {@code org.eclipse.ui.statup}
 * extension point.
 *
 * <p>The service doesn't itself expose any UI components. This should be done
 * using an AbstractWorkbenchTrimWidget an IViewPart or some other GUI
 * component.
 *
 * <p>TODO(smckay): We're exposing various bits of our internal state. Expose a
 * proper model eliminating the need to be-spangle this class with various
 * getters.
 *
 * @author smckay@google.com (Steve McKay)
 */
public final class MechanicService {

  private static final MechanicService instance = new MechanicService();

  /**
   * Enumeration of the various states a task can be in.
   */
  public enum TaskStatus {
    PASSED, FAILED, BLOCKED
  }

  private static class Collector implements TaskCollector {
    private final Set<Task> tasks = new LinkedHashSet<Task>();

    public void add(Task task) {
      tasks.add(task);
    }

    public Set<Task> getTasks() {
      return tasks;
    }

    public void reset() {
      tasks.clear();
    }
  }

  private final MechanicLog log = MechanicLog.getDefault();

  private final RootTaskScanner scanner = RootTaskScanner.getInstance();
  private final Map<Task, TaskStatus> taskStatus = Util.newHashMap();
  private final Collector collector = new Collector();

  // those registered to receive events we generate
  private final Set<StatusChangeListener> statusChangeListeners = Util.newHashSet();

  // the job we use to run us periodically. We provide our own faux
  // job control as part of our interface.
  private final Job job;

  private MechanicStatus currentStatus = MechanicStatus.STOPPED;

  private AtomicBoolean repairing = new AtomicBoolean(false);

  /**
   * Returns an instance of MechanicService.
   */
  public static MechanicService getInstance() {
    return instance;
  }

  private MechanicService() {

    // add a property change listener to the plugin prefs so we can
    // update our running config when prefs have been changed.
    MechanicPreferences.addListener(new PreferenceChangeListener());

    job = new ServiceJob();
  }

  /**
   * Causes the job to be scheduled to run after the period of time
   * specified in the thread sleep seconds preference.
   */
  private void reschedule() {
    job.schedule(MechanicPreferences.getThreadSleepSeconds() * 1000);
  }

  /**
   * Causes the job to start as soon as possible. If the job is already running
   * it has no affect.
   */
  public void start() {
    job.schedule(); // in case it has never been scheduled
    job.wakeUp(); // in case it is sleeping
    // our run method should be called shortly
  }

  /**
   * Causes our Job to sleep, meaning that it will no longer be scheduled
   * to wake at certain intervals. Calling {@link #start()} or
   * {@link #reschedule()} will cause the job to run again.
   *
   * TODO(smckay): remember stopped state across sessions.
   */
  public void stop() {

    // effectively un-schedules the job. If it is already running we must wait.
    job.sleep();

    // TODO(smckay): this displays the stopped icon even if the job is still
    // running. It would be nice to either force the job to stop, or to
    // wait to display the stopped icon.
    setStatus(MechanicStatus.STOPPED);
  }

  public boolean isStopped() {
    return currentStatus == MechanicStatus.STOPPED;
  }

  /**
   * This is the main service entry point. Our job calls through directly
   * to this method when its corresponding run method is called.
   *
   * Here we scan for new tasks, test for compliance and send notifications
   * to other interested classes.
   */
  private IStatus run(IProgressMonitor monitor) {

    // TODO(smckay): use the progress monitor to report task information
    monitor.beginTask("Updating Tasks", 3);
    try {
      update(monitor);
    } finally {
      reschedule();
    }
    monitor.worked(1);
    return Status.OK_STATUS;
  }

  /**
   * Adds the supplied listener to status change events for the Mechanic (not the
   * Job). UI components can use this facility to get notifications announcing
   * changes in the MechanicService.
   *
   * <p>Callers of this method should note that Mechanic Service will
   * immediately send a notification to the supplied listener when this method
   * is called. Also, callers should remember to unsubscribe listeners when
   * they are disposed (or otherwise reach the end of their life).
   */
  public void addTaskStatusChangeListener(StatusChangeListener listener) {
    statusChangeListeners.add(listener);
    notifyListener(listener); // send courtesy update with current status
  }

  /**
   * Removes supplied listener from our set of listeners if it is contained
   * therein, else does nothing.
   *
   * @param listener listener to receive status update notifications.
   */
  public void removeTaskStatusChangeListener(StatusChangeListener listener) {
    statusChangeListeners.remove(listener);
  }

  /**
   * Returns a {@link RepairManager} capable of fixing broken Tasks. The
   * returned {@link RepairManager} will use the supplied
   * {@link RepairDecisionProvider} to collect user input needed to determine
   * what actions to take for each failing Task.
   */
  public RepairManager getRepairManager(RepairDecisionProvider choiceProvider) {
    return new RepairManager(this, getFailingItems(), choiceProvider);
  }

  /**
   * Runs the {@link RepairManager} capable of fixing broken Tasks. The
   * supplied {@link RepairDecisionProvider} will be used to collect user input
   * needed to determine what actions to take for each failing Task.
   *
   * <p>If a repair manager is already running, then no operation will be
   * performed.
   */
  public void runRepairManager(RepairDecisionProvider choiceProvider) {
    boolean oldValue = repairing.getAndSet(true);
    if (oldValue == false) {
      try {
        getRepairManager(choiceProvider).run();
      } finally {
        repairing.set(false);
      }
    }
  }

  /**
   * Scans for all tasks, creates instances, tests them. Sends updates
   * to listeners.
   */
  private synchronized void update(IProgressMonitor monitor) {

    // first tell everyone we're updating
    setStatus(MechanicStatus.UPDATING);

    // scans for tasks
    updateTasks(monitor);

    // Only update if the status is still updating. (The mechanic might have been stopped.)
    if (currentStatus == MechanicStatus.UPDATING) {
      // send an update notifying statusChangeListeners of the new status
      setStatus(getFailingItemCount() > 0
          ? MechanicStatus.FAILED
          : MechanicStatus.PASSED);
    }
  }

  /**
   * Returns an immutable set of all the currently known tasks, passing or not.
   */
  public Set<Task> getAllKnownTasks() {
    return Collections.unmodifiableSet(collector.getTasks());
  }

  /**
   * A temporary solution allowing the trim widget to display the number
   * of failing items to the user.
   */
  public int getFailingItemCount() {
    return getFailingItems().size();
  }

  /**
   * Refreshes the list of {@link Task}s.
   */
  private void updateTasks(IProgressMonitor monitor) {

    monitor.subTask("Looking For Tasks");

    // TODO(konigsberg): Pre-validate the directories and urls here. Wonder if we should add
    // something to scanner.

    // clear out the items
    collector.reset();
    scanner.scan(collector);

    monitor.subTask("Evaluating Tasks");
    monitor.worked(1);

    // test tasks and figure out which ones are blocked
    updateTaskStatus(new SubProgressMonitor(monitor, collector.getTasks().size()));

    monitor.worked(1);
  }

  /**
   * Executes each {@link Task}'s {@link Evaluator} test. Store the result.
   * Also marks blocked plugins as such.
   */
  private synchronized void updateTaskStatus(IProgressMonitor monitor) {

    Set<String> blocked = MechanicPreferences.getBlockedTaskIds();

    monitor.beginTask("", collector.getTasks().size());
    try {
      taskStatus.clear();

      for (Task task : collector.getTasks()) {
        monitor.subTask("Evaluating: " + task.getId());

        if (blocked.contains(task.getId())) {
          taskStatus.put(task, TaskStatus.BLOCKED);
          continue;
        }
        try {
          taskStatus
              .put(task, task.getEvaluator().evaluate() ? TaskStatus.PASSED : TaskStatus.FAILED);
        } catch (RuntimeException e) {
          log.logError(e, "Evaluator test failed for task %s", task);
        }
        monitor.worked(1);
      }
    } finally {
      monitor.done();
    }
  }

  /**
   * Saves the current status of the Mechanic Service, then notifies
   * all registered listeners of the status change.
   */
  private void setStatus(MechanicStatus status) {
    currentStatus = status;
    notifyListeners();
  }

  /**
   * Notifies all registered listeners using the last available task status.
   */
  private void notifyListeners() {

    for (StatusChangeListener listener : statusChangeListeners) {
      notifyListener(listener);
    }
  }

  /**
   * Sends a status changed notification to the specified listener.
   */
  private void notifyListener(StatusChangeListener listener) {

    // sends a notification in the UI thread. If we get other
    // listeners in other threads we'll need to re-think this model
    Display.getDefault().asyncExec(new NotificationDispatcher(listener, currentStatus));
  }

  /**
   * Returns a list of tasks with the FAILED status.
   */
  private List<Task> getFailingItems() {

    List<Task> failing = Util.newArrayList();
    for (Task item : collector.getTasks()) {
      TaskStatus status = taskStatus.get(item);
      if (TaskStatus.FAILED == status) {
        failing.add(item);
      }
    }
    return failing;
  }

  /**
   * Job instance providing periodic execution of our service and other
   * job control gizmos.
   */
  private class ServiceJob extends Job {

    public ServiceJob() {
      super("Workspace Mechanic");
      setPriority(Job.DECORATE); // very low priority
      addJobChangeListener(new TaskScanJobChangeListener()); // basic job info
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
      return instance.run(monitor);
    }
  }

  /**
   * Runnable class used to dispatch notifications to subscribers.
   */
  private static class NotificationDispatcher implements Runnable {

    private final StatusChangeListener listener;

    private final MechanicStatus status;

    public NotificationDispatcher(StatusChangeListener listener, MechanicStatus status) {
      this.listener = listener;
      this.status = status;
    }

    public void run() {
      listener.statusChanged(new StatusChangedEvent(status));
    }
  }

  /**
   * Logs job status changes.
   */
  private static class TaskScanJobChangeListener extends JobChangeAdapter {

    @Override
    public void done(IJobChangeEvent event) {
      // This seems to double up a logged message with the job manager.
      if (!event.getResult().isOK()) {
        MechanicLog.getDefault().log(event.getResult());
      }
    }
  }

  /**
   * Wakes up the service when a preferences change is received.
   */
  private class PreferenceChangeListener implements IPreferenceChangeListener {
    public void preferenceChange(PreferenceChangeEvent event) {
      // if the service is stopped, we don't do anything.
      if (!MechanicService.getInstance().isStopped()) {
        MechanicService.getInstance().start();
      }
    }
  }
}
