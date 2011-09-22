/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.TaskScanner;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.junit.Test;
import org.osgi.framework.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zorzella
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
    ILog ilog = new ILog() {
      public void removeLogListener(ILogListener listener) {}
      public void log(IStatus status) {}
      public Bundle getBundle() {
        return null;
      }
      public void addLogListener(ILogListener listener) {}
    };
    MechanicLog log = new MechanicLog(ilog);
    RootTaskScanner scanner = new RootTaskScanner(log, scannerPoint);
    TaskCollector collector = new TaskCollector() {
      public void add(Task task) {}
    };
    scanner.scan(collector);
    // We just want to be sure that this does not throw
  }
}
