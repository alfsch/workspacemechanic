/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/package com.google.eclipse.mechanic.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Contains some canned entries that prevent actually fetching content from the web.
 */
public final class TestUriContentProvider implements IUriContentProvider {
  public static final URI WWW_GOOGLE_COM = URI.create("http://www.google.com");
  public static final URI WWW_IMDB_COM = URI.create("http://www.imdb.com");

  private final Map<String, String> map = Maps.newHashMap();
  private final Map<String, Long> lastmod = Maps.newHashMap();

  private int fetches;
  private int clears;

  public TestUriContentProvider() {
    map.put("http://www.google.com", "asdf");
    map.put("http://www.imdb.com", "qwerty");
    lastmod.put("http://www.google.com", 1L);
    lastmod.put("http://www.imdb.com", 2L);
  }

  public InputStream get(URI uri) throws IOException {
    fetches++;
    return new ByteArrayInputStream(map.get(uri.toASCIIString()).getBytes());
  }

  public void clear() {
    clears++;
  }

  public int clearCount() {
    return clears;
  }

  public int fetchCount() {
    return fetches;
  }

  public long lastModifiedTime(URI uri) throws IOException {
    fetches++;
    Long l = lastmod.get(uri.toASCIIString());
    return l == null ? 0 : l;
  }
}
