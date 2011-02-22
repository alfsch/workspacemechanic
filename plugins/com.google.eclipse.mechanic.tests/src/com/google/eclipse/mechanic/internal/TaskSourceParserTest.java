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
 * Tests for {@link TaskSourceParser}.
 */
@RunAsJUnitTest
public class TaskSourceParserTest extends TestCase {
  private static class TestParser extends TaskSourceParser {
    @Override
    public String doVariableSubstitution(String val) {
      return val;
    }
  };
  private TaskSourceParser parser = new TestParser();

  public void testParse() {
    assertResults(parser.parse(""));
    assertResults(parser.parse("x"), "x");
    assertResults(parser.parse("x:y"), "x", "y");
    assertResults(parser.parse(
        "['/home/user/path/.eclipse','http://www.google.com/directory'," +
            "'https://www.yahoo.com/directory?param\\u003dvalue%amp;term']"),
        "/home/user/path/.eclipse", "http://www.google.com/directory",
            "https://www.yahoo.com/directory?param=value%amp;term");
  }

  public void testUnparse() {
    assertEquals("[]", createList());
    assertEquals("[\"x\"]", createList("x"));
    assertEquals("[\"x\",\"x\"]", createList("x", "x"));
    assertEquals(
        "[\"/home/user/path/.eclipse\",\"http://www.google.com/directory\"," +
            "\"https://www.yahoo.com/directory?param\\u003dvalue%amp;term\"]",
        createList("/home/user/path/.eclipse", "http://www.google.com/directory",
            "https://www.yahoo.com/directory?param=value%amp;term"));
  }

  public void testRoundTrip() {
    String json = "[\"/home/user/path/.eclipse\",\"http://www.google.com/directory\"," +
        "\"https://www.yahoo.com/directory?param\\u003dvalue%amp;term\"]";
    assertEquals(json, parser.unparse(parser.parse(json)));

    String[] items = new String[] { "/home/user/path/.eclipse", "http://www.google.com/directory",
        "https://www.yahoo.com/directory?param=value%amp;term"};
    assertTrue(Arrays.deepEquals(items, parser.parse(parser.unparse(items))));
  }

  private String createList(String... items) {
    return parser.unparse(items);
  }

  private void assertResults(String[] actual, String... expected) {
    if (!Arrays.deepEquals(actual, expected)) {
      fail("Expected " + Arrays.deepToString(actual) + " but got " +
          Arrays.deepToString(actual));
    }
    assertEquals(actual.length, expected.length);
    for (int i = 0; i < actual.length; i++) {
      assertEquals(actual[i], expected[i]);
    }
  }
}
