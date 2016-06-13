/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

/**
 * Tests for {@link ThreadsafeUriContentCache}.
 */
public class ThreadsafeUriContentCacheTest extends TestCase {
  private static final URI WWW_GOOGLE_COM = TestUriContentProvider.WWW_GOOGLE_COM;

  private final TestUriContentProvider delegate = new TestUriContentProvider();

  public void testGet_cache() throws Exception {
    ThreadsafeUriContentCache cache = new ThreadsafeUriContentCache(0, TimeUnit.MILLISECONDS, delegate);
    assertEquals(0, delegate.fetchCount());
    assertEquals("asdf", read(cache.get(WWW_GOOGLE_COM)));
    assertEquals(1, delegate.fetchCount());
    assertEquals("asdf", read(cache.get(WWW_GOOGLE_COM)));
    assertEquals(1, delegate.fetchCount());
    cache.clear();
    assertEquals("asdf", read(cache.get(WWW_GOOGLE_COM)));
    assertEquals(2, delegate.fetchCount());
  }

  public void testLastmod_cache() throws Exception {
    ThreadsafeUriContentCache cache = new ThreadsafeUriContentCache(0, TimeUnit.MILLISECONDS, delegate);
    assertEquals(0, delegate.fetchCount());
    assertEquals(1L, cache.lastModifiedTime(WWW_GOOGLE_COM));
    assertEquals(1, delegate.fetchCount());
    assertEquals(1L, cache.lastModifiedTime(WWW_GOOGLE_COM));
    assertEquals(1, delegate.fetchCount());
    cache.clear();
    assertEquals(1L, cache.lastModifiedTime(WWW_GOOGLE_COM));
    assertEquals(2, delegate.fetchCount());
  }

  private String read(InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    return reader.readLine();
  }
}
