/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;
import com.google.gson.JsonIOException;
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

    public ResourceTaskProvider getProvider() {
      return UriTaskProvider.this;
    }

    public InputStream newInputStream() throws IOException {
      return longTermCache.get(uri);
    }

    public String getName() {
      return uri.getPath();
    }

    public String getPath() {
      return uri.getPath();
    }

    @Override
    public String toString() {
      return uri.toString();
    }
  }

  /**
   * Create a new instance.
   *
   * <p>This constructor takes two caches. One has a shorter lifetime and so is more frequently
   * polled, to get the list of tasks to process. The other has a longer lifetime (12 hours
   * ATM) and is used to cache actual tasks (e.g. .epf files) which are much less likely to change.
   *
   * @param uri The URI that contains information about tasks.
   * @param stateSensitiveCache short term cache.
   * @param longTermCache long term cache.
   */
  public UriTaskProvider(URI uri, IUriContentProvider stateSensitiveCache,
      IUriContentProvider longTermCache) {
    this.uri = Util.checkNotNull(uri);
    this.stateSensitiveCache = Util.checkNotNull(stateSensitiveCache);
    this.longTermCache = Util.checkNotNull(longTermCache);
  }

  public IStatus initialize() {
    InputStream inputStream;
    try {
      inputStream = stateSensitiveCache.get(uri);
    } catch (IOException e) {
      return newExceptionStatus(e);
    }
    try {
      model = UriTaskProviderModelParser.read(inputStream);
    } catch (JsonSyntaxException e) {
      return newExceptionStatus(e);
    } catch (JsonIOException e) {
      return newExceptionStatus(e);
    } catch (RuntimeException e) {
      return newExceptionStatus(e);
    }
    try {
      inputStream.close();
    } catch (IOException e) {
      return newExceptionStatus(e);
    }
    return Status.OK_STATUS;
  }

  private IStatus newExceptionStatus(Exception e) {
    return new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID, "Can't initialize " + this, e);
  }

  public List<IResourceTaskReference> getTaskReferences(String localPath, String filter) {
    // This is for class files, and we don't need to implement this. The function should be
    // removed anyway.
    return null;
  }

  public List<IResourceTaskReference> getTaskReferences(String filterText) {
    List<IResourceTaskReference> refs = Util.newArrayList();
    for (URI uri : model.getTasks()) {
      if (uri.getPath().endsWith(filterText)) {
        refs.add(new TaskReference(uri));
      }
    }
    return refs;
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
