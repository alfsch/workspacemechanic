// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.internal.PreferenceFileTaskScanner.*;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Tests for PreferencesFileTaskItemScanner
 * 
 * @author smckay@google.com (Steve McKay)
 */
public class PreferencesFileTaskScannerTest extends TestCase {

  // TODO(konigsberg): generalize and reshare.
  private InputStream getInputStream(String resource) throws IOException {
    Bundle bundle = Platform.getBundle("com.google.eclipse.mechanic.tests");
    URL url = bundle.getEntry(resource);
    if (url != null) {
      return url.openStream();
    }
    return null;
  }

  private EpfTaskHeaderParser parser;
  private InputStream in;
  private Header header; 

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    // The File argument is actually ignored.
    parser = new EpfTaskHeaderParser(new File("/foo/123.epf"));
  }

  @Override
  protected void tearDown() throws Exception {
    in.close();
    super.tearDown();
  }
    
  /*
  # @title Import stuff.
  # @description I'm a lastmod import.
  # @audit_type LASTMOD
  */
  public void testHeaderParsing_lastmod() throws Exception {
    in = getInputStream("testdata/lastmod.epf");

    header = parser.parseHeader(in);
    assertEquals(TaskType.LASTMOD, header.getType());
    assertEquals("Import stuff.", header.getTitle());
    assertEquals("I'm a lastmod import.", header.getDescription());
  }

  /*
  # @title I'm a reconciler task.
  # @description Reconciles some stuff...
  # @audit_type RECONCILE
  */
  public void testHeaderParsing_reconciler() throws Exception {
    in = getInputStream("testdata/reconciler.epf");

    header = parser.parseHeader(in);
    assertEquals(TaskType.RECONCILE, header.getType());
    assertEquals("I'm a reconciler task.", header.getTitle());
    assertEquals("Reconciles some stuff...", header.getDescription());
  }

  public void testHeaderParsing_vanilla() throws Exception {
    in = getInputStream("testdata/vanilla.epf");

    // vanilla.epf has no title or description so it uses
    // the values from 789.epf (even though it's a nonexistent file)
    parser = new EpfTaskHeaderParser(new File("/foo/789.epf"));
    header = parser.parseHeader(in);
    assertEquals(TaskType.LASTMOD, header.getType());
    assertEquals("Import preferences from file: 789.epf", header.getTitle());
    assertEquals("Imports the preferences from the file: /foo/789.epf",
        header.getDescription());
  }

  /**
   * Testing that @task_type is processed.
   */
  public void testHeaderParsing_tasktype_lastmod() throws Exception {

    in = getInputStream("testdata/tasktype_lastmod.epf");

    header = parser.parseHeader(in);
    assertEquals(TaskType.LASTMOD, header.getType());
    assertEquals("Import stuff.", header.getTitle());
    assertEquals("I'm a lastmod import.", header.getDescription());
  }

  /**
   * Testing that @task_type is processed.
   */
  public void testHeaderParsing_tasktype_reconcile() throws Exception {
    in = getInputStream("testdata/tasktype_reconciler.epf");

    header = parser.parseHeader(in);
    assertEquals(TaskType.RECONCILE, header.getType());
    assertEquals("I'm a reconciler task.", header.getTitle());
    assertEquals("Reconciles some stuff...", header.getDescription());
  }
}
