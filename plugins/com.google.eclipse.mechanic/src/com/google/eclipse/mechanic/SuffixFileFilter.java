/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.io.File;
import java.io.FileFilter;

/**
 * Simple suffix FileFilter. Matches the value supplied to the constructor at
 * the end of a file name (inclusive of the extension).
 *
 * <br/><br/>
 *
 * For example:
 * <pre>
 * FileFilter filter = new SuffixFileFilter(".class");
 * for (File file : dir.listFiles(filter)) {
 *   System.out.pringln("Matched file: " + file.getName());
 * }
 * </pre>
 * Would print out all files in a directory that ended with ".class".
 *
 * @author smckay@google.com (Steve McKay)
 */
public final class SuffixFileFilter implements FileFilter {

  private final String suffix;

  public SuffixFileFilter(String suffix) {
    this.suffix = suffix;
  }

  public boolean accept(File file) {
    return file.getName().endsWith(suffix);
  }
}
