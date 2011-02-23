/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

/**
 * General supplier interface.
 *
 * @param <T> the type of object being supplied.
 */
public interface Supplier<T> {

  // This could be replaced with com.google.common.base.Supplier.

  /**
   * Get a supplied instance of T.
   */
  T get();
}
