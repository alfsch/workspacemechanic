/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;

/**
 * Writes {@link EpfFileModel}.
 */
public class EpfFileModelWriter {
  /**
   * Write the model to a preference file.
   */
  public static void write(EpfFileModel model, IPath location) throws IOException {
    FileOutputStream fileOutput = new FileOutputStream(location.toFile());
    write(model, fileOutput);
    fileOutput.close();
  }

  public static void write(EpfFileModel model, OutputStream outputStream) throws IOException {
    PrintWriter commentPrintWriter = new PrintWriter(outputStream);
    commentPrintWriter.format("# @title %s%n", model.getTitle());
    commentPrintWriter.format("# @description %s%n", model.getDescription());
    commentPrintWriter.format("# @task_type %s%n#%n", model.getTaskType().toString());
    commentPrintWriter.println(
        "# Created by the Workspace Mechanic Preference Recorder");
    commentPrintWriter.flush();

    // Use java Properties object to output key/value pairs
    
    Properties outputProperties = new Properties();
    
    outputProperties.setProperty("file_export_version", "3.0");
    
    for (Map.Entry<String, String> e : model.getPreferences().entrySet()) {
      String value = e.getValue();
      if(value == null) {
        value = "";
      }
      outputProperties.setProperty(e.getKey(), value);
    }

    outputProperties.store(outputStream, null);
  }

}
