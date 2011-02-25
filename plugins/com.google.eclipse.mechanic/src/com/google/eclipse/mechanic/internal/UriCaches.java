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

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;

import com.google.eclipse.mechanic.MechanicService;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

/**
 * Web content caches.
 */
public class UriCaches {
  // This content provider uses the cache above, and clears the entries when the mechanic status
  // changes to Updating. It's the one used to fetch URL task metadata. This is the cache from
  // which UriTaskProviderModel instances come from.
  private static StateSensitiveCache stateSensitive = null;

  // This provider is used for fetching .epf and other web resources.
  private static IUriContentProvider lifetime = null;

  // Since |stateSensitive| and |lifetime| can change over time they
  // can't be held on to by outside clients. These two proxies are
  // passed to the callers and can be kept forever.
  private static final ProxyUriContentProvider stateSensitiveProxy =
        new ProxyUriContentProvider(null);
  private static final ProxyUriContentProvider lifetimeProxy =
      new ProxyUriContentProvider(null);

  private static final IPreferenceChangeListener listener =
      new IPreferenceChangeListener() {
    public void preferenceChange(PreferenceChangeEvent event) {
      if (MechanicPreferences.CACHE_URI_AGE_HOURS_PREF.equals(event.getKey()) ||
          MechanicPreferences.CACHE_URI_CONTENT_PREF.equals(event.getKey())) {
        resetCaches();
      }
    }
  };

  public static void initialize() {
    // This content provider fetches URIs from the web using uri.toURL.openStream();
    IUriContentProvider standardProvider = new StandardContentProvider();

    // This content provider fetches from the web and caches the results until |clear| is called.
    IUriContentProvider threadsafeUriContentCache =
        new ThreadsafeUriContentCache(0, TimeUnit.MILLISECONDS, standardProvider);

    stateSensitive = new StateSensitiveCache(
        MechanicService.getInstance(), threadsafeUriContentCache);
    stateSensitiveProxy.set(stateSensitive);

    boolean isEnabled = MechanicPreferences.isWebCacheEnabled();
    int ageHours = MechanicPreferences.getWebCacheEntryAgeHours();
    if (isEnabled) {
      lifetime = new ThreadsafeUriContentCache(ageHours, TimeUnit.HOURS, standardProvider);
    } else {
      lifetime = standardProvider;
    }
    lifetimeProxy.set(lifetime);
    stateSensitive.initialize();

    MechanicPreferences.addListener(listener);
  }

  private static void resetCaches() {
    clear();
    initialize();
  }

  public static void clear() {
    lifetime.clear();
    stateSensitive.dispose();
  }

  public static void destroy() {
    clear();
    MechanicPreferences.removeListener(listener);
  }

  /**
   * Get the content provider whose cache contents are cleared when the Mechanic Service
   * starts updating.
   */
  public static IUriContentProvider getStateSensitiveCache() {
    return stateSensitiveProxy;
  }

  /**
   * Get the content provider whose cache contents are cleared every 12 hours.
   */
  public static IUriContentProvider getLifetimeCache() {
    return lifetimeProxy;
  }
}
