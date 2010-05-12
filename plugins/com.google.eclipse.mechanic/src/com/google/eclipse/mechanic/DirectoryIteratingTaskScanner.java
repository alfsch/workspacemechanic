/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import static com.google.eclipse.mechanic.internal.Util.checkArgument;
import static com.google.eclipse.mechanic.internal.Util.checkNotNull;

import com.google.eclipse.mechanic.internal.DirectorySetSupplier;
import com.google.eclipse.mechanic.internal.PreferencesDirectorySetSupplier;
import com.google.eclipse.mechanic.internal.Util;

import java.io.File;

/**
 * Scanner that looks in the registered directories for tasks.
 *
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public abstract class DirectoryIteratingTaskScanner implements TaskScanner {

  private final DirectorySetSupplier dirs = PreferencesDirectorySetSupplier.getInstance();

  public void scan(TaskCollector collector) {
    Util.checkNotNull(collector, "'collector' cannot be null.");

    for (File dir : dirs.get()) {
      checkNotNull(dir, "'dir' cannot be null.");
      checkArgument(dir.exists(), String.format("Directory '%s' does not exist.", dir));
      checkArgument(dir.canRead(), String.format("Directory '%s' is not readable.", dir));

      scan(dir, collector);
    }
  }

  /**
   * Scan a directory for tasks.
   *
   * @param dir the directory to scan. A test has been made to ensure the
   * dir is not null, that the directory exists and can be read.
   * @param collector the collector of tasks. Guaranteed to be not null.
   */
  protected abstract void scan(File dir, TaskCollector collector);
}
