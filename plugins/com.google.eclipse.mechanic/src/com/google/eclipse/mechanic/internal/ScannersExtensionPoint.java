/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.util.List;

import org.eclipse.core.runtime.Platform;

import com.google.eclipse.mechanic.TaskScanner;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

/**
 * Code behind the {@code com.google.eclipse.mechanic.scanners} extension point.
 *
 * <p>This class interfaces with the {@link Platform}, reading all extensions of the
 * {@code scanners} extension point, providing a mechanism for translating their
 * implementations to instances of {@link TaskScanner}.
 */
public class ScannersExtensionPoint implements ScannersExtensionPointInterface {
  private static final String EXTENSION_POINT_NAME = "scanners";
  private static final String TAG_SCANNER = "scanner";
  private static final String ATTR_CLASS = "class";

  // Initialization On Demand Holder Idiom
  // http://crazybob.org/2007/01/lazy-loading-singletons.html
  private static class SingletonHolder {
    static SimpleExtensionPointManager<TaskScanner> instance =
        SimpleExtensionPointManager.newInstance(
            EXTENSION_POINT_NAME,
            TaskScanner.class,
            TAG_SCANNER,
            ATTR_CLASS,
            null);
  }

  /**
   * Clears the list of scanners.
   * 
   * <p><em>This should only be called by {@link MechanicPlugin#stop}.</em>
   */
  public static void dispose() {
    SingletonHolder.instance = null;
  }

  /**
   * Return as many registered extensions of {@link TaskScanner} as possible.
   */
  public List<TaskScanner> get() {
    return SingletonHolder.instance.getInstances();
  }
}
