/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import junit.framework.TestCase;

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
}
