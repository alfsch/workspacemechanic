/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;


/**
 * Interface for collecting tasks from {@link TaskScanner}s.
 *
 * <p>This is the main communications channel between the {@link
 * com.google.eclipse.mechanic.MechanicService} and the {@link TaskScanner}s.
 *
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public interface TaskCollector {
  /**
   * Add a task to the collector.
   *
   * @param task the task to be added to the collector.
   */
  void add(Task task);
}
