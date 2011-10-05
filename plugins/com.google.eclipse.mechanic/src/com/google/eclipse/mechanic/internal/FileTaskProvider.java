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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.SuffixFileFilter;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;

public final class FileTaskProvider extends ResourceTaskProvider {
  private final File dir;

  /**
   * This is only public for ClassFileTaskScanner. See if we can make this private.
   */
  public final class TaskReference implements IResourceTaskReference {
    private final File file;

    public TaskReference(File file) {
      this.file = file;
    }

    public ResourceTaskProvider getProvider() {
      return FileTaskProvider.this;
    }

    public InputStream newInputStream() throws FileNotFoundException {
      return new FileInputStream(file);
    }

    public String getName() {
      return file.getName();
    }

    public String getPath() {
      return file.getPath();
    }

    @Override
    public String toString() {
      return file.getPath();
    }

    public long getLastModified() {
      return file.lastModified();
    }

    public File asFile() {
      return file;
    }
  }

  private static final String SEPARATOR = System.getProperty("file.separator");

  public FileTaskProvider(File dir) {
    dir = Preconditions.checkNotNull(dir);
    String path = dir.getPath();
    if (path.startsWith("~" + SEPARATOR)) {
      dir = new File(System.getProperty("user.home"), path.substring(1));
    } else if (path.startsWith(".."  + SEPARATOR)) {
      dir = new File(new File(System.getProperty("user.dir"), ".."), path.substring(2));
    } else if (path.startsWith("." + SEPARATOR)) {
      dir = new File(System.getProperty("user.dir"), path.substring(1));
    }
    this.dir = dir;
  }
  /**
   * REMOVE as we move things along.
   */
  public File getFile() {
    return dir;
  }

  public IStatus initialize() {
    if (!dir.exists()) {
      return new Status(IStatus.WARNING, MechanicPlugin.PLUGIN_ID, 
         String.format("Directory '%s' does not exist.", dir));
    }
    if (!dir.canRead()) {
      return new Status(IStatus.WARNING, MechanicPlugin.PLUGIN_ID, 
          String.format("Directory '%s' is not readable.", dir));
    }
    return Status.OK_STATUS;
  }

  public List<IResourceTaskReference> getTaskReferences(String localPath, String filter) {
    // dir points to the root of the task directory, we
    // add the PACKAGE PATH to point to the dir with classes.
    File localDir = new File(createPath(dir.getAbsolutePath(), localPath));
    return new FileTaskProvider(localDir).getTaskReferences(filter);
  }

  /**
   * Returns all elements joined by the local OS's path segment separator.
   */
  private static String createPath(String... elems) {
    return Joiner.on(File.separator).join(elems);
  }

  public List<IResourceTaskReference> getTaskReferences(String filterText) {
    SuffixFileFilter filter = new SuffixFileFilter(filterText);

    File[] filesInDir = dir.listFiles(filter);
    if (filesInDir == null) {
      return Collections.emptyList();
    }
    List<IResourceTaskReference> refs = Lists.newArrayList();
    for (File file : filesInDir) {
      refs.add(new TaskReference(file));
    }
    return refs;
  }

  @Override
  public String toString() {
    return "File provider: " + dir.getPath();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof FileTaskProvider)) {
      return false;
    }

    return ((FileTaskProvider) obj).dir.equals(dir);
  }

  @Override
  public int hashCode() {
    return dir.hashCode();
  }
}
