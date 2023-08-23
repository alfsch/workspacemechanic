/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Display;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.internal.RootTaskScanner;
import com.google.eclipse.mechanic.plugin.core.IMechanicPreferences;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

/**
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
public final class MechanicService implements IMechanicService {

  private static final MechanicService instance = new MechanicService();

  private final IMechanicPreferences mechanicPreferences;

  /**
   * Enumeration of the various states a task can be in.
   */
  public enum TaskStatus {
    PASSED, FAILED, BLOCKED
  }

  static class Collector implements TaskCollector {
    private final Set<Task> tasks = new LinkedHashSet<Task>();

    public void collect(Task task) {
      tasks.add(task);
    }

    public Set<Task> getTasks() {
      return tasks;
    }

    public void reset() {
      tasks.clear();
    }
    
    @Override
    public String toString() {
      return tasks.toString();
    }
  }

  private final MechanicLog log = MechanicLog.getDefault();

  private final RootTaskScanner scanner = RootTaskScanner.getInstance();
  private final Map<Task, TaskStatus> taskStatus = Maps.newHashMap();
  private final Collector collector = new Collector();

  // those registered to receive events we generate
  // TODO(konigsberg): Change to CopyOnWriteArrayList
  private final Set<IStatusChangeListener> statusChangeListeners = Sets.newHashSet();

  // the job we use to run us periodically. We provide our own faux
  // job control as part of our interface.
  private final Job job;

  private MechanicStatus currentStatus = MechanicStatus.STOPPED;

  private AtomicBoolean repairing = new AtomicBoolean(false);

  /**
   * Returns an instance of MechanicService.
   */
  public static IMechanicService getInstance() {
    return instance;
  }

  private MechanicService() {
    mechanicPreferences = MechanicPlugin.getDefault().getMechanicPreferences();

    // add a property change listener to the plugin prefs so we can
    // update our running config when prefs have been changed.
    mechanicPreferences.addListener(new PreferenceChangeListener());

    job = new ServiceJob();
  }

  /**
   * Causes the job to be scheduled to run after the period of time
   * specified in the thread sleep seconds preference.
   */
  private void reschedule() {
    job.schedule(mechanicPreferences.getThreadSleepSeconds() * 1000);
  }

  /**
   * {@inheritDoc}
   *
   * Causes the job to start as soon as possible. If the job is already running
   * it has no affect.
   */
  public void start() {
    job.schedule(); // in case it has never been scheduled
    job.wakeUp(); // in case it is sleeping
    // our run method should be called shortly
  }

  /**
   * {@inheritDoc}
   *
   * Causes our Job to sleep, meaning that it will no longer be scheduled
   * to wake at certain intervals. Calling {@link #start()} or
   * {@link #reschedule()} will cause the job to run again.
   */
  public void stop() {

    // effectively un-schedules the job. If it is already running we must wait.
    job.sleep();

    // TODO(smckay): this displays the stopped icon even if the job is still
    // running. It would be nice to either force the job to stop, or to
    // wait to display the stopped icon.
    setStatus(MechanicStatus.STOPPED);
  }

  /**
   * {@inheritDoc}
   */
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
   * {@inheritDoc}
   */
  public void addTaskStatusChangeListener(IStatusChangeListener listener) {
    statusChangeListeners.add(listener);
    notifyListener(listener); // send courtesy update with current status
  }

  /**
   * Removes supplied listener from our set of listeners if it is contained
   * therein, else does nothing.
   *
   * @param listener listener to receive status update notifications.
   */
  public void removeTaskStatusChangeListener(IStatusChangeListener listener) {
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
   * {@inheritDoc}
   */
  public Set<Task> getAllKnownTasks() {
    Set<Task> set = Sets.newLinkedHashSet();
    set.addAll(collector.getTasks());
    return set;
  }

  /**
   * {@inheritDoc}
   */
  public int getFailingItemCount() {
    return getFailingItems().size();
  }

  /**
   * Refreshes the list of {@link Task}s.
   */
  private void updateTasks(IProgressMonitor monitor) {

    monitor.subTask("Looking For Tasks");

    // clear out the items
    collector.reset();
    scanner.scan(collector);

    monitor.subTask("Evaluating Tasks");
    monitor.worked(1);

    // test tasks and figure out which ones are blocked
    updateTaskStatus(monitor);

    monitor.worked(1);
  }

  /**
   * Executes each {@link Task}'s {@link Evaluator} test. Store the result.
   * Also marks blocked plugins as such.
   */
  private synchronized void updateTaskStatus(IProgressMonitor monitor) {

    Set<String> blocked = mechanicPreferences.getBlockedTaskIds();

    SubMonitor subMonitor = SubMonitor.convert(monitor, collector.getTasks().size());
    try {
      taskStatus.clear();

      for (Task task : collector.getTasks()) {
        subMonitor.subTask("Evaluating: " + task.getId());

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
        subMonitor.worked(1);
      }
    } finally {
      subMonitor.done();
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

    for (IStatusChangeListener listener : statusChangeListeners) {
      notifyListener(listener);
    }
  }

  /**
   * Sends a status changed notification to the specified listener.
   */
  private void notifyListener(IStatusChangeListener listener) {

    // sends a notification in the UI thread. If we get other
    // listeners in other threads we'll need to re-think this model
    Display.getDefault().asyncExec(new NotificationDispatcher(listener, currentStatus));
  }

  /**
   * Returns a list of tasks with the FAILED status.
   */
  private List<Task> getFailingItems() {

    List<Task> failing = Lists.newArrayList();
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
  private static class ServiceJob extends Job {

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

    private final IStatusChangeListener listener;

    private final MechanicStatus status;

    public NotificationDispatcher(IStatusChangeListener listener, MechanicStatus status) {
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
  @SuppressWarnings("deprecation") // for IPropertyChangeListener and PropertyChangeEvent
  private static class PreferenceChangeListener implements org.eclipse.core.runtime.Preferences.IPropertyChangeListener {
    public void propertyChange(org.eclipse.core.runtime.Preferences.PropertyChangeEvent event) {
      // if the service is stopped, we don't do anything.
      if (!MechanicService.getInstance().isStopped()) {
        MechanicService.getInstance().start();
      }
    }
  }
}
