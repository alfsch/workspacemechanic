/*******************************************************************************
 * Copyright (C) 2012, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * Tests for {@link ReconcilingPreferencesTask}.
 */
public class ReconcilingPreferencesTaskTest extends TestCase {

  /**
   * This ensures that it's reading the unicode-formatted reconcile task correctly.
   * The preferences, as we've stored them, will match, and so, there is no call
   * to prefsMock.put.
   */
  public void testSanity_Unicode() throws IOException {
    URL url = new URL("platform:/plugin/com.google.eclipse.mechanic.tests/testdata/reconcile_unicode.epf");
    InputStream inputStream = url.openConnection().getInputStream();

    assertNotNull(inputStream);

    IResourceTaskReference mockReference = createMock(IResourceTaskReference.class);
    expect(mockReference.getPath()).andReturn("/path").anyTimes();
    expect(mockReference.newInputStream()).andReturn(inputStream);

    IEclipsePreferences prefsRootMock = createMock(IEclipsePreferences.class);
    IEclipsePreferences prefsMock = createMock(IEclipsePreferences.class);
    expect(prefsRootMock.node("/instance/org.eclipse.jdt.debug.ui")).andReturn(prefsMock).anyTimes();
    expect(prefsMock.get("org.eclipse.jdt.debug.ui.detail_formatters", null)).andReturn(
        "java.lang.Exception,String.format(\"Exception : %s\",this),1").anyTimes();

    replay(mockReference, prefsRootMock, prefsMock);

    ReconcilingPreferencesTask task = new ReconcilingPreferencesTask(mockReference, prefsRootMock) {
      public String getTitle() {
        return "title";
      }
      
      public String getDescription() {
        return "desc";
      }
    };

    task.run();

    verify(mockReference, prefsRootMock, prefsMock);
  }
}
