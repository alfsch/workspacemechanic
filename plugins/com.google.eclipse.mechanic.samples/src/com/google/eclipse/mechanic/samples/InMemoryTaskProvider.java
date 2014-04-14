/*******************************************************************************
 * Copyright (C) 2014, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.samples;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;
import com.google.eclipse.mechanic.ICollector;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.IResourceTaskReference;

/**
 * Task provider that uses an in-memory map to provide task data.
 */
public class InMemoryTaskProvider implements IResourceTaskProvider {

  private static final Map<String, String> RESOURCES = Maps.newLinkedHashMap();
  private static final String SHOW_LINE_NUMBERS = Joiner.on("\n").join( 
      "# @title Show Line Numbers",
      "# @description Show line numbers in text editors.",
      "# @audit_type LASTMOD",
      "",
      "file_export_version=3.0",
      "/instance/org.eclipse.ui.editors/lineNumberRuler=true");

  static {
    RESOURCES.put("foo.message", "HELLO");
    RESOURCES.put("showlinenumbers.epf", SHOW_LINE_NUMBERS);
    RESOURCES.put("breakpoint.showview", "org.eclipse.debug.ui.BreakpointView");
    RESOURCES.put("junit.showview", "org.eclipse.jdt.junit.ResultView");
    RESOURCES.put("packageexplorer.showview", "org.eclipse.jdt.ui.PackageExplorer");
  }

  @Override
  public void collectTaskReferences(String extFilter,
      ICollector<IResourceTaskReference> collector) {
    for (String key : RESOURCES.keySet()) {
      if (key.endsWith(extFilter)) {
        collector.collect(new TaskReference(key));
      }
    }
  }

  @Override
  public void collectTaskReferences(String localPath, String extFilter,
      ICollector<IResourceTaskReference> collector) {
    // Not supporting this for the example.
  }

  private class TaskReference implements IResourceTaskReference {
    private final String key;

    public TaskReference(String key) {
      this.key = key;
    }

    @Override
    public IResourceTaskProvider getProvider() {
      return InMemoryTaskProvider.this;
    }

    @Override
    public String getName() {
      return key;
    }

    @Override
    public InputStream newInputStream() throws IOException {
      return new ByteArrayInputStream(RESOURCES.get(key).getBytes());
    }

    @Override
    public long getLastModified() throws IOException {
      return 0;
    }

    @Override
    public String getPath() {
      return "InMemoryTaskProvider:" + key;
    }

    @Override
    public File asFile() {
      return null;
    }

    @Override
    public long computeMD5() throws IOException {
      InputSupplier<InputStream> supplier = new InputSupplier<InputStream>() {
        @Override
        public InputStream getInput() throws IOException {
          return newInputStream();
        }
      };
      return ByteStreams.hash(supplier, Hashing.md5()).asLong();
    }

    @Override
    public String toString() {
      return getClass().toString() + ": " + key;
    }
  }
}
