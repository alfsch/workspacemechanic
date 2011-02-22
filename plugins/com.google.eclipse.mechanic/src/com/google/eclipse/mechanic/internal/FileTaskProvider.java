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

import com.google.eclipse.mechanic.SuffixFileFilter;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskReference;

public final class FileTaskProvider extends ResourceTaskProvider {
  private final File dir;

  /**
   * This is only public for ClassFileTaskScanner. See if we can make this private.
   */
  public final class TaskReference implements ResourceTaskReference {
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
  }

  public FileTaskProvider(File dir) {
    this.dir = Util.checkNotNull(dir);
  }

  /**
   * REMOVE as we move things along.
   */
  public File getFile() {
    return dir;
  }

  @Override
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

  @Override
  public List<ResourceTaskReference> getTaskReferences(String localPath, String filter) {
    // dir points to the root of the task directory, we
    // add the PACKAGE PATH to point to the dir with classes.
    File localDir = new File(createPath(dir.getAbsolutePath(), localPath));
    return new FileTaskProvider(localDir).getTaskReferences(filter);
  }

  /**
   * Returns all elements joined by the local OS's path segment separator.
   */
  private static String createPath(String... elems) {
    return Util.join(File.separator, elems);
  }

  public List<ResourceTaskReference> getTaskReferences(String filterText) {
    SuffixFileFilter filter = new SuffixFileFilter(filterText);

    File[] filesInDir = dir.listFiles(filter);
    if (filesInDir == null) {
      return Collections.emptyList();
    }
    List<ResourceTaskReference> refs = Util.newArrayList();
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
