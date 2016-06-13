/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
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
 * Tests for {@link EpfFileModel}.
 */
@RunAsJUnitTest
public class EpfFileModelTest extends TestCase {
  public void testTitle() {
    try {
      new EpfFileModel(null, "x", TaskType.LASTMOD);
      fail("exception expected");
    } catch (NullPointerException e) {}

    assertEquals("Y", new EpfFileModel("Y", "x", TaskType.LASTMOD).getTitle());
  }

  public void testDescription() {
    try {
      new EpfFileModel("x", null, TaskType.LASTMOD);
      fail("exception expected");
    } catch (NullPointerException e) {}

    assertEquals("Y", new EpfFileModel("x", "Y", TaskType.LASTMOD).getDescription());
  }

  public void testTaskType() {
    try {
      new EpfFileModel("x", "y", null);
      fail("exception expected");
    } catch (NullPointerException e) {}

    assertEquals(TaskType.LASTMOD, new EpfFileModel("x", "Y", TaskType.LASTMOD).getTaskType());
  }

  public void testElements() {
    EpfFileModel model = new EpfFileModel("x", "y", TaskType.RECONCILE);
    assertEquals(0, model.getPreferences().size());

    model.addElement("first", "second");
    assertEquals(1, model.getPreferences().size());
    assertEquals("first", model.getPreferences().keySet().iterator().next());
    assertEquals("second", model.getPreferences().values().iterator().next());

    model.addElement("third", "fourth");
    assertEquals(2, model.getPreferences().size());
  }
}
