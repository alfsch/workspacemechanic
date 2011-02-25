/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.core;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Preference initializer.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class MechanicPreferencesInitializer extends AbstractPreferenceInitializer {

  // Sleep for one hour by default between tasks.
  private static final int DEFAULT_SLEEP_SECONDS = 3600;

  private static final String DEFAULT_DIRS = "${user_homedir}/.eclipse/mechanic";

  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = MechanicPlugin.getDefault().getPreferenceStore();
    // store.setDefault(STARTUP_PREF, false);
    store.setDefault(MechanicPreferences.DIRS_PREF, DEFAULT_DIRS);
    store.setDefault(MechanicPreferences.SLEEPAGE_PREF, DEFAULT_SLEEP_SECONDS);
    store.setDefault(MechanicPreferences.HELP_URL_PREF,
        "http://code.google.com/a/eclipselabs.org/p/workspacemechanic/wiki/GettingStarted");
    store.setDefault(MechanicPreferences.SHOW_POPUP_PREF, true);
    store.setDefault(MechanicPreferences.CACHE_URI_CONTENT_PREF, false);
    store.setDefault(MechanicPreferences.CACHE_URI_AGE_HOURS_PREF, 12);
  }
}
