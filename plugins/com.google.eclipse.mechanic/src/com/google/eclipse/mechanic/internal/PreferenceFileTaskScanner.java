/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import com.google.eclipse.mechanic.DirectoryIteratingTaskScanner;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.LastModifiedPreferencesFileTask;
import com.google.eclipse.mechanic.ReconcilingPreferencesTask;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

/**
 * Provides support for treating Elipse "epf" files as first class
 * {@link Task}s. Each EPF file can have a recommended header section
 * consisting of comments with javadoc like tags specifying information
 * about the task to be performed for this file.
 * 
 * <p/>
 * 
 * Example:
 * <pre>
 * # @title Reconcile Uncategorized Google Preferences
 * # @description Reconciles various Google preferences that don't...
 * # @task_type RECONCILE
 * #
 * /instance/some/pref=true
 * /instance/another/pref="not on your life"
 * </pre>
 * 
 * Please note the the simple header parser doesn't support line wrapping. So
 * you can wrap your lines, but the parser will ignore them.
 * 
 * <p/>
 * 
 * Valid values for the 'title' and 'description' field should be fairly
 * obvious. Valid values for the 'task_type' field are:
 * 
 * <ul>
 *   <li>'LASTMOD' - The task will be triggered based on the last modified time
 *   of the file.</li>
 *   <li>'RECONCILE' - The task will be triggered if any of the preferences in
 *   running eclipse instance differ in value from the preferences in the file.
 * </ul>
 * 
 * @author smckay@google.com (Steve McKay)
 */
public final class PreferenceFileTaskScanner extends DirectoryIteratingTaskScanner {
  
  private static final MechanicLog log = MechanicLog.getDefault();
  private static final Logger LOG = Logger.getLogger(
      PreferenceFileTaskScanner.class.getName());

  /**
   * Adds tasks to the supplied collector.
   */
  @Override
  protected void scan(IResourceTaskProvider source, TaskCollector collector) {
    /**
     * Scan our source. Add a new Task for each EPF found.
     */
    for (IResourceTaskReference taskRef : source.getTaskReferences(".epf")) {
      try {
        LOG.fine(String.format("Loading preferences from: %s", taskRef));
  
        // will throw a RuntimeException in the event of a problem reading
        // the epf file
        Header header = new EpfTaskHeaderParser(taskRef).parseHeader();
        if (header.getType().equals(TaskType.LASTMOD)) {
          collector.add(new LastmodEpfTask(taskRef, header));
        } else if (header.getType().equals(TaskType.RECONCILE)) {
          collector.add(new ReconcilingEpfTask(taskRef, header));
        } else {
          throw new IllegalStateException("Unsupported Task Type");
        }
      } catch(RuntimeException e) {
        log.logError(e, "Loading preferences from: %s", taskRef);
      }
    }
  }

  /**
   * Reconciles preferences in the underlying Eclipse preferences system
   * with the preference values declared in the supplied file.
   */
  private static final class ReconcilingEpfTask
      extends ReconcilingPreferencesTask {

    private final Header header;

    public ReconcilingEpfTask(IResourceTaskReference taskRef, Header header) {
      super(taskRef);
      this.header = header;
    }

    public String getTitle() {
      return header.getTitle();
    }

    public String getDescription() {
      return header.getDescription();
    }
  }

  /**
   * Imports preferences from supplied file based on the last modified time.
   */
  private static final class LastmodEpfTask
      extends LastModifiedPreferencesFileTask {

    private final Header header;

    public LastmodEpfTask(IResourceTaskReference taskRef, Header header) {
      super(taskRef);
      this.header = header;
    }

    public String getTitle() {
      return header.getTitle();
    }

    public String getDescription() {
      return header.getDescription();
    }
  }

  /**
   * Parses fields out of a leading header in the supplied file. Provides
   * reasonable defaults for when the file does not contain a header
   * or the header does not contain all of the recognized tags.
   */
  static final class EpfTaskHeaderParser implements Header {

    private static final String TITLE_TAG = "@title";
    private static final String DESCRIPTION_TAG = "@description";
    private static final String TYPE_TAG = "@task_type";

    // Please don't use this tag in your EPF tasks.
    // Consider it deprecated, but don't remove it; it's kept for backwards compatibility.
    private static final String OLD_TYPE_TAG = "@audit_type";

    private final IResourceTaskReference taskRef;
    private TaskType type;
    private String title;
    private String description;

    public EpfTaskHeaderParser(IResourceTaskReference taskRef) {
      this.taskRef = taskRef;

      // give the file friendly default values
      type = TaskType.LASTMOD;
      title = "Import preferences from: " + taskRef.getName();
      description = "Imports the preferences from: " + taskRef.getPath();
    }

    Header parseHeader() {
      try {
        return parseHeader(taskRef.newInputStream());
      } catch (IOException e) {
        throw new RuntimeException("Couldn't parse " + taskRef.getPath(), e);
      }
    }

    /**
     * Parses attribute tags out of the epf file header.
     */
    Header parseHeader(InputStream in) {

      BufferedReader reader = null;

      try {

        // do we need to handle closing the file reader?
        reader = new BufferedReader(new InputStreamReader(in));

        for (String line = reader.readLine(); line != null;
            line = reader.readLine()) {  

          // iterate until we find a non-comment line
          if (line.length() > 0 && line.charAt(0) == '#') {
            parseHeaderLine(line);
            continue;
          }
          break;
        }
      } catch (IOException e) {
        throw new RuntimeException(
            "Couldn't read " + taskRef.getPath(), e);
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
      
      return this;
    }

    /**
     * Parses a line from the header of the epf file.
     */
    private void parseHeaderLine(String line) {

      // strip the comment and all whitespace
      line = line.substring(1).trim();

      /*
       * We're looking for javadoc style tags in single line script
       * style comments:
       */

      // check for the title tag
      int i = line.indexOf(TITLE_TAG);
      if (i == 0) {
        title = line.substring(TITLE_TAG.length()).trim();
      }

      // check for the description tag
      i = line.indexOf(DESCRIPTION_TAG);
      if (i == 0) {
        description = line.substring(DESCRIPTION_TAG.length()).trim();
      }

      // check for the type tag
      // Parsing the historical @audit_type tag
      i = line.indexOf(OLD_TYPE_TAG);
      if (i == 0) {
        type = TaskType.valueOf(line.substring(OLD_TYPE_TAG.length()).trim());
      }

      // Checking for new @task_type tag.
      i = line.indexOf(TYPE_TAG);
      if (i == 0) {
        type = TaskType.valueOf(line.substring(TYPE_TAG.length()).trim());
      }
    }

    public TaskType getType() {
      return type;
    }

    public String getDescription() {
      return description;
    }

    public String getTitle() {
      return title;
    }
  }

  /**
   * Facilitates sharing of header info without depending on the parser.
   */
  interface Header {

    public TaskType getType();
    public String getDescription();
    public String getTitle();

  }
}
