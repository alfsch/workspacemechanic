/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.util.Map;

/**
 * A thread-safe map of objects that can optionally be evicted after a certain time
 * after its creation.
 *
 * <p>Entries are evicted of the supplied lifetime parameter is greater than zero. Eviction
 * is a passive process; entries are only removed when calls to {@link #clear()} or
 * {@link #putIfAbsent(Object, Object)} are called.
 *
 * @param <K> the type of key stored.
 * @param <V> the type of value stored.
 */
public class TimedEvictionCache<K, V> {

  private final long durationMillis;
  private final Object lock = new Object();

  private int requests;
  private int cacheHits;
  private int evictions;

  private static class Value<V> {
    V value;
    long creationTimeMillis;
    public Value(V value, long creationTimeMillis) {
      this.value= value;
      this.creationTimeMillis = creationTimeMillis;
    }
  }

  private final Map<K, Value<V>> cache = Util.newHashMap();

  /**
   * Create a new instance.
   *
   * @param <K> The type of keys stored in the cache.
   * @param <V> The type of values stored in the cache.
   * @param durationMillis The duration, in milliseconds, that a cache entry is
   * considered valid. If this value is zero (or negative) entries are
   * not evicted until {@link #clear()} is called.
   */
  public static <K, V>TimedEvictionCache<K, V> create(
      long durationMillis) {
    return new TimedEvictionCache<K, V>(durationMillis);
  }

  /** Package private for testing */
  TimedEvictionCache(long durationMillis) {
    this.durationMillis = durationMillis;
  }

  /**
   * @param key the key to store. Must not be null.
   * @param value the value to store. Must not be null.
   * @return the prior value for this key. If there is no prior value for this key,
   * or the prior value was evicted, this method returns null.
   */
  public V putIfAbsent(K key, V value) {
    Util.checkNotNull(key, "key is null");
    Util.checkNotNull(value, "values is null");

    synchronized(lock) {
      requests++;
      long now = getNow();
      Value<V> candidate = new Value<V>(value, now);

      Value<V> prior = cache.get(key);
      if (prior == null) {
        cache.put(key, candidate);
        return null;
      }
      if (durationMillis > 0 && ((now - prior.creationTimeMillis) > durationMillis)) {
        evictions++;
        cache.put(key, candidate);
        return null;
      }

      // This is a cache hit.
      cacheHits++;
      return prior.value;
    }
  }

  /** Package-private for testing */
  long getNow() {
    return System.currentTimeMillis();
  }

  public void clear() {
    synchronized(lock) {
      cache.clear();
    }
  }

  /**
   * Returns the number of entries stored.
   */
  public int size() {
    synchronized(lock) {
      return cache.size();
    }
  }

  /**
   * Returns the number of calls to {@link #putIfAbsent(Object, Object)}.
   */
  public int getRequests() {
    synchronized(lock) {
      return requests;
    }
  }

  /**
   * Returns the number of calls to {@link #putIfAbsent(Object, Object)} that resulted
   * in cache hits.
   */
  public int getCacheHits() {
    synchronized(lock) {
      return cacheHits;
    }
  }

  /**
   * Returns the number of calls to {@link #putIfAbsent(Object, Object)} that resulted
   * in cache evictions.
   */
  public int getEvictions() {
    synchronized(lock) {
      return evictions;
    }
  }

  
}
