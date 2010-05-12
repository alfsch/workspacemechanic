/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.DirectoryIteratingTaskScanner;
import com.google.eclipse.mechanic.KeyboardBindingsTask;
import com.google.eclipse.mechanic.TaskCollector;

import java.io.File;

/**
 * Scanner for keyboard bindings.
 *
 * @author zorzella@google.com (Luiz-Otavio Zorzella)
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public class KeyboardBindingsScanner extends DirectoryIteratingTaskScanner {

  @Override
  public void scan(File dir, TaskCollector collector) {
    collector.add(new KeyboardBindingsTask());
  }
}
