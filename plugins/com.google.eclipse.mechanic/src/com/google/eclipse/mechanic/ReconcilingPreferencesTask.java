/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Properties;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.google.common.io.Closeables;

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
  @Override
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


  /**
   * Adds a new reconciler for each preference line found in the file
   * @throws RuntimeException if any files are not found.
   */
  private void initReconcilers_oldFashioned() {

    BufferedReader reader = null;
    
    try {
      reader = new BufferedReader(new InputStreamReader(taskRef.newInputStream(), "ISO-8859-1"));
      for (String line = reader.readLine(); line != null;
          line = reader.readLine()) {

        line = line.trim();

        // if the line starts with a slash, we treat it as a preference
        if (line.length() > 0 && line.charAt(0) == '/') {
          addReconciler(createReconciler(line));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(
          "Couldn't read " + taskRef.getPath(), e);
    } finally {
      Closeables.closeQuietly(reader);
    }
  }
}
