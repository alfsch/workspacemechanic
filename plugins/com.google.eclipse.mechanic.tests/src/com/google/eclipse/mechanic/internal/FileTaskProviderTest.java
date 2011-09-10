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

import junit.framework.TestCase;

import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for FileTaskProvider.
 */
@RunAsJUnitTest
public class FileTaskProviderTest extends TestCase {

  public void testDir() {
    String tmp = System.getProperty("java.io.tmpdir");
    File f = new File(tmp);
    FileTaskProvider provider = new FileTaskProvider(f);
    assertEquals(f,provider.getFile());
  }

  public void testRelative() throws IOException {
    String tmp = "./foo";
    File f = new File(System.getProperty("user.dir"), tmp);
    FileTaskProvider provider = new FileTaskProvider(new File(tmp));
    assertEquals(f.getCanonicalPath(), provider.getFile().getCanonicalPath());
  }

  public void testParentRelative() throws IOException {
    String tmp = "../foo";
    File f = new File(System.getProperty("user.dir"), tmp);
    FileTaskProvider provider = new FileTaskProvider(new File(tmp));
    assertEquals(f.getCanonicalPath(), provider.getFile().getCanonicalPath());
  }

  public void testUserHome() throws IOException {
    String tmp = "~/foo";
    File f = new File(System.getProperty("user.home"), tmp.substring(2));
    FileTaskProvider provider = new FileTaskProvider(new File(tmp));
    assertEquals(f.getCanonicalPath(), provider.getFile().getCanonicalPath());
  }
}