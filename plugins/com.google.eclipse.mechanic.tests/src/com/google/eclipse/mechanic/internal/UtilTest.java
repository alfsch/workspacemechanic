/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import junit.framework.TestCase;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for Util.
 * 
 * @author smckay@google.com (Steve McKay)
 */
@RunAsJUnitTest
public class UtilTest extends TestCase {

  public void testUnquote() {

    // test without embedded quotes
    assertEquals("foo", Util.unquote("\"foo\""));

    // test with embedded quotes
    assertEquals("b\"ar", Util.unquote("\"b\"ar\""));

  }

  public void testUnquoteNullThrowsNullPointerException() {
    try {
      Util.unquote(null);
      fail("unquote should have thrown a NullPointerException.");
    } catch (NullPointerException expected) {
      // as expected
    }
  }

  public void testGetBytes() throws Exception {
    testGetBytes(new byte [ ] { });
    testGetBytes(new byte [ ] { 1, 2, 3, 4, 5, 6, 7 });
    testGetBytes("Now is the time to come to the aid of our contry.".getBytes());
    StringBuilder big = new StringBuilder();
    for (int i = 1; i < 1000; i++) {
      big.append("this is a giant string, we want it to be very long. When it is very long");
      big.append("then the string will be read in multiple chunks.");
    }
    testGetBytes(big.toString().getBytes());
  }

  private void testGetBytes(byte[] expected) throws IOException {
    InputStream is = new ByteArrayInputStream(expected);
    byte[] actual = Util.getBytes(is);
    is.close();
    assertTrue(Arrays.equals(expected, actual));
  }

  private void assertArray(String[] actual, String... expected) {
    if (!Arrays.deepEquals(expected, actual)) {
      fail("Expected " + Arrays.deepToString(expected) + " Actual " + Arrays.deepToString(actual));
    }
  }
}
