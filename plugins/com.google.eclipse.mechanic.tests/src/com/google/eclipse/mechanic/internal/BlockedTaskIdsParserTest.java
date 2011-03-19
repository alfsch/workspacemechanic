/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.util.Arrays;

import junit.framework.TestCase;

import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for {@link BlockedTaskIdsParser}.
 */
@RunAsJUnitTest
public class BlockedTaskIdsParserTest extends TestCase {

  private BlockedTaskIdsParser parser = new BlockedTaskIdsParser();

  String x= "com.google.eclipse.path$Class@/Path/To/Thing";
  String y = "com.google.eclipse.path2@file://path/to/thing";
  String z = "com.google.eclipse.path3@http://www.google.com/path.json?term&term2";
  public void testParse() {
    assertResults(parser.parse(""));
    assertResults(parser.parse("x"), "x");
    assertResults(parser.parse("x:y"), "x", "y");
    assertResults(parser.parse(
        "['com.google.eclipse.path$Class@/Path/To/Thing', " +
            "'com.google.eclipse.path2@file://path/to/thing', " +
            "'com.google.eclipse.path3@http://www.google.com/path.json?term&term2']"),
        x, y, z);
  }

  public void testUnparse() {
    assertEquals("[]", parser.unparse());
    assertEquals("[\"x\"]", parser.unparse("x"));
    assertEquals("[\"x\",\"x\"]", parser.unparse("x", "x"));
    assertEquals(
        "[\"com.google.eclipse.path$Class@/Path/To/Thing\"," +
        "\"com.google.eclipse.path2@file://path/to/thing\"," +
        "\"com.google.eclipse.path3@http://www.google.com/path.json?term\u0026term2\"]",
        parser.unparse(x, y, z));
  }

  public void testRoundTrip() {
    testRoundTripFromJson(
        "[\"/home/user/path/.eclipse\",\"http://www.google.com/directory\"," +
            "\"https://www.yahoo.com/directory?param\\u003dvalue%amp;term\"]");
    testRoundTripFromList(x, y, z);
  }

  private void testRoundTripFromList(String... items) {
    assertTrue(Arrays.deepEquals(items, parser.parse(parser.unparse(items))));
  }

  private void testRoundTripFromJson(String string) {
    assertEquals(string, parser.unparse(parser.parse(string)));
  }

  private void assertResults(String[] actual, String... expected) {
    if (!Arrays.deepEquals(actual, expected)) {
      fail("Expected " + Arrays.deepToString(expected) + " but got " +
          Arrays.deepToString(actual));
    }
  }
}
