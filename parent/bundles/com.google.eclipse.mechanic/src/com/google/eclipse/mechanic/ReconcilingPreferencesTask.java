/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * Models an Eclipse preferences export file as a series of individual
 * preference reconcilers. If any of the reconcilers need fixing, they
 * get fixed.
 * 
 * @author smckay@google.com (Steve McKay)
 */
public abstract class ReconcilingPreferencesTask extends PreferenceReconcilerTask {

  private final IResourceTaskReference taskRef;

  public ReconcilingPreferencesTask(
      IResourceTaskReference taskRef, IEclipsePreferences prefsRoot) throws IOException {
    super(prefsRoot);
    this.taskRef = taskRef;
    initReconcilers();
  }

  public ReconcilingPreferencesTask(IResourceTaskReference taskRef) throws IOException {
    this.taskRef = taskRef;
    initReconcilers();
  }

  /**
   * Returns an id for the specified class and file.
   */
  public String getId() {
    return String.format("%s@%s", getClass().getName(), taskRef.getPath());
  }

  /**
   * Adds a new reconciler for each preference line found in the file
   * @throws IOException if the input stream can't be parsed.
   * @throws RuntimeException if any files are not found.
   */
  private void initReconcilers() throws IOException {
    // If it's good enough for Eclipse to use the Properties object to read preference files,
    // then it's good enough for us.
    Properties properties = new Properties();
    properties.load(taskRef.newInputStream());
    for (Entry<Object, Object> entry : properties.entrySet()) {
      String id = (String) entry.getKey();
      // Ignore entries like "file_export_version=3.0"
      if (!id.isEmpty() && id.charAt(0) == '/') {
        String value = (String) entry.getValue();
        addReconciler(createReconciler(id, value));
      }
    }
  }
}
