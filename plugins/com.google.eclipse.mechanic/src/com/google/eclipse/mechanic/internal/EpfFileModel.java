/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import org.eclipse.core.runtime.IPath;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A model of an EPF file.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public class EpfFileModel {
  private final String title;
  private final String description;
  private final TaskType taskType;
  private final Map<String, String> preferences = 
      new HashMap<String, String>();
  
  /**
   * Creates an EPF file model with the given properties.
   * @param title the title of this task. Listed in the @title field.
   * @param description the description of this task. Listed in the 
   *     @description field.
   * @param taskType the type of task to create.
   */
  public EpfFileModel(
      String title, 
      String description, 
      TaskType taskType) {
    this.title = title;
    this.description = description;
    this.taskType = taskType;
  }
  
  /**
   * Adds a preference entry to this file.
   * @param key the preference's key
   * @param value the preference's value
   */
  public void addElement(String key, String value) {
    preferences.put(key, value);
    // TODO(brianchin): Currently, this outputs the scope used for each modification as well
    // as part of the key. I assume this needs to be changed.
  }
  
  /**
   * Write this model to a preference file.
   */
  public void writeFile(IPath location) throws IOException {
    FileOutputStream fileOutput = new FileOutputStream(location.toFile());
    write(fileOutput);
    fileOutput.close();
  }

  private void write(OutputStream outputStream) throws IOException {
    PrintWriter commentPrintWriter = new PrintWriter(outputStream);
    commentPrintWriter.format("# @title %s\n", title);
    commentPrintWriter.format("# @description %s\n", description);
    commentPrintWriter.format("# @audit_type %s\n#\n", taskType.toString());
    commentPrintWriter.println(
        "# Created by the Workspace Mechanic Preference Recorder");
    commentPrintWriter.flush();

    // Use java Properties object to output key/value pairs
    
    Properties outputProperties = new Properties();
    
    outputProperties.setProperty("file_export_version", "3.0");
    
    for (Map.Entry<String, String> e : preferences.entrySet()) {
      String value = e.getValue();
      if(value == null) {
        value = "";
      }
      outputProperties.setProperty(e.getKey(), value);
    }

    outputProperties.store(outputStream, null);
  }
}
