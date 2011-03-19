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
 * Tests for {@link ResourceTaskProviderParser}.
 */
@RunAsJUnitTest
public class ResourceTaskProviderParserTest extends TestCase {
  private static class TestParser extends ResourceTaskProviderParser {
    @Override
    public String doVariableSubstitution(String val) {
      return val;
    }
  };
  private ResourceTaskProviderParser parser = new TestParser();

  public void testParse() {
    assertResults(parser.parse(""));
    assertResults(parser.parse("x"), "x");
    assertResults(parser.parse("x" + System.getProperty("path.separator") + "y"), "x", "y");
    assertResults(parser.parse(
        "['/home/user/path/.eclipse','http://www.google.com/directory'," +
            "'https://www.yahoo.com/directory?param\\u003dvalue%amp;term']"),
        "/home/user/path/.eclipse", "http://www.google.com/directory",
            "https://www.yahoo.com/directory?param=value%amp;term");
  }

  public void testUnparse() {
    assertEquals("[]", parser.unparse());
    assertEquals("[\"x\"]", parser.unparse("x"));
    assertEquals("[\"x\",\"x\"]", parser.unparse("x", "x"));
    assertEquals(
        "[\"/home/user/path/.eclipse\",\"http://www.google.com/directory\"," +
            "\"https://www.yahoo.com/directory?param\\u003dvalue%amp;term\"]",
            parser.unparse("/home/user/path/.eclipse", "http://www.google.com/directory",
            "https://www.yahoo.com/directory?param=value%amp;term"));
  }

  public void testRoundTrip() {
    testRoundTripFromJson(
        "[\"/home/user/path/.eclipse\",\"http://www.google.com/directory\"," +
            "\"https://www.yahoo.com/directory?param\\u003dvalue%amp;term\"]");
    testRoundTripFromList("/home/user/path/.eclipse", "http://www.google.com/directory",
            "https://www.yahoo.com/directory?param=value%amp;term");
  }

  private void testRoundTripFromList(String... items) {
    assertTrue(Arrays.deepEquals(items, parser.parse(parser.unparse(items))));
  }

  private void testRoundTripFromJson(String string) {
    assertEquals(string, parser.unparse(parser.parse(string)));
  }

  // TODO(konigsberg): Move to common area, BlockedTaskIdsParserTest also uses this.
  private void assertResults(String[] actual, String... expected) {
    if (!Arrays.deepEquals(actual, expected)) {
      fail("Expected " + Arrays.deepToString(expected) + " but got " +
          Arrays.deepToString(actual));
    }
  }
}
