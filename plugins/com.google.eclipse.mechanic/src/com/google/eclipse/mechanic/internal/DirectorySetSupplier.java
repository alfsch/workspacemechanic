// Copyright 2010 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.internal;

import java.io.File;
import java.util.List;

/**
 * Supplies a list of directories.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface DirectorySetSupplier {

  /**
   * Returns a {@link List} of directories known to be both existing and
   * readable at the time they are returned.
   */
  List<File> get();
}
