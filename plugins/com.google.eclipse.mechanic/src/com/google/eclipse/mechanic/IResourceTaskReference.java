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

import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;

/**
 * A reference to a resource-based task.
 */
public interface IResourceTaskReference {
  /** Return the provider that created this task reference. */
  ResourceTaskProvider getProvider();

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
   * Return the File representation of this resource. Is {@code null} when this file isn't on disk.
   */
  File asFile();

  /**
   * Return the MD5 hash of this task
   */
  long computeMD5() throws IOException;
}
