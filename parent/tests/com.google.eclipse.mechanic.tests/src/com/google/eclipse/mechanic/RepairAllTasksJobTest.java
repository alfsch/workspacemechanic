/*******************************************************************************
 * Copyright (C) 2026, Workspace Mechanic contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.google.common.collect.ImmutableSet;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.testing.EmptyLog;

/**
 * Tests for {@link RepairAllTasksJob}.
 */
public class RepairAllTasksJobTest extends TestCase {

  private final RepairAction repairA = mock(RepairAction.class);
  private final RepairAction repairB = mock(RepairAction.class);
  private final RepairAction repairC = mock(RepairAction.class);

  private final Task taskA = task("a", repairA);
  private final Task taskB = task("b", repairB);
  private final Task taskC = task("c", repairC);

  private static Task task(String id, RepairAction repairAction) {
    Task task = mock(Task.class);
    when(task.getId()).thenReturn(id);
    when(task.getTitle()).thenReturn("Task " + id);
    when(task.getRepairAction()).thenReturn(repairAction);
    return task;
  }

  private static IStatus run(
      List<Task> tasks, Set<String> blockedIds, NullProgressMonitor monitor) {
    return new RepairAllTasksJob(new MechanicLog(new EmptyLog()), tasks, blockedIds)
        .run(monitor);
  }

  public void testRepairsAllTasks() {
    IStatus status = run(Arrays.asList(taskA, taskB, taskC), Collections.<String>emptySet(),
        new NullProgressMonitor());

    assertTrue(status.isOK());
    verify(repairA).run();
    verify(repairB).run();
    verify(repairC).run();
  }

  public void testSkipsBlockedTasks() {
    IStatus status = run(Arrays.asList(taskA, taskB, taskC), ImmutableSet.of("b"),
        new NullProgressMonitor());

    assertTrue(status.isOK());
    verify(repairA).run();
    verify(repairB, never()).run();
    verify(repairC).run();
  }

  public void testFailingRepairDoesNotStopTheOthers() {
    doThrow(new IllegalStateException("boom")).when(repairA).run();

    IStatus status = run(Arrays.asList(taskA, taskB, taskC), Collections.<String>emptySet(),
        new NullProgressMonitor());

    assertEquals(IStatus.ERROR, status.getSeverity());
    assertTrue(status.getMessage(), status.getMessage().startsWith("1 of 3 tasks"));
    verify(repairB).run();
    verify(repairC).run();
  }

  public void testCanceledMonitorStopsBeforeRepairing() {
    NullProgressMonitor monitor = new NullProgressMonitor();
    monitor.setCanceled(true);

    IStatus status = run(Arrays.asList(taskA, taskB), Collections.<String>emptySet(), monitor);

    assertEquals(IStatus.CANCEL, status.getSeverity());
    verify(repairA, never()).run();
    verify(repairB, never()).run();
  }
}
