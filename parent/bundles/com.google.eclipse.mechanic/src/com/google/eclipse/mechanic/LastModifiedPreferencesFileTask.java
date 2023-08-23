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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.runtime.preferences.PreferenceFilterEntry;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.plugin.core.IMechanicPreferences;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * Imports Eclipse preferences from a file. Preferences are in the
 * form of an Eclipse prefs export.
 */
// DO NOT RENAME THIS CLASS. IT WILL BREAK ALL IDS.
public abstract class LastModifiedPreferencesFileTask extends CompositeTask {

  private final IResourceTaskReference taskRef;
  private final IMechanicPreferences prefs;
  private final MechanicLog log;

  private final File file;

  private final String id;
  private final String key;
  private final String md5Key;

  public LastModifiedPreferencesFileTask(
      IResourceTaskReference taskRef,
      IMechanicPreferences prefs,
      MechanicLog log) {
    this.taskRef = taskRef;
    this.prefs = prefs;
    this.log = log;
    this.file = taskRef.asFile();
    Preconditions.checkArgument(file == null || file.canRead(), file + " must be readable");
    this.id = String.format("%s@%s", getClass().getName(), taskRef.getPath());
    this.key = String.format("%s_lastmod", id);
    this.md5Key = String.format("%s_lastmd5", id);
  }

  /**
   * Returns an id for the specified class and file.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns {@code true} if the preferences file hasn't changed.
   * Returns {@code false} if prefs have never been imported, or if they have been imported
   * but a new version exists.
   */
  public boolean evaluate() {
    try {
      return taskRef.asFile() == null ? evaluateByMD5() : evaluateByModificationDate();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean evaluateByMD5() throws IOException {
    if (!prefs.contains(md5Key)) {
      return false;
    }
    long previous = prefs.getLong(md5Key);
    long current = taskRef.computeMD5();
    return previous == current;
  }

  private boolean evaluateByModificationDate() throws IOException {
    long previous = prefs.getLong(key);
    long current = taskRef.getLastModified();
    return previous > 0L && previous >= current;
  }

  public void run() {
    // grab the lastmod time just before we import the file
    try {
      long lastmod = taskRef.getLastModified();
      long lastMD5 = taskRef.computeMD5();

      // TODO(konigsberg): validate preferences for URIs by storing the contents locally on disk?

      // Validate preferences
      IStatus validStatus = file != null ?
          prefs.validatePreferencesFile(new Path(file.getPath())) :
          Status.OK_STATUS;
      if (validStatus.isOK()) {
        transfer();
        prefs.setLong(key, lastmod);
        prefs.setLong(md5Key, lastMD5);
      } else {
        throw new CoreException(validStatus);
      }
    } catch (CoreException e) {
      throw new RuntimeException("Couldn't import preferences.", e);
    } catch (IOException e) {
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
      input = new BufferedInputStream(taskRef.newInputStream());
    } catch (IOException e) {
      log.logError(e);
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

        public Map<String, PreferenceFilterEntry[]> getMapping(String scope) {
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
