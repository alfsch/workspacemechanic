/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Tests for {@link EpfFileModelWriter}.
 */
public class EpfFileModelWriterTest extends TestCase {
  public void testSimple() throws IOException {
    EpfFileModel model = new EpfFileModel("TiTlE", "DeScRiPtIoN", TaskType.RECONCILE);
    model.addElement("first", "second");
    String actual = writeToString(model);
    assertBasics(actual);
  }

  public void testOnePreference() throws IOException {
    EpfFileModel model = new EpfFileModel("TiTlE", "DeScRiPtIoN", TaskType.RECONCILE);
    model.addElement("first", "second");
    String actual = writeToString(model);
    assertBasics(actual);
    assertLine("first=second", actual);
  }

  public void testTwoPreferences() throws IOException {
    EpfFileModel model = new EpfFileModel("TiTlE", "DeScRiPtIoN", TaskType.RECONCILE);
    model.addElement("first", "second");
    model.addElement("third", "fourth");
    String actual = writeToString(model);
    assertBasics(actual);
    assertLine("first=second", actual);
    assertLine("third=fourth", actual);
  }

  public void testNewlineInPreferences() throws IOException {
    EpfFileModel model = new EpfFileModel("TiTlE", "DeScRiPtIoN", TaskType.RECONCILE);
    model.addElement("key", "longline\nnewline");
    String actual = writeToString(model);
    fail("didn't test this one yet " + actual);
  }

  private String writeToString(EpfFileModel model) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    EpfFileModelWriter.write(model, os);
    String actual = new String(os.toByteArray());
    return actual;
  }

  private void assertLine(String substring, String string) {
    assertTrue("Could not find line [" + substring + "] in [" + string + "]",
        Arrays.asList(string.split("\n")).contains(substring));
  }

  private void assertBasics(String actual) {
    assertLine("# @title TiTlE", actual);
    assertLine("# @description DeScRiPtIoN", actual);
    assertLine("# @audit_type RECONCILE", actual);
    assertLine("# Created by the Workspace Mechanic Preference Recorder", actual);
    assertLine("file_export_version=3.0", actual);
  }

}
