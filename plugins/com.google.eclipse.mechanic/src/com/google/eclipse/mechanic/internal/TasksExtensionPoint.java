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

import com.google.eclipse.mechanic.CompositeTaskInterface;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

/**
 * Code behind the {@code com.google.eclipse.mechanic.tasks} extension point.
 *
 * <p>This class interfaces with the {@link Platform}, reading all extensions of the
 * {@code tasks} extension point, providing a mechanism for translating their
 * implementations to instances of {@link CompositeTaskInterface}.
 */
public class TasksExtensionPoint implements Supplier<List<CompositeTaskInterface>> {
  private static final String EXTENSION_POINT_NAME = "tasks";
  private static final String TAG_TASK = "task";
  private static final String ATTR_CLASS = "class";
  private static final String ATTR_FORCE_PLUGIN_ACTIVATION = "forcePluginActivation";

  // Initialization On Demand Holder Idiom
  // http://crazybob.org/2007/01/lazy-loading-singletons.html
  private static class SingletonHolder {
    static SimpleExtensionPointManager<CompositeTaskInterface> instance =
        SimpleExtensionPointManager.newInstance(
            EXTENSION_POINT_NAME,
            CompositeTaskInterface.class,
            TAG_TASK,
            ATTR_CLASS,
            ATTR_FORCE_PLUGIN_ACTIVATION);
  }

  /**
   * Clears the list of tasks.
   * 
   * <p><em>This should only be called by {@link MechanicPlugin#stop}.</em>
   */
  public static void dispose() {
    SingletonHolder.instance = null;
  }

  /**
   * Return as many registered extensions of {@link CompositeTaskInterface} as possible.
   */
  public List<CompositeTaskInterface> get() {
    return SingletonHolder.instance.getInstances();
  }
}
