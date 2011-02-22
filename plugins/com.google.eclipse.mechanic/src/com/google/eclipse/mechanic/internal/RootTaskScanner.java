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

/**
 * A {@link TaskScanner} that loads and runs all other {@link TaskScanner}s.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class RootTaskScanner implements TaskScanner {

  private static final RootTaskScanner instance = new RootTaskScanner();

  private final ScannersExtensionPoint scannersExtensionPoint = new ScannersExtensionPoint();

  public static RootTaskScanner getInstance() {
    return instance;
  }

  public void scan(TaskCollector collector) {
    for (TaskScanner scanner : scannersExtensionPoint.getScanners()) {
      scanner.scan(collector);
    }
  }
}
