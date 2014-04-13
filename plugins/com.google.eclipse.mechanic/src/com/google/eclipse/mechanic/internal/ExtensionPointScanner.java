/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.CompositeTaskInterface;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.TaskScanner;

/**
 * Provides support for loading tasks defined in extension points.
 */
public class ExtensionPointScanner implements TaskScanner {
  public void scan(TaskCollector collector) {
    for (CompositeTaskInterface task : new TasksExtensionPoint().get()) {
      collector.collect(task);
    }
  }
}
