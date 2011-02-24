/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.util.concurrent.TimeUnit;

import com.google.eclipse.mechanic.MechanicService;

/**
 * Web content caches.
 */
public class UriCaches {
  private static final StateSensitiveCache stateSensitive =
      new StateSensitiveCache(MechanicService.getInstance());
  private static final IUriContentProvider lifetime =
      new ThreadsafeUriContentCache(12, TimeUnit.HOURS);

  public static void initialize() {
    stateSensitive.initialize();
  }

  public static void destroy() {
    stateSensitive.dispose();
  }

  /**
   * Get the content provider whose cache contents are cleared when the Mechanic Service
   * starts updating.
   */
  public static IUriContentProvider getStateSensitiveCache() {
    return stateSensitive;
  }

  /**
   * Get the content provider whose cache contents are cleared every 12 hours.
   */
  public static IUriContentProvider getLifetimeCache() {
    return lifetime;
  }
}
