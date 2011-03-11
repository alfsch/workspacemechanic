/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic;

import java.util.List;

import org.eclipse.core.runtime.IStatus;

/**
 * Provides resource-based tasks.
 *
 * <b>Note: this API needs some rework, and is very likely to change.
 */
public interface IResourceTaskProvider {
  /**
   * Provides post-initialization validation of a provider.
   *
   * @return status of the initialization.
   */
  IStatus initialize();

  /**
   * TODO(konigsberg): Remove the filter parameter.
   */
  List<IResourceTaskReference> getTaskReferences(String extFilter);


  /**
   * TODO(konigsberg): Remove the pathFilter
   */
  List<IResourceTaskReference> getTaskReferences(String localPath, String extFilter);
}
