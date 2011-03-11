/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.core;

import com.google.eclipse.mechanic.IResourceTaskProvider;

/**
 * Provides resource-based tasks.
 *
 * <b>Note: this API needs some rework, and is very likely to change.
 */
public abstract class ResourceTaskProvider implements IResourceTaskProvider {

  /**
   * Throws exception, ensures subclasses implement equals method.
   */
  @Override
  public boolean equals(Object obj) {
    throw new RuntimeException(this.getClass().getName() + "doesn't implement equals");
  }

  /**
   * Throws exception, ensures subclasses implement hashCode method.
   */
  @Override
  public int hashCode() {
    throw new RuntimeException(this.getClass().getName() + "doesn't implement hashCode");
  }
}
