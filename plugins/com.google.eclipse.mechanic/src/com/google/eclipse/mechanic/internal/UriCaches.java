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

  // While these static instances are nice, it's probably going to need be dynamic at runtime when
  // someone complains "I don't want content cached for 12 hours!"

  // Here's what this is saying:

  // This content provider fetches URIs from the web using uri.toURL.openStream();
  private static final IUriContentProvider standardProvider = new StandardContentProvider();

  // This content provider fetches from the web and caches the results until |clear| is called.
  private static final IUriContentProvider threadsafeUriContentCache =
      new ThreadsafeUriContentCache(0, TimeUnit.MILLISECONDS, standardProvider);

  // This content provider uses the cache above, and clears the entries when the mechanic status
  // changes to Updating. It's the one used to fetch URL task metadata. This is the cache from
  // which UriTaskProviderModel instances come from.
  private static final StateSensitiveCache stateSensitive =
      new StateSensitiveCache(MechanicService.getInstance(), threadsafeUriContentCache);

  // This cache keeps its entries for 12 hours. It's used for fetching .epf and other files
  // from the web.
  private static final IUriContentProvider lifetime =
      new ThreadsafeUriContentCache(12, TimeUnit.HOURS, standardProvider);

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
