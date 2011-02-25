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

public final class ProxyUriContentProvider implements IUriContentProvider {

  private volatile IUriContentProvider delegate;

  public ProxyUriContentProvider(IUriContentProvider delegate) {
    this.delegate = delegate;
  }

  public void set(IUriContentProvider delegate) {
    this.delegate = Util.checkNotNull(delegate);
  }

  public InputStream get(URI uri) throws IOException {
    return delegate.get(uri);
  }

  public void clear() {
    delegate.clear();
  }
}
