/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

/**
 * Simple interface for collecting tasks.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface TaskScanner {

  /**
   * Adds Tasks to the supplied collector.
   *
   * @param collector the collector of {@link Task}s.
   */
  void scan(TaskCollector collector);

}
