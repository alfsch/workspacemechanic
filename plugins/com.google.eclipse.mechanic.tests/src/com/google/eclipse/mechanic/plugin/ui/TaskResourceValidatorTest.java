/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.ui;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;
import static com.google.eclipse.mechanic.plugin.ui.TaskResourceValidator.*;

/**
 * Tests for {@link TaskResourceValidator}.
 */
@RunAsJUnitTest
public class TaskResourceValidatorTest extends TestCase {
  private static final TaskResourceValidator ALL_VALIDATOR = new TaskResourceValidator(true);
  private static final TaskResourceValidator PATH_VALIDATOR = new TaskResourceValidator(false);
  private File file;
  File dir;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    file = File.createTempFile("asfd", "fdas");
    dir = File.createTempFile("xxx", "ooo");
    // hack: delete the dir file, and make it a directory.
    assertTrue(dir.delete());
    assertTrue(dir.mkdir());
    chmod(dir, "-r");
  }

  private void chmod(File path, String change) throws InterruptedException, IOException {
    if (!path.isAbsolute()) {
      fail("chmod function requires absolute paths");
    }
    assertEquals(0, new ProcessBuilder("chmod", change, path.getPath()).start().waitFor());
  }

  @Override
  public void tearDown() throws Exception {
    assertTrue(file.delete());
    assertTrue(dir.delete());
    super.tearDown();
  }

  public void testValidation_doesnotexist() {
    validate(new File(file, "fakesubdir"), PATH_DOES_NOT_EXIST, PATH_DOES_NOT_EXIST);
  }

  public void testValidation_file() throws Exception {
    validate(file, NOT_A_DIRECTORY, NOT_A_DIRECTORY);
  }

  public void testValidation_relative() {
    validate("relative/path", RELATIVE_PATH, RELATIVE_PATH);
  }

  public void testValidation_directory() {
    validate(file.getParent(), null, null);
  }

  public void testValidation_canRead() throws Exception {
    validate(dir, CANNOT_READ, CANNOT_READ);
  }

  public void testValidation_uri() {
    validate("http://www.google.com", null, RELATIVE_PATH);
    validate("https://www.google.com", null, RELATIVE_PATH);
    validate("file:///tmp/foo", null, RELATIVE_PATH);
  }

  public void testValidation_weridUri() {
    validate("http://www.google.com/#p&104=305", null, RELATIVE_PATH);
  }

  public void testValidation_protocol() {
    validate("ftp://ftp.wustl.edu/path", UNACCEPTABLE_PROTOCOL, RELATIVE_PATH);
  }

  public void testValidation_syntax() {
    // What throws URI Syntax Exception?
    validate("htpftp.wustl.edu/path", RELATIVE_PATH, RELATIVE_PATH);
  }

  private void validate(File file, String expectedAll, String expectedPath) {
    validate(file.getPath(), expectedAll, expectedPath);
  }

  private void validate(String string, String expectedAll, String expectedPath) {
    validateImpl(string, ALL_VALIDATOR, expectedAll);
    validateImpl(string, PATH_VALIDATOR, expectedPath);
  }

  private void validateImpl(String string, TaskResourceValidator validator,
      String expected) {
    String actual = validator.isValid(string);
    assertEquals("for " + validator, expected, actual);
  }
}
