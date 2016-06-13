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

/**
 * Supplies InputStreams from URIs.
 */
public interface IUriContentProvider {
  /**
   * Fetch the {@link InputStream} for this {@link URI}.
   */
  InputStream get(URI uri) throws IOException;

  /**
   * Fetch the last modified time of this {@link URI} in milliseconds since the epoch.
   *
   * This function can return 0 if the last modified time is not known.
   */
  long lastModifiedTime(URI uri) throws IOException;

  /**
   * Clear any cached information associated with this content provider.
   */
  void clear();
}
