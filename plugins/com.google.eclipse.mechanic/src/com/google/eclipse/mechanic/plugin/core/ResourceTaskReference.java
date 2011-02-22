/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * A reference to a resource-based task.
 */
public interface ResourceTaskReference {
  ResourceTaskProvider getProvider();
  String getName();
  InputStream newInputStream() throws IOException;
  String getPath();
}
