/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.TaskScanner;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.testing.EmptyLog;

/**
 * Tests for {@link RootTaskScanner}
 */
public class RootTaskScannerTest {

  @Test
  public void testThatAThrowingTaskScannerDoesNotKillTheMechanic() {
    // TODO(zorzella): where are we supposed to put test infra, like fakes
    // for ScannersExtensionPointInterface, TaskScanner and TaskCollector?
    ScannersExtensionPointInterface scannerPoint = new ScannersExtensionPointInterface() {
      public List<TaskScanner> getScanners() {
        List<TaskScanner> result = new ArrayList<TaskScanner>();
        result.add(new TaskScanner() {
          public void scan(TaskCollector collector) {
            throw new RuntimeException();
          }
        });
        return result;
      }
    };
    MechanicLog log = new MechanicLog(new EmptyLog());
    RootTaskScanner scanner = new RootTaskScanner(log, scannerPoint);
    TaskCollector collector = new TaskCollector() {
      public void add(Task task) {}
    };
    scanner.scan(collector);
    // We just want to be sure that this does not throw
  }
}
