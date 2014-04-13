/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic;

/**
 * Provides resource-based tasks.
 *
 * <b>Note: this API needs some rework, and is very likely to change.
 */
public interface IResourceTaskProvider {
  /**
   * Collect task references provided by this provider.
   *
   * <p>In theory this is similar to calling
   * {@code collectTaskReferences(".", extFilter, collector)}, but
   * that's in theory only.
   *
   * @param extFilter the filename extension to filter.
   * @param collector the collector to receive all task references.
   *
   * TODO(konigsberg): Remove the filter parameter.
   */
  void collectTaskReferences(
      String extFilter,
      ICollector<IResourceTaskReference> collector);

  /**
   * Collect task references provided by this provider, relative to the
   * provider's root.
   *
   * <p>This is only used for finding Class-based tasks, and is not even
   * implemented in the UriTaskProvider.
   *
   * @param localPath the relative path to the resources
   * @param extFilter the filename extension to filter.
   * @param collector the collector to receive all task references.
   *
   * TODO(konigsberg): Remove the filter parameter.
   */
  void collectTaskReferences(
      String localPath,
      String extFilter,
      ICollector<IResourceTaskReference> collector);
}
