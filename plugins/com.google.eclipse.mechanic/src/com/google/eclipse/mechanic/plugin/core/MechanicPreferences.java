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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.internal.FileTaskProvider;
import com.google.eclipse.mechanic.internal.Util;

/**
 * Class used to initialize and access various plugin related preference values.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class MechanicPreferences {
  private static final ILog log = MechanicPlugin.getDefault().getLog();

  /**
   * Preference string for directories containing tasks.
   *
   * Stores a list of strings separated by the platform's file separator.
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

  public static final int MINIMUM_SLEEP_SECONDS = 10;

  public void addListener(Preferences.IPropertyChangeListener listener) {
    getPreferences().addPropertyChangeListener(listener);
  }

  /**
   * Return a list of task sources.
   *
   * @return list of task sources.
   */
  public static List<ResourceTaskProvider> getTaskSources() {
    Preferences prefs = getPreferences();
    
    String paths = prefs.getString(DIRS_PREF);

    List<ResourceTaskProvider> sources = Util.newArrayList();
    for (String path : Util.split(paths, File.pathSeparator)) {

      FileTaskProvider provider = new FileTaskProvider(new File(doVariableSubstitution(path)));
      
      IStatus initializationStatus = provider.initialize();
      if (initializationStatus.isOK()) {
        sources.add(provider);
      } else {
        log.log(initializationStatus);
      }
    }
    return sources;
  }

  /**
   * Returns the number of seconds the mechanic should sleep between passes.
   */
  public static int getThreadSleepSeconds() {
    return cleansSleepSeconds(getPreferences().getInt(SLEEPAGE_PREF));
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
    Preferences prefs = getPreferences();
    String val = prefs.getString(BLOCKED_PREF);

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
    Preferences prefs = getPreferences();
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
    prefs.setValue(BLOCKED_PREF, b.toString());
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
    Preferences prefs = getPreferences();
    return prefs.getString(HELP_URL_PREF);
  }
  
  /**
   * Returns the plugin preferences without. Just a convenience method.
   */
  private static Preferences getPreferences() {
    return MechanicPlugin.getDefault().getPluginPreferences();
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
}
