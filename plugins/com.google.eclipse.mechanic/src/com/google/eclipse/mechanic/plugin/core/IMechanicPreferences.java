/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.core;

import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.Task;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;

import java.util.List;
import java.util.Set;

/**
 * Interface for mechanic preferences.
 */
@SuppressWarnings("deprecation") // Uses the old-style API.
public interface IMechanicPreferences {
  public static final String DIRS_PREF = "mechanicSourceDirectories";

  public static final String BLOCKED_PREF = "blockedTaskIds";

  public static final String SLEEPAGE_PREF = "sleepSeconds";

  public static final String HELP_URL_PREF = "helpUrl";

  public static final String SHOW_POPUP_PREF = "showPopup";

//  /**
//   * Preference string to enable web content caching.
//   *
//   * <p>Stores a boolean.
//   */
//  public static final String CACHE_URI_CONTENT_PREF = "cacheUriContent";

//  /**
//   * Preference string that defines the maximum lifetime of web cache entries, in hours.
//   *
//   * <p>Stores an integer.
//   */
//  public static final String CACHE_URI_AGE_HOURS_PREF = "cacheUriAgeHours";

  /**
   * Minimum duration between tasks, in seconds.
   */
  public static final int MINIMUM_SLEEP_SECONDS = 10;

  void addListener(IPropertyChangeListener listener);

  void removeListener(IPropertyChangeListener listener);

  /**
   * Return a list of task sources where tasks may be found.
   *
   * @return list of task sources where tasks may be found.
   */
  List<IResourceTaskProvider> getTaskProviders();

  /**
   * Returns the number of seconds the mechanic should sleep between passes.
   */
  int getThreadSleepSeconds();

  /**
   * Ensures the supplied sleep duration falls in an acceptable range.
   */
  int cleanSleepSeconds(int seconds);

    /**
   * Returns a mutable set of blocked Task ids.
   */
  Set<String> getBlockedTaskIds();

  /**
   * Saves the supplied Task id set in the preferences system.
   */
  void setBlockedTaskIds(Set<String> ids);

  /**
   * Adds the supplied Task's id to the set of blocked Tasks.
   */
  void blockItem(Task item);

  /**
   * Returns the mechanic help url.
   */
  String getHelpUrl();

  boolean contains(String key);

  /**
   * returns the value of given key as a int.
   */
  int getInt(String key);

  /**
   * returns the value of given key as a long.
   */
  long getLong(String key);

  /**
   * Set the long value of a preference on the MechanicPreferences scope.
   */
  void setLong(String key, long value);

  /**
   * returns the value of given key as a string.
   */
  String getString(String key);

  /**
   * Set the string value of a preference on the MechanicPreferences scope.
   */
  void setString(String key, String value);

  /**
   * Return;@code true} if the notification popup should be shown when
   * tasks fail.
   */
  boolean isShowPopup();

  /**
   * Disable the preference that shows the notification popup.
   */
  void doNotShowPopup();

  /**
   * Enable preference that shows the notification popup.
   *
   * <p>For tests only.
   */
  void showPopup();

//  /**
//   * Return {@code true} if web caching is enabled.
//   */
//  public static boolean isWebCacheEnabled() {
//    return preferencesService.getBoolean(
//        MechanicPlugin.PLUGIN_ID, CACHE_URI_CONTENT_PREF, true, null);
//  }

//  /**
//   * Return the age of web cache entries, in hours. Meaningless when {@link
//   * #isWebCacheEnabled()} is {@code false}.
//   */
//  public static int getWebCacheEntryAgeHours() {
//    return preferencesService.getInt(
//        MechanicPlugin.PLUGIN_ID, CACHE_URI_AGE_HOURS_PREF, 0, null);
//  }

  /**
   * Get the validation status of a preferences file.
   */
  IStatus validatePreferencesFile(IPath path);
}
