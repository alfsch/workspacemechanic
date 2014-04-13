/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.internal.ResourceTaskProvidersExtensionPoint;
import com.google.eclipse.mechanic.internal.Supplier;

/**
 * Scanner that looks in the registered {@link IResourceTaskProvider}s
 * for tasks.
 */
public abstract class ResourceTaskScanner implements TaskScanner {

  private final Supplier<List<IResourceTaskProvider>> supplier;

  public ResourceTaskScanner() {
    this(new ResourceTaskProvidersExtensionPoint());
  }

  public ResourceTaskScanner(Supplier<List<IResourceTaskProvider>> supplier) {
    this.supplier = supplier;
  }

  public void scan(TaskCollector collector) {
    Preconditions.checkNotNull(collector, "'collector' cannot be null.");

    for (IResourceTaskProvider source : supplier.get()) {
      scan(source, collector);
    }
  }

  /**
   * Scan the source for tasks.
   *
   * @param source the source to scan. The source should already be considered valid.
   * @param collector the collector of tasks. Guaranteed to be not null.
   */
  protected abstract void scan(IResourceTaskProvider source, TaskCollector collector);
}
