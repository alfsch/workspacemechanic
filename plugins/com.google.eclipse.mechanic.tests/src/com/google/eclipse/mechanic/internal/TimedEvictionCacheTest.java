/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

import junit.framework.TestCase;

@RunAsJUnitTest
public class TimedEvictionCacheTest extends TestCase {

  static class TestingCache<K, V> extends TimedEvictionCache<K, V> {
    private long now;

    public int size;
    public int requests;
    public int cacheHits;
    public int evictions;

    TestingCache(long lifetimeMillis) {
      super(lifetimeMillis);
      now = 0;
      baseline();
    }

    public void setNow(long now) {
      this.now = now;
    }

    @Override
    long getNow() {
      return this.now;
    }

    // Use of baselines means that we can test how much each metric changed
    // between calls rather than in total.

    public void baseline() {
      this.size = size();
      this.requests = getRequests();
      this.evictions = getEvictions();
      this.cacheHits = getCacheHits();
    }

    public int baselineSize() {
      return size() - size;
    }

    public int baselineRequests() {
      return getRequests() - requests;
    }

    public int baselineEvictions() {
      return getEvictions() - evictions;
    }

    public int baselineCacheHits() {
      return getCacheHits() - cacheHits;
    }
  }

 
  public void testCache() {
    TestingCache<String, String> cache = new TestingCache<String, String>(0);
    assertMetrics(cache, 0, 0, 0, 0);
    assertNull(cache.putIfAbsent("a", "b"));
    assertMetrics(cache, 1, 1, 0, 0);
    assertEquals("b", cache.putIfAbsent("a", "c"));
    assertMetrics(cache, 0, 1, 1, 0);
    assertEquals("b", cache.putIfAbsent("a", "d"));
    assertMetrics(cache, 0, 1, 1, 0);
    cache.clear();
    assertMetrics(cache, -1, 0, 0, 0);
    assertNull(cache.putIfAbsent("a", "c"));
    assertMetrics(cache, 1, 1, 0, 0);
    assertEquals("c", cache.putIfAbsent("a", "c"));
    assertMetrics(cache, 0, 1, 1, 0);
    assertNull(cache.putIfAbsent("b", "c"));
    assertMetrics(cache, 1, 1, 0, 0);
  }

  public void testEviction() {
    TestingCache<String, String> cache = new TestingCache<String, String>(1);
    assertMetrics(cache, 0, 0, 0, 0);
    assertNull(cache.putIfAbsent("a", "b"));
    assertMetrics(cache, 1, 1, 0, 0);
    assertEquals("b", cache.putIfAbsent("a", "c"));
    assertMetrics(cache, 0, 1, 1, 0);
    assertEquals("b", cache.putIfAbsent("a", "d"));
    assertMetrics(cache, 0, 1, 1, 0);
    cache.setNow(2);
    assertNull(cache.putIfAbsent("a", "c"));
    assertMetrics(cache, 0, 1, 0, 1);
  }

  /**
   * @param size expected increase in size
   * @param requests expected increase in requests
   * @param cacheHits expected increase in cache hits
   * @param evictions expected increase in evictions
   */
  public void assertMetrics(TestingCache<String, String> cache,
      int size, int requests, int cacheHits, int evictions) {
    assertEquals("size", size, cache.baselineSize());
    assertEquals("requests", requests, cache.baselineRequests());
    assertEquals("cacheHits", cacheHits, cache.baselineCacheHits());
    assertEquals("evictions", evictions, cache.baselineEvictions());
    // Update the baseline.
    cache.baseline();
  }
}
