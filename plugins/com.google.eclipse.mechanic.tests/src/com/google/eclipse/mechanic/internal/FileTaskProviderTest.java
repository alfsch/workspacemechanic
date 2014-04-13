/*******************************************************************************
 * Copyright (C) 2011, Alex Blewitt
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for FileTaskProvider.
 */
@RunAsJUnitTest
public class FileTaskProviderTest extends TestCase {
  private static final String TMP_ROOT = System.getProperty("java.io.tmpdir");

  private String testDirString = TMP_ROOT + File.separator + "test" + Math.random();
  private File testDir;

  private Properties properties;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    testDir = new File(testDirString);
    mkdir(testDir);
    properties = System.getProperties();
  }

  
  @Override
  protected void tearDown() throws Exception {
    System.setProperties(properties);
    super.tearDown();
  }


  public void testDir() throws Exception {
    FileTaskProvider provider = FileTaskProvider.newInstance(testDir);
    assertEquals(testDir, provider.getFile());
  }

  public void testRelative() throws IOException {
    System.setProperty("user.dir", testDirString);

    String tmp = "./foo";
    File f = new File(testDir, tmp);
    mkdir(f);

    FileTaskProvider provider = FileTaskProvider.newInstance(new File(tmp), properties);
    assertEquals(f.getCanonicalPath(), provider.getFile().getCanonicalPath());
  }

  public void testParentRelative() throws IOException {
    mkdir(new File(testDir, "userdir"));
    mkdir(new File(testDir, "foo"));

    System.setProperty("user.dir", testDirString + File.separator + "userdir");

    String tmp = "../foo";
    File f = new File(testDir, "foo");

    FileTaskProvider provider = FileTaskProvider.newInstance(new File(tmp), properties);
    assertEquals(f.getCanonicalPath(), provider.getFile().getCanonicalPath());
  }

  public void testUserHome() throws IOException {
    System.setProperty("user.home", testDirString);

    String tmp = "~/foo";
    File f = new File(System.getProperty("user.home"), tmp.substring(2));
    mkdir(f);

    FileTaskProvider provider = FileTaskProvider.newInstance(new File(tmp), properties);
    assertEquals(f.getCanonicalPath(), provider.getFile().getCanonicalPath());
  }

  private void mkdir(File dir) {
    if (!dir.mkdir()) {
      fail("Can't make " + testDir);
    }
  }
}