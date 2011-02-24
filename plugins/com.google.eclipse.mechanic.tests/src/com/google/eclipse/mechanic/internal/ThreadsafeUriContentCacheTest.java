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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Tests for {@link ThreadsafeUriContentCache}.
 */
public class ThreadsafeUriContentCacheTest extends TestCase {
  /**
   * Contains some canned entries that prevent actually fetching content from the web.
   */
  class TestInner extends ThreadsafeUriContentCache {

    private final Map<String, String> map = Util.newHashMap();
    // Number of cache requests.
    public int count;

    public TestInner() {
      super(0);
      map.put("http://www.google.com", "asdf");
      map.put("http://www.imdb.com", "qwerty");
    }

    @Override
    InputStream openStream(URI uri) throws IOException, MalformedURLException {
      count++;
      return new ByteArrayInputStream(map.get(uri.toASCIIString()).getBytes());
    }
  }
  public void testGet_cache() throws Exception {
    TestInner cache = new TestInner();
    assertEquals(0, cache.count);
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, cache.count);
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, cache.count);
    cache.clear();
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(2, cache.count);
  }

  private String read(InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    return reader.readLine();
  }
}
