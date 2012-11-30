/*******************************************************************************
 * Copyright (C) 2012, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import junit.framework.TestCase;

import org.eclipse.core.runtime.ILog;

import com.google.eclipse.mechanic.plugin.core.IMechanicPreferences;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for {@link LastModifiedPreferencesFileTask}.
 */
@RunAsJUnitTest
public class LastModifiedPreferencesFileTaskTest extends TestCase {

  private IResourceTaskReference ref;
  private ILog ilog;
  private IMechanicPreferences prefs;
  private MechanicLog log;


  private static class TestTask extends LastModifiedPreferencesFileTask {
    private final String title;
    private final String description;

    public TestTask(
        IResourceTaskReference taskRef,
        IMechanicPreferences prefs,
        MechanicLog log,
        String title,
        String description) {
      super(taskRef, prefs, log);
      this.title = title;
      this.description = description;
    }

    public String getTitle() {
      return title;
    }

    public String getDescription() {
      return description;
    }
  }

  
  @Override
  protected void setUp() throws Exception {
    ref = mock(IResourceTaskReference.class);
    ilog = mock(ILog.class);
    prefs = mock(IMechanicPreferences.class);
    log = new MechanicLog(ilog);
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
//    verify(ref);
//    verify(ilog);
//    verify(prefs);
    super.tearDown();
  }

  public void testGetId() {
    when(ref.getPath()).thenReturn("/path/to");

    LastModifiedPreferencesFileTask task = new TestTask(ref, prefs, log, "X", "Y");

    assertEquals(
        "com.google.eclipse.mechanic.LastModifiedPreferencesFileTaskTest$TestTask@/path/to",
        task.getId());
  }

  public void testMd5_DoesNotExist() {
    when(ref.getPath()).thenReturn("/path/to");

    String key =
        "com.google.eclipse.mechanic.LastModifiedPreferencesFileTaskTest$TestTask@/path/to_lastmd5";

    // TODO: make sure this is called.
    when(prefs.contains(key)).thenReturn(false);

    LastModifiedPreferencesFileTask task = new TestTask(ref, prefs, log, "X", "Y");

    assertFalse(task.evaluate());
  }

  public void testMd5_ExistsButDoesNotMatch() throws Exception {
    when(ref.getPath()).thenReturn("/path/to");

    String key =
        "com.google.eclipse.mechanic.LastModifiedPreferencesFileTaskTest$TestTask@/path/to_lastmd5";

    // TODO: make sure this is called.
    when(prefs.contains(key)).thenReturn(true);
    when(prefs.getLong(key)).thenReturn(12345L);
    when(ref.computeMD5()).thenReturn(23456L);

    LastModifiedPreferencesFileTask task = new TestTask(ref, prefs, log, "X", "Y");

    assertFalse(task.evaluate());
  }

  public void testMd5_ExistsButMatches() throws Exception {
    when(ref.getPath()).thenReturn("/path/to");

    String key =
        "com.google.eclipse.mechanic.LastModifiedPreferencesFileTaskTest$TestTask@/path/to_lastmd5";

    // TODO: make sure this is called.
    when(prefs.contains(key)).thenReturn(true);
    when(prefs.getLong(key)).thenReturn(12345L);
    when(ref.computeMD5()).thenReturn(12345L);

    LastModifiedPreferencesFileTask task = new TestTask(ref, prefs, log, "X", "Y");

    assertTrue(task.evaluate());
  }
}
