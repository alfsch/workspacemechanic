/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.core;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.internal.BlockedTaskIdsParser;
import com.google.eclipse.mechanic.internal.FileTaskProvider;
import com.google.eclipse.mechanic.internal.ResourceTaskProviderParser;
import com.google.eclipse.mechanic.internal.UriCaches;
import com.google.eclipse.mechanic.internal.UriTaskProvider;
import com.google.eclipse.mechanic.internal.Util;

/**
 * Class used to initialize and access various plugin related preference values.
 *
 * <p> API Note: I tried using the newer API (as the Preferences API was
 * deprecated, but I had such a bizarre issue? As evidenced by the test
 * in MechanicPreferencesTest.testWithFunnyKey.
 */
@SuppressWarnings("deprecation") // Uses the old-style API.
public class MechanicPreferences {
 
  /**
   * Returns the plugin preferences. Just a convenience method.
   */
  private static Preferences getPreferences() {
    return MechanicPlugin.getDefault().getPluginPreferences();
  }

  private static final MechanicLog log = MechanicLog.getDefault();

  /**
   * Preference string for directories containing tasks.
   *
   * Stores a list of strings separated by the platform's file separator, or as a GSON array of
   * URIs.
   */
  public static final String DIRS_PREF = "mechanicSourceDirectories";

  /**
   * Preference string for blocked task ids.
   *
   * <p>Stores a list of strings separated by ???.
   */
  public static final String BLOCKED_PREF = "blockedTaskIds";

  /**
   * Preference string for sleep duration between scans.
   *
   * <p>Stores an integer value.
   */
  public static final String SLEEPAGE_PREF = "sleepSeconds";

  /**
   * Preference string for Help URL.
   *
   * <p>Stores a String value.
   */
  public static final String HELP_URL_PREF = "helpUrl";

  /**
   * Preference string to choose when to show the popup when the mechanic status is "failed".
   *
   * <p>Stores a boolean.
   */
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

  public static void addListener(IPropertyChangeListener listener) {
    Preferences prefs = getPreferences();
    prefs.addPropertyChangeListener(listener);
  }

  public static void removeListener(IPropertyChangeListener listener) {
    Preferences prefs = getPreferences();
    prefs.removePropertyChangeListener(listener);
  }

  // CHM used for thread-safe map.
  private static final ConcurrentMap<String, String> sourcesFailingInitialization = Maps.newConcurrentMap();

  /**
   * Return a list of task sources where tasks may be found.
   *
   * @return list of task sources where tasks may be found.
   */
  public static List<ResourceTaskProvider> getTaskProviders() {
    String paths = getString(DIRS_PREF);

    ResourceTaskProviderParser parser = new ResourceTaskProviderParser();
    List<ResourceTaskProvider> providers = Lists.newArrayList();
    for (String source : parser.parse(paths)) {
      ResourceTaskProvider provider = toProvider(source);
      if (provider != null) {
        IStatus initializationStatus = provider.initialize();
        if (initializationStatus.isOK()) {
          providers.add(provider);
          sourcesFailingInitialization.remove(source);
        } else {
          if (!sourcesFailingInitialization.containsKey(source)) {
            sourcesFailingInitialization.put(source, source);
            log.log(initializationStatus);
          }
        }
      }
    }
    return providers;
  }

  private static ResourceTaskProvider toProvider(String source) {
    URI uri;
    try {
      uri = new URI(source);
      if (uri.getScheme() != null) {
        return new UriTaskProvider(uri, UriCaches.getStateSensitiveCache(),
//             UriCaches.getLifetimeCache());
            UriCaches.getStateSensitiveCache());
      } else {
        return new FileTaskProvider(new File(source));
      }
    } catch (URISyntaxException e) {
      // This is a fall-through for files like C:\path\to\file
      return new FileTaskProvider(new File(source));
    }
  }

  /**
   * Returns the number of seconds the mechanic should sleep between passes.
   */
  public static int getThreadSleepSeconds() {
    int seconds = getInt(SLEEPAGE_PREF);
    return cleanSleepSeconds(seconds);
  }

  /**
   * Ensures the supplied sleep duration falls in an acceptable range.
   */
  public static int cleanSleepSeconds(int seconds) {
    return Math.max(seconds, MINIMUM_SLEEP_SECONDS);
  }

  /**
   * Returns a mutable set of blocked Task ids.
   */
  public static Set<String> getBlockedTaskIds() {
    BlockedTaskIdsParser parser = new BlockedTaskIdsParser();

    String val = getString(BLOCKED_PREF);
    Set<String> set = Sets.newHashSet();
    Collections.addAll(set, parser.parse(val));
    return set;
  }

  /**
   * Saves the supplied Task id set in the preferences system.
   */
  public static void setBlockedTaskIds(Set<String> ids) {
    BlockedTaskIdsParser parser = new BlockedTaskIdsParser();

    String unparse = parser.unparse(ids.toArray(new String[0]));

    Preferences prefs = getPreferences();
    prefs.setValue(BLOCKED_PREF, unparse);
  }

  /**
   * Adds the supplied Task's id to the set of blocked Tasks.
   */
  public static void blockItem(Task item) {
    Set<String> ids = getBlockedTaskIds();
    ids.add(item.getId());
    setBlockedTaskIds(ids);
  }

  /**
   * Returns the mechanic help url.
   */
  public static String getHelpUrl() {
    return getString(HELP_URL_PREF);
  }

  /**
   * returns the value of given key as a int.
   */
  public static int getInt(String key) {
    Preferences prefs = getPreferences();
    return prefs.getInt(key);
  }

  /**
   * returns the value of given key as a long.
   */
  public static long getLong(String key) {
    Preferences prefs = getPreferences();
    return prefs.getLong(key);
  }

  /**
   * Set the long value of a preference on the MechanicPreferences scope.
   */
  public static void setLong(String key, long value) {
    Preferences prefs = getPreferences();
    prefs.setValue(key, value);
  }

  /**
   * returns the value of given key as a string.
   */
  public static String getString(String key) {
    Preferences prefs = getPreferences();
    return prefs.getString(key);
  }

  /**
   * Set the string value of a preference on the MechanicPreferences scope.
   */
  public static void setString(String key, String value) {
    Preferences prefs = getPreferences();
    prefs.setValue(key, value);
  }

  /**
   * Perform variable substitution on a string. Used for translating the task directories,
   * which can contain variables.
   *
   * <p>Comes from the default {@link IStringVariableManager} from the {@link VariablesPlugin}.
   */
  public static String doVariableSubstitution(String input) {
    try {
      IStringVariableManager stringManager =
          VariablesPlugin.getDefault().getStringVariableManager();
      return stringManager.performStringSubstitution(input);
    } catch (CoreException e) {
      return "";
    }
  }

  /**
   * Return {@code true} if the notification popup should be shown when
   * tasks fail.
   */
  public static boolean isShowPopup() {
    Preferences prefs = getPreferences();
    return prefs.getBoolean(SHOW_POPUP_PREF);
  }

  /**
   * Disable the preference that shows the notification popup.
   */
  public static void doNotShowPopup() {
    Preferences prefs = getPreferences();
    prefs.setValue(SHOW_POPUP_PREF, false);
  }

  /**
   * Enable preference that shows the notification popup.
   *
   * <p>For tests only.
   */
  public static void showPopup() {
    Preferences prefs = getPreferences();
    prefs.setValue(SHOW_POPUP_PREF, true);
  }

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
  public static IStatus validatePreferencesFile(IPath path) {
    return Preferences.validatePreferenceVersions(path);
  }
}
