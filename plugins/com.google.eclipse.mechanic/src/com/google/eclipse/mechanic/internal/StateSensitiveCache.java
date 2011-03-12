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

import com.google.eclipse.mechanic.IMechanicService;
import com.google.eclipse.mechanic.IStatusChangeListener;
import com.google.eclipse.mechanic.MechanicStatus;
import com.google.eclipse.mechanic.StatusChangedEvent;

/**
 * A {@link IUriContentProvider} that clears its cache when the Mechanic starts a
 * scanning session.
 */
public final class StateSensitiveCache implements IUriContentProvider {
  private final IMechanicService service;
  private final IStatusChangeListener statusChangeListener;
  private final IUriContentProvider delegate;

  public StateSensitiveCache(IMechanicService service, IUriContentProvider delegate) {
    this.service = Util.checkNotNull(service);
    this.delegate = Util.checkNotNull(delegate);
    this.statusChangeListener = new IStatusChangeListener() {
      public void statusChanged(StatusChangedEvent event) {
        if (event.getStatus() == MechanicStatus.UPDATING) {
          StateSensitiveCache.this.delegate.clear();
        }
      }
    };
  }

  public void initialize() {
    service.addTaskStatusChangeListener(statusChangeListener);
  }

  public void dispose() {
    service.removeTaskStatusChangeListener(statusChangeListener);
  }

  public InputStream get(URI uri) throws IOException {
    return delegate.get(uri);
  }

  public long lastModifiedTime(URI uri) throws IOException {
    return delegate.lastModifiedTime(uri);
  }

  public void clear() {
    delegate.clear();
  }
}
