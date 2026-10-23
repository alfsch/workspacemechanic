/*******************************************************************************
 * Copyright (C) 2026, Workspace Mechanic contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

/**
 * Runs the {@link RepairAction} of every given {@link Task} in the background,
 * whether or not the task is currently in sync. This resets settings the user
 * changed by hand to the values from the task configuration. Blocked tasks are
 * skipped, and a task whose repair action fails does not stop the others.
 */
public final class RepairAllTasksJob extends Job {

  private final MechanicLog log;
  private final List<Task> tasks;
  private final Set<String> blockedIds;

  /**
   * @param tasks the tasks to repair. The collection is copied.
   * @param blockedIds ids of the tasks the user blocked. These are not repaired.
   */
  public RepairAllTasksJob(Collection<Task> tasks, Set<String> blockedIds) {
    this(MechanicLog.getDefault(), tasks, blockedIds);
  }

  RepairAllTasksJob(MechanicLog log, Collection<Task> tasks, Set<String> blockedIds) {
    super("Re-applying all Workspace Mechanic tasks");
    this.log = log;
    this.tasks = Lists.newArrayList(tasks);
    this.blockedIds = Sets.newHashSet(blockedIds);
    setUser(true);
  }

  @Override
  protected IStatus run(IProgressMonitor monitor) {
    SubMonitor progress = SubMonitor.convert(monitor, "Re-applying tasks", tasks.size());

    int repaired = 0;
    int failed = 0;
    int skipped = 0;
    for (Task task : tasks) {
      if (progress.isCanceled()) {
        log.logInfo("Re-applying tasks canceled after %d of %d tasks", repaired + failed + skipped,
            tasks.size());
        return Status.CANCEL_STATUS;
      }
      if (blockedIds.contains(task.getId())) {
        skipped++;
      } else {
        progress.subTask(task.getTitle());
        log.logInfo("Repairing item: %s", task.getId());
        try {
          task.getRepairAction().run();
          repaired++;
        } catch (RuntimeException e) {
          failed++;
          log.logError(e, "RepairAction failed for %s, %s", task.getId(), e.getMessage());
        }
      }
      progress.worked(1);
    }

    log.logInfo("Re-applied %d tasks, %d failed, %d blocked tasks skipped", repaired, failed,
        skipped);
    if (failed > 0) {
      return new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID,
          String.format("%d of %d tasks could not be applied, see the Error Log.", failed,
              repaired + failed));
    }
    return Status.OK_STATUS;
  }
}
