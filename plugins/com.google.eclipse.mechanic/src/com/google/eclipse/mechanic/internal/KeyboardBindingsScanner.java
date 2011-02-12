/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.DirectoryIteratingTaskScanner;
import com.google.eclipse.mechanic.KeyboardBindingsTask;
import com.google.eclipse.mechanic.SuffixFileFilter;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.keybinding.KeyBindingsParser;
import com.google.eclipse.mechanic.keybinding.KeyBindingsTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

/**
 * Scanner for keyboard bindings.
 *
 * @author zorzella@google.com (Luiz-Otavio Zorzella)
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public class KeyboardBindingsScanner extends DirectoryIteratingTaskScanner {

  private static final FileFilter KBD_FILTER = new SuffixFileFilter(".kbd");

  private static final Logger LOG = Logger.getLogger(
      KeyboardBindingsScanner.class.getName());

  @Override
  public void scan(File dir, TaskCollector collector) {
    /**
     * Scan our target directory. Add a new Task for each EPF file found.
     */
    File[] filesInDir = dir.listFiles(KBD_FILTER);
    if (filesInDir == null) {
      return;
    }
    for (File file : filesInDir) {
      
      LOG.fine(String.format("Loading preference file: %s", file.getPath()));

      // will throw a RuntimeException in the event of a problem reading
      // the kbd file
      Reader reader = toReader(file);
      KeyBindingsTask taskData = KeyBindingsParser.deSerialize(reader);
      collector.add(new KeyboardBindingsTask(taskData));
    }
  }

  private Reader toReader(File file)  {
    try {
      // Why do I have to write this - AGAIN.
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      InputStreamReader reader = new InputStreamReader(bis);
      return new BufferedReader(reader);
    } catch (IOException e) {
      // TODO(konigsberg): We need to build an infrastructure for reporting these errors.
      throw new RuntimeException(e);
    }
  }
}
