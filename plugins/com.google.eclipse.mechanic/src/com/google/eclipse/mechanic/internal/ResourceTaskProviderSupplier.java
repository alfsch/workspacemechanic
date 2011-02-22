/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.util.List;

import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;

/**
 * Supplies a list of task sources.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface ResourceTaskProviderSupplier {

  /**
   * Returns a {@link List} of valid task providers.
   */
  List<ResourceTaskProvider> get();
}
