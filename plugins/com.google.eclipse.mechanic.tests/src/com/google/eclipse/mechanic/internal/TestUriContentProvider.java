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

/**
 * Contains some canned entries that prevent actually fetching content from the web.
 */
public final class TestUriContentProvider implements IUriContentProvider {
  private final Map<String, String> map = Util.newHashMap();

  private int fetches;
  private int clears;

  public TestUriContentProvider() {
    map.put("http://www.google.com", "asdf");
    map.put("http://www.imdb.com", "qwerty");
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
}
