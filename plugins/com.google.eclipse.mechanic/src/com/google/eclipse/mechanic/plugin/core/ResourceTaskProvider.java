/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.core;

import java.util.List;

import org.eclipse.core.runtime.IStatus;

/**
 * Provides resource-based tasks.
 */
public abstract class ResourceTaskProvider {
  /**
   * Provides post-initialization validation of a provider.
   *
   * @return status of the initialization.
   */
  public abstract IStatus initialize();

  /**
   * TODO(konigsberg): Remove the filter parameter.
   */
  public abstract List<ResourceTaskReference> getTaskReferences(String extFilter);


  /**
   * TODO: Remove the pathFilter
   */
  public abstract List<ResourceTaskReference> getTaskReferences(String localPath, String extFilter);

  @Override
  public boolean equals(Object obj) {
    throw new RuntimeException(this.getClass().getName() + "doesn't implement equals");
  }

  @Override
  public int hashCode() {
    throw new RuntimeException(this.getClass().getName() + "doesn't implement hashCode");
  }
}
