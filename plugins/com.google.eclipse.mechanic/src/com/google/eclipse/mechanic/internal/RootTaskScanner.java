/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.TaskScanner;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * A {@link TaskScanner} that loads and runs all other {@link TaskScanner}s.
 */
public class RootTaskScanner implements TaskScanner {

  private static RootTaskScanner instance;

  private final MechanicLog log;
  private final ScannersExtensionPointInterface scannersExtensionPoint;

  public RootTaskScanner() {
    this(MechanicLog.getDefault(), new ScannersExtensionPoint());
  }
  
  RootTaskScanner(MechanicLog log, ScannersExtensionPointInterface scannersExtensionPoint) {
    this.log = log;
    this.scannersExtensionPoint = scannersExtensionPoint;
  }

  public synchronized static RootTaskScanner getInstance() {
    if (instance == null) {
      instance = new RootTaskScanner();
    }
    return instance;
  }

  public void scan(TaskCollector collector) {
    for (TaskScanner scanner : scannersExtensionPoint.get()) {
      try {
        scanner.scan(collector);
      } catch (RuntimeException e) {
        log.logError(e, "Exception scanning '%s', class '%s'",
            collector, collector.getClass().getName());
      }
    }
  }
}
