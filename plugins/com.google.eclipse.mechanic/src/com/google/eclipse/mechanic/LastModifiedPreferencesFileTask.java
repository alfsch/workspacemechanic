/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;

import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

/**
 * Imports Eclipse preferences from a file. Preferences are in the
 * form of an Eclipse prefs export.
 *
 * @author smckay@google.com (Steve McKay)
 */
public abstract class LastModifiedPreferencesFileTask extends CompositeTask {
  private final MechanicLog log = MechanicLog.getDefault();

  private final IPath file;

  public LastModifiedPreferencesFileTask(IPath file) {

    Util.checkArgument(file.toFile().canRead(),
        file + " must be readable");

    this.file = file;
  }

  private final String getKey() {
    return String.format("%s_lastmod", getId());
  }

  /**
   * Returns an id for the specified class and file.
   */
  @Override
  public String getId() {
    return String.format("%s@%s",
        getClass().getName(),
        file.toFile().getPath());
  }

  /**
   * Fails if prefs have never been imported, or if they have been imported
   * but there is a newer version of the preferences file.
   */
  public boolean evaluate() {
    long previous = MechanicPreferences.getLong(getKey());
    return previous > 0L && previous >= file.toFile().lastModified();
  }

  public void run() {
    // grab the lastmod time just before we import the file
    long lastmod = file.toFile().lastModified();
    try {
      // Validate preferences
      IStatus validStatus = MechanicPreferences.validatePreferencesFile(file);
      if (validStatus.isOK()) {
        transfer();
        MechanicPreferences.setValue(getKey(), lastmod);
      } else {
        throw new CoreException(validStatus);
      }
    } catch (CoreException e) {
      throw new RuntimeException("Couldn't import preferences.", e);
    }
  }
  
  /**
   * Applies preferences from a export file using IPreferenceFilter such that other prefs don't
   * get removed. This allows pref fragment files to do the right thing.
   */
  private void transfer() {
    InputStream input = null;
    try {
      input = new BufferedInputStream(new FileInputStream(file.toFile()));
    } catch (FileNotFoundException ex) {
      log.logError(ex);
    }

    IPreferenceFilter[] filters = new IPreferenceFilter[1];
    // Matches all prefs for both Instance and Config scope.
    filters[0] = new IPreferenceFilter() {
        public String[] getScopes() {
            return new String[] {
                InstanceScope.SCOPE,
                ConfigurationScope.SCOPE
            };
        }

        @SuppressWarnings("rawtypes") // Eclipse doesn't do generics.
        public Map getMapping(String scope) {
            return null;
        }
    };

    IPreferencesService service = Platform.getPreferencesService();
    try {
      IExportedPreferences prefs = service.readPreferences(input);
      // Apply the prefs with the filters so they're only imported and others aren't removed.
      service.applyPreferences(prefs, filters);
    } catch (CoreException ex) {
      log.log(ex.getStatus());
    }
    
  }
}
