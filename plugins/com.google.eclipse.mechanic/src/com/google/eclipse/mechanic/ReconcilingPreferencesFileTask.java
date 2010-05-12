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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Models an Eclipse preferences export file as a series of individual
 * preference reconcilers. If any of the reconcilers need fixing, they
 * get fixed.
 * 
 * @author smckay@google.com (Steve McKay)
 */
public abstract class ReconcilingPreferencesFileTask
    extends PreferenceReconcilerTask {

  private final File file;

  public ReconcilingPreferencesFileTask(File file) {
    this.file = file;
    initReconcilers();
  }

  /**
   * Returns an id for the specified class and file.
   */
  @Override
  public String getId() {
    return String.format("%s@%s", getClass().getName(), file.getPath());
  }

  /**
   * Adds a new reconciler for each preference line found in the file
   * @throws RuntimeException if any files are not found.
   */
  private void initReconcilers() {

    BufferedReader reader = null;
    
    try {
      reader = new BufferedReader(new FileReader(file));
  
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
          "Couldn't read file: " + file.getPath(), e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          throw new RuntimeException("Error occured while trying to " +
              "cleanup from another error. Life sucks.", e);
        }
      }
    }
  }
}
