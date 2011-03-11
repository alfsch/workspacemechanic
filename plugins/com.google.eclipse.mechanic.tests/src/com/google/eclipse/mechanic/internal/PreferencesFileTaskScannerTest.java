/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.internal.PreferenceFileTaskScanner.EpfTaskHeaderParser;
import com.google.eclipse.mechanic.internal.PreferenceFileTaskScanner.Header;
import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for PreferencesFileTaskItemScanner
 * 
 * @author smckay@google.com (Steve McKay)
 */
@RunAsJUnitTest
public class PreferencesFileTaskScannerTest extends TestCase {

  private static final String LASTMOD_EPF = join(
      "# @title Import stuff.",
      "# @description I'm a lastmod import.",
      "# @audit_type LASTMOD",
      "file_export_version=3.0",
      "# misc ui settings",
      "/instance/org.eclipse.ui/SHOW_TEXT_ON_PERSPECTIVE_BAR=false",
      "/instance/org.eclipse.ui/DOCK_PERSPECTIVE_BAR=left");

  private static final String RECONCILER_EPF = join(
      "# @title I'm a reconciler task.",
      "# @description Reconciles some stuff...",
      "# @audit_type RECONCILE",
      "file_export_version=3.0",
      "# misc ui settings",
      "/instance/org.eclipse.ui/SHOW_TEXT_ON_PERSPECTIVE_BAR=false",
      "/instance/org.eclipse.ui/DOCK_PERSPECTIVE_BAR=left");

  private static final String VANILLA_EPF = join(
      "file_export_version=3.0",
      "/instance/org.eclipse.ui/SHOW_TEXT_ON_PERSPECTIVE_BAR=false",
      "/instance/org.eclipse.ui/DOCK_PERSPECTIVE_BAR=left",
      "/instance/org.eclipse.ui/showIntro=false",
      "# howdy");

  private static final String TASKTYPE_LASTMOD_EPF = join(
      "# @title Import stuff.",
      "# @description I'm a lastmod import.",
      "# @task_type LASTMOD",
      "file_export_version=3.0",
      "# misc ui settings",
      "/instance/org.eclipse.ui/SHOW_TEXT_ON_PERSPECTIVE_BAR=false",
      "/instance/org.eclipse.ui/DOCK_PERSPECTIVE_BAR=left");

  private static final String TASKTYPE_RECONCILER_EPF = join(
      "# @title I'm a reconciler task.",
      "# @description Reconciles some stuff...",
      "# @task_type RECONCILE",
      "file_export_version=3.0",
      "# misc ui settings",
      "/instance/org.eclipse.ui/SHOW_TEXT_ON_PERSPECTIVE_BAR=false",
      "/instance/org.eclipse.ui/DOCK_PERSPECTIVE_BAR=left");

  private static String join(String... lines) {
    return Util.join("\n", lines);
  }

  private InputStream getInputStream(String text) {
    return new ByteArrayInputStream(text.getBytes());
  }

  private IResourceTaskReference taskRef;
  private EpfTaskHeaderParser parser;
  private InputStream in;
  private Header header; 

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    taskRef = createMock(IResourceTaskReference.class);
    expect(taskRef.getName()).andReturn("123.epf").anyTimes();
    expect(taskRef.getPath()).andReturn("/foo/123.epf").anyTimes();
    replay(taskRef);
    // The argument is actually ignored.
    parser = new EpfTaskHeaderParser(taskRef);
  }

  @Override
  protected void tearDown() throws Exception {
    in.close();
    verify(taskRef);
    super.tearDown();
  }
    
  /*
  # @title Import stuff.
  # @description I'm a lastmod import.
  # @audit_type LASTMOD
  */
  public void testHeaderParsing_lastmod() throws Exception {
    in = getInputStream(LASTMOD_EPF);

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
    in = getInputStream(RECONCILER_EPF);

    header = parser.parseHeader(in);
    assertEquals(TaskType.RECONCILE, header.getType());
    assertEquals("I'm a reconciler task.", header.getTitle());
    assertEquals("Reconciles some stuff...", header.getDescription());
  }

  public void testHeaderParsing_vanilla() throws Exception {
    in = getInputStream(VANILLA_EPF);

    // vanilla.epf has no title or description so it uses
    // values from the taskRef.
    parser = new EpfTaskHeaderParser(taskRef);
    header = parser.parseHeader(in);
    assertEquals(TaskType.LASTMOD, header.getType());
    assertEquals("Import preferences from: 123.epf", header.getTitle());
    assertEquals("Imports the preferences from: /foo/123.epf",
        header.getDescription());
  }

  /**
   * Testing that @task_type is processed.
   */
  public void testHeaderParsing_tasktype_lastmod() throws Exception {

    in = getInputStream(TASKTYPE_LASTMOD_EPF);

    header = parser.parseHeader(in);
    assertEquals(TaskType.LASTMOD, header.getType());
    assertEquals("Import stuff.", header.getTitle());
    assertEquals("I'm a lastmod import.", header.getDescription());
  }

  /**
   * Testing that @task_type is processed.
   */
  public void testHeaderParsing_tasktype_reconcile() throws Exception {
    in = getInputStream(TASKTYPE_RECONCILER_EPF);

    header = parser.parseHeader(in);
    assertEquals(TaskType.RECONCILE, header.getType());
    assertEquals("I'm a reconciler task.", header.getTitle());
    assertEquals("Reconciles some stuff...", header.getDescription());
  }
}
