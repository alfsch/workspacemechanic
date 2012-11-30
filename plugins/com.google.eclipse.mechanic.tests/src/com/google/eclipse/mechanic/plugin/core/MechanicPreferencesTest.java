/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.google.eclipse.mechanic.Evaluator;
import com.google.eclipse.mechanic.RepairAction;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.tests.internal.RunAsPluginTest;
import static com.google.eclipse.mechanic.plugin.core.OldMechanicPreferences.*;

/**
 * Tests for {@link OldMechanicPreferences}
 */
@RunAsPluginTest
public class MechanicPreferencesTest extends TestCase {

  public void testGetHelpUrl() {
    MechanicPlugin.getDefault().getPluginPreferences()
        .setToDefault(OldMechanicPreferences.HELP_URL_PREF);
    assertEquals(
        "http://code.google.com/a/eclipselabs.org/p/workspacemechanic/wiki/GettingStarted",
        OldMechanicPreferences.getHelpUrl());
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    setBlockedTaskIds(Collections.<String>emptySet());
    assertEquals(0, getBlockedTaskIds().size());
  }

  public void testSetBlockedTaskIds() {
    setBlockedTaskIds(Collections.singleton("asdf"));
    assertContentsAnyOrder(getBlockedTaskIds(), "asdf");

    setBlockedTaskIds(Collections.<String>emptySet());
    assertEquals(0, getBlockedTaskIds().size());

    Set<String> set = new HashSet<String>();
    set.add("123");
    set.add("234");
    setBlockedTaskIds(set);
    assertContentsAnyOrder(getBlockedTaskIds(), "123", "234");
  }


  public void testBlockItem() {
    blockItem(createTask("abcdefg"));
    assertContentsAnyOrder(getBlockedTaskIds(), "abcdefg");

    blockItem(createTask("hijklmnop"));
    assertContentsAnyOrder(getBlockedTaskIds(), "abcdefg", "hijklmnop");

    blockItem(createTask("qrstuv"));
    assertContentsAnyOrder(
        getBlockedTaskIds(), "abcdefg", "hijklmnop", "qrstuv");

    blockItem(createTask("wxyz"));
    assertContentsAnyOrder(
        getBlockedTaskIds(), "abcdefg", "hijklmnop", "qrstuv", "wxyz");
  }

  public void testShowPopup() {
    showPopup();
    assertTrue(isShowPopup());
    doNotShowPopup();
    assertFalse(isShowPopup());
    showPopup();
    assertTrue(isShowPopup());
  }

  public void testCleanSleepSeconds() {
    assertEquals(10, cleanSleepSeconds(8));
    assertEquals(11, cleanSleepSeconds(11));
  }

  public void testSetLongGetLong() {
    String pref = "TEST_PREF";
    assertEquals(0, getLong(pref));
    setLong(pref, 1);
    assertEquals(1, getLong(pref));
    setLong(pref, Long.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, getLong(pref));
  }

  public void testWithFunnyKey() {
    String pref =
        "com.google.eclipse.mechanic.internal.PreferenceFileTaskScanner$" +
        "LastmodEpfTask@" + 
        "/Users/kberg/.eclipse/mechanic/showlinenumbers.epf_lastmod";

    assertEquals(0, getLong(pref));
    setLong(pref, 1);
    assertEquals(1, getLong(pref));
    setLong(pref, Long.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, getLong(pref));
  }

  public void testGetTaskDirectories() {
    MechanicPlugin.getDefault().getPluginPreferences()
        .setToDefault(OldMechanicPreferences.DIRS_PREF);

    String taskDirs = OldMechanicPreferences.getString(OldMechanicPreferences.DIRS_PREF);
    assertEquals("${user_homedir}/.eclipse/mechanic:${mechanic_configuration_path}/mechanic", taskDirs);
  }

  public void testContains() {
    String pref = "XYZPDQ";
    assertFalse(contains(pref));
    setLong(pref, 1L);
    assertTrue(contains(pref));
    getPreferences().setToDefault(pref);
    assertFalse(contains(pref));
  }

  //public static int getThreadSleepSeconds() {
  //public static String doVariableSubstitution(String input) {
  //public static IStatus validatePreferencesFile(IPath path) {

  /**
   * Create a mock task with the given id.
   */
  private Task createTask(final String id) {
    return new Task() {
      public String getId() {
        return id;
      }

      public String getTitle() {
        throw new AssertionError();
      }

      public String getDescription() {
        throw new AssertionError();
      }

      public Evaluator getEvaluator() {
        throw new AssertionError();
      }

      public RepairAction getRepairAction() {
        throw new AssertionError();
      }
    };
  }

  private void assertContentsAnyOrder(Collection<?> stuff, Object... elements) {
    assertEquals(elements.length, stuff.size());
    for (Object object : elements) {
      assertTrue(String.valueOf(object), stuff.contains(object));
    }
  }
}
