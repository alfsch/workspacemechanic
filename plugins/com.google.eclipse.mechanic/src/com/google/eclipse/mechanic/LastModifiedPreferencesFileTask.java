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

import com.google.common.base.Preconditions;
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

  private final IResourceTaskReference taskRef;
  private final File file;

  private final String id;
  private final String key;

  public LastModifiedPreferencesFileTask(IResourceTaskReference taskRef) {
    this.taskRef = taskRef;
    this.file = taskRef.asFile();
    Preconditions.checkArgument(file == null || file.canRead(), file + " must be readable");
    this.id = String.format("%s@%s", getClass().getName(), taskRef.getPath());
    this.key = String.format("%s_lastmod", id);
  }

  /**
   * Returns an id for the specified class and file.
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Fails if prefs have never been imported, or if they have been imported
   * but there is a newer version of the preferences file.
   */
  public boolean evaluate() {
    long previous = MechanicPreferences.getLong(key);
    try {
      return previous > 0L && previous >= taskRef.getLastModified();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void run() {
    // grab the lastmod time just before we import the file
    try {
      long lastmod = taskRef.getLastModified();

      // TODO(konigsberg): validate preferences for URIs by storing the contents locally on disk?

      // Validate preferences
      IStatus validStatus = file != null ?
          MechanicPreferences.validatePreferencesFile(new Path(file.getPath())) :
          Status.OK_STATUS;
      if (validStatus.isOK()) {
        transfer();
        MechanicPreferences.setLong(key, lastmod);
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
