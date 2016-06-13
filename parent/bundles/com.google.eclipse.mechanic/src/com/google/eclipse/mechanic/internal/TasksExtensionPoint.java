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

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.eclipse.mechanic.CompositeTaskInterface;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

/**
 * Code behind the {@code com.google.eclipse.mechanic.tasks} extension point.
 *
 * <p>This class interfaces with the {@link Platform}, reading all extensions of the
 * {@code tasks} extension point, providing a mechanism for translating their
 * implementations to instances of {@link CompositeTaskInterface}.
 */
public class TasksExtensionPoint {
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

  private TasksExtensionPoint() {
  }

  /**
   * Return the {@link IResourceTaskProvider} supplier, initializing it if required.
   *
   * <p>The supplier is memoized, so it will return the same instantiated
   * objects upon repeated calls.
   */
  public static Supplier<List<CompositeTaskInterface>> getInstance() {
    return Suppliers.memoize(new Supplier<List<CompositeTaskInterface>>() {
      public List<CompositeTaskInterface> get() {
        return SingletonHolder.instance.getInstances();
      }
    });
  }

  /**
   * Clears the list of tasks.
   * 
   * <p><em>This should only be called by {@link MechanicPlugin#stop}.</em>
   */
  public static void dispose() {
    SingletonHolder.instance = null;
  }
}
