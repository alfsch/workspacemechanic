// Copyright 2010 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.internal;

import java.util.List;

import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;

/**
 * Supplies a list of task sources.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface TaskSourcesSupplier {

  /**
   * Returns a {@link List} of valid task providers.
   */
  List<ResourceTaskProvider> get();
}
