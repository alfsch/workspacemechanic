/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
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
 * Provides support for loading tasks defined in extension points.
 *
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public class ExtensionPointScanner implements TaskScanner {
  public void scan(TaskCollector collector) {
    new TasksExtensionPoint().getTasks(collector);
  }
}
