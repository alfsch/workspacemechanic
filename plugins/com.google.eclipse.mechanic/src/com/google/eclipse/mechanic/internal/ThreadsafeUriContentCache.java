/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
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
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Stores content from URIs to prevent re-scanning for the same content.
 */
public class ThreadsafeUriContentCache implements IUriContentProvider {

  // Do not store URLs in the cache. URL's hashCode is bad, as in, _really_ bad.
  private final TimedEvictionCache<URI, FutureTask<byte[]>> cache;
  private final Executor executor = Executors.newSingleThreadExecutor();

  public ThreadsafeUriContentCache() {
    cache = TimedEvictionCache.create(0L);
  }

  public ThreadsafeUriContentCache(long lifetimeMillis) {
    cache = TimedEvictionCache.create(lifetimeMillis);
  }

  public InputStream get(final URI uri) throws IOException {
    FutureTask<byte[]> future = new FutureTask<byte[]>(new Callable<byte[]>() {
      public byte[] call() throws IOException {
        return Util.getBytes(openStream(uri));
      }
    });

    FutureTask<byte[]> futureToFetch = cache.putIfAbsent(uri, future);
    if (futureToFetch == null) {
      futureToFetch = future;
      executor.execute(future);
    }

    try {
      return new ByteArrayInputStream(futureToFetch.get());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException(e);
    } catch (ExecutionException e) {
      if (e.getCause() instanceof IOException) {
        throw (IOException) e.getCause();
      }
      throw new IOException(e);
    }
  }

  public void clear() {
    cache.clear();
  }

  /**
   * Package private for testing.
   */
  InputStream openStream(final URI uri) throws IOException,
      MalformedURLException {
    return uri.toURL().openStream();
  }
}
