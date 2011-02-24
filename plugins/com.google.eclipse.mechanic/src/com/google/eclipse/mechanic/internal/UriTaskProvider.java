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
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskReference;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public final class UriTaskProvider extends ResourceTaskProvider {
  private final URI uri;

  private UriTaskProviderModel model;

  private final IUriContentProvider contentProvider;

  private final class TaskReference implements ResourceTaskReference {
    private final URI uri;

    public TaskReference(URI uri) {
      this.uri = uri;
    }

    public ResourceTaskProvider getProvider() {
      return UriTaskProvider.this;
    }

    public InputStream newInputStream() throws IOException {
      return contentProvider.get(uri);
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

  public UriTaskProvider(URI uri, IUriContentProvider contentProvider) {
    this.uri = Util.checkNotNull(uri);
    this.contentProvider = Util.checkNotNull(contentProvider);
  }

  @Override
  public IStatus initialize() {
    InputStream inputStream;
    try {
      inputStream = uri.toURL().openStream();
    } catch (MalformedURLException e) {
      return newExceptionStatus(e);
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

  @Override
  public List<ResourceTaskReference> getTaskReferences(String localPath, String filter) {
    // This is for class files, and we don't need to implement this. The function should be
    // removed anyway.
    return null;
  }

  public List<ResourceTaskReference> getTaskReferences(String filterText) {
    List<ResourceTaskReference> refs = Util.newArrayList();
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
