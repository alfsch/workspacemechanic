/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.eclipse.mechanic.ICollector;
import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;
import com.google.gson.JsonSyntaxException;

/**
 * Provides information about tasks that come from URIs.
 */
public final class UriTaskProvider extends ResourceTaskProvider {
  private final URI uri;

  private UriTaskProviderModel model;

  private final IUriContentProvider stateSensitiveCache;
  private final IUriContentProvider longTermCache;

  private final class TaskReference implements IResourceTaskReference {
    private final URI uri;

    public TaskReference(URI uri) {
      this.uri = uri;
    }

    public InputStream newInputStream() throws IOException {
      return longTermCache.get(uri);
    }

    public String getName() {
      return uri.getPath();
    }

    public String getPath() {
      return uri.toString();
    }

    @Override
    public String toString() {
      return uri.toString();
    }

    public File asFile() {
      return null;
    }

    public long getLastModified() throws IOException {
      return longTermCache.lastModifiedTime(uri);
    }

    public long computeMD5() throws IOException {
      ByteSource supplier = new ByteSource() {
        @Override
        public InputStream openStream() throws IOException {
          return newInputStream();
        }
      };
      return supplier.hash(Hashing.md5()).asLong();
    }
  }

  private UriTaskProvider(URI uri, IUriContentProvider stateSensitiveCache,
      IUriContentProvider longTermCache) {
    this.uri = Preconditions.checkNotNull(uri);
    this.stateSensitiveCache = Preconditions.checkNotNull(stateSensitiveCache);
    this.longTermCache = Preconditions.checkNotNull(longTermCache);
  }

  /**
   * Create a new instance.
   *
   * <p>The constructor takes two caches. One has a shorter lifetime and so is more frequently
   * polled, to get the list of tasks to process. The other has a longer lifetime (12 hours
   * ATM) and is used to cache actual tasks (e.g. .epf files) which are much less likely to change.
   *
   * @param uri The URI that contains information about tasks.
   * @param stateSensitiveCache short term cache.
   * @param longTermCache long term cache.
   */
  public static UriTaskProvider newInstance(
      URI uri,
      IUriContentProvider stateSensitiveCache,
      IUriContentProvider longTermCache) throws IOException {

    UriTaskProvider instance = new UriTaskProvider(
        uri, stateSensitiveCache, longTermCache);
    instance.setModel();
    return instance;
  }

  private void setModel() throws IOException, JsonSyntaxException {
    InputStream inputStream = stateSensitiveCache.get(uri);
    try {
      model = UriTaskProviderModelParser.read(inputStream);
    } finally {
      inputStream.close();
    }
  }

  public void collectTaskReferences(String localPath, String filter, ICollector<IResourceTaskReference> collector) {
    // This is for class files, and we don't need to implement this. The function should be
    // removed anyway.
  }

  public void collectTaskReferences(String filterText, ICollector<IResourceTaskReference> collector) {
    for (URI uri : model.getTasks()) {
      if (uri.getPath().endsWith(filterText)) {
        if (!uri.isAbsolute()) {
          // resolve 
          uri = UriTaskProvider.this.uri.resolve(uri);
        }
        collector.collect(new TaskReference(uri));
      }
    }
  }

  @Override
  public String toString() {
    return "URI Provider: " + uri.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof UriTaskProvider)) {
      return false;
    }

    return ((UriTaskProvider) obj).uri.equals(uri);
  }

  @Override
  public int hashCode() {
    return uri.hashCode();
  }
}
