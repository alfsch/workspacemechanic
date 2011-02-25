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

import org.eclipse.core.internal.preferences.PreferencesService;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.internal.FileTaskProvider;
import com.google.eclipse.mechanic.internal.ResourceTaskProviderParser;
import com.google.eclipse.mechanic.internal.UriCaches;
import com.google.eclipse.mechanic.internal.UriTaskProvider;
import com.google.eclipse.mechanic.internal.Util;

/**
 * Class used to initialize and access various plugin related preference values.
 *
 * @author smckay@google.com (Steve McKay)
 */
@SuppressWarnings("restriction") // PreferencesService is the only access to validateVersions.
public class MechanicPreferences {
  private static final IPreferencesService preferencesService = Platform.getPreferencesService();
  private static final IEclipsePreferences pluginPreferences =
      new InstanceScope().getNode(MechanicPlugin.PLUGIN_ID);

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

  /**
   * Preference string to enable web content caching.
   *
   * <p>Stores a boolean.
   */
  public static final String CACHE_URI_CONTENT_PREF = "cacheUriContent";

  /**
   * Preference string that defines the maximum lifetime of web cache entries, in hours.
   *
   * <p>Stores an integer.
   */
  public static final String CACHE_URI_AGE_HOURS_PREF = "cacheUriAgeHours";

  /**
   * Minimum duration between tasks, in seconds.
   */
  public static final int MINIMUM_SLEEP_SECONDS = 10;

  public static void addListener(IPreferenceChangeListener listener) {
    pluginPreferences.addPreferenceChangeListener(listener);
  }

  public static void removeListener(IPreferenceChangeListener listener) {
    pluginPreferences.removePreferenceChangeListener(listener);
  }

  private static final Set<String> ignoredStrings = Util.newHashSet();
  private static void addIgnoredString(String s) {
    synchronized(ignoredStrings) {
      ignoredStrings.add(s);
    }
  }
  private static boolean isIgnoredString(String s) {
    synchronized(ignoredStrings) {
      return ignoredStrings.contains(s);
    }
  }

  /**
   * Return a list of task sources where tasks may be found.
   *
   * @return list of task sources where tasks may be found.
   */
  public static List<ResourceTaskProvider> getTaskProviders() {
    String paths = preferencesService.getString(MechanicPlugin.PLUGIN_ID, DIRS_PREF, null, null);

    // Create static default parser.
    ResourceTaskProviderParser parser = new ResourceTaskProviderParser();
    List<ResourceTaskProvider> providers = Util.newArrayList();
    for (String source : parser.parse(paths)) {
      if (isIgnoredString(source)) {
        continue;
      }
      URI uri;
      try {
        uri = new URI(source);
      } catch (URISyntaxException e) {
        log.logError(e, "Can't parse %s", source);
        addIgnoredString(source);
        continue;
      }
      ResourceTaskProvider provider;
      if (uri.getScheme() != null) {
        provider = new UriTaskProvider(uri, UriCaches.getStateSensitiveCache(),
            UriCaches.getLifetimeCache());
      } else {
        provider = new FileTaskProvider(new File(source));
      }

      IStatus initializationStatus = provider.initialize();
      if (initializationStatus.isOK()) {
        providers.add(provider);
      } else {
        log.log(initializationStatus);
      }
    }
    return providers;
  }

  /**
   * Returns the number of seconds the mechanic should sleep between passes.
   */
  public static int getThreadSleepSeconds() {
    int seconds = preferencesService.getInt(MechanicPlugin.PLUGIN_ID, SLEEPAGE_PREF, 0, null);
    return cleansSleepSeconds(seconds);
  }

  /**
   * Ensures the supplied sleep duration falls in an acceptable range.
   */
  public static int cleansSleepSeconds(int seconds) {
    return Math.max(seconds, MINIMUM_SLEEP_SECONDS);
  }

  /**
   * Returns a list of Task ids that have been blocked.
   */
  public static Set<String> getBlockedTaskIds() {
    String val = preferencesService.getString(MechanicPlugin.PLUGIN_ID, BLOCKED_PREF, null, null);

    if (val != null) {
      Set<String> set = Util.newHashSet();
      Collections.addAll(set, Util.split(val, File.pathSeparator));
      return set;
    }
    return Collections.emptySet();
  }

  /**
   * Saves the supplied Task id set in the preferences system.
   */
  public static void setBlockedTaskIds(Set<String> ids) {
    StringBuilder b = new StringBuilder();

    for (String id : ids) {
      if (id.contains(File.pathSeparator)) {
        throw new IllegalStateException(
            String.format("ids cannot contain '%s'", File.pathSeparator));
      }
      if (b.length() > 0) {
        b.append(File.pathSeparatorChar);
      }
      b.append(id);
    }
    pluginPreferences.put(BLOCKED_PREF, b.toString());
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
    return preferencesService.getString(MechanicPlugin.PLUGIN_ID, HELP_URL_PREF, null, null);
  }

  /**
   * returns the value of given key as a long.
   */
  public static long getLong(String key) {
    return preferencesService.getLong(MechanicPlugin.PLUGIN_ID,
        key, 0L, null);
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
    return preferencesService.getBoolean(MechanicPlugin.PLUGIN_ID, SHOW_POPUP_PREF, true, null);
  }

  /**
   * Disable the preference that shows the notification popup.
   */
  public static void doNotShowPopup() {
    pluginPreferences.putBoolean(SHOW_POPUP_PREF, false);
  }

  /**
   * Return {@code true} if web caching is enabled.
   */
  public static boolean isWebCacheEnabled() {
    return preferencesService.getBoolean(
        MechanicPlugin.PLUGIN_ID, CACHE_URI_CONTENT_PREF, true, null);
  }

  /**
   * Return the age of web cache entries, in hours. Meaningless when {@link
   * #isWebCacheEnabled()} is {@code false}.
   */
  public static int getWebCacheEntryAgeHours() {
    return preferencesService.getInt(
        MechanicPlugin.PLUGIN_ID, CACHE_URI_AGE_HOURS_PREF, 0, null);
  }

  /**
   * Get the validation status of a preferences file.
   */
  public static IStatus validatePreferencesFile(IPath path) {
    return ((PreferencesService) preferencesService).validateVersions(path);
  }

  /**
   * Set the value of a preference on the MechanicPreferences scope.
   */
  public static void setValue(String key, long value) {
    pluginPreferences.putLong(key, value);
  }
}
