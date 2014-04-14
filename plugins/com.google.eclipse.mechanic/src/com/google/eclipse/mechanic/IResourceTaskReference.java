/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A reference to a resource-based task.
 *
 * <p>Overriding toString
 * <p>Since some tasks can come from disk, and it's expected that most of them will,
 * task scanners should rely on a null-test of {@link #asFile()} to see if that's the case.
 * So when identifying if a resource has changed, the caller might typically rely on
 * {@link #getLastModified()}, which works great if it's for a file, but not for a URL-based
 * resource. In that case, use {@link #computeMD5()}.
 */
public interface IResourceTaskReference {
  /** Return the provider that created this task reference. */
  IResourceTaskProvider getProvider();

  /** Return the name of the task reference. This is typically a local name. */
  String getName();

  /** Return the task reference as an input stream. */
  InputStream newInputStream() throws IOException;

  /**
   * Return the time this resource was last modified, in milliseconds since the epoch.
   */
  long getLastModified() throws IOException;

  /** Return the task reference path. This is typically a full path. */
  String getPath();

  /**
   * Return the File representation of this resource. Is {@code null} it's not a File.
   */
  File asFile();

  /**
   * Return the MD5 hash of this task
   */
  long computeMD5() throws IOException;
}
