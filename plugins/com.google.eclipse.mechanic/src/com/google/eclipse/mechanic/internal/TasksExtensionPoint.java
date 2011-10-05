/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.CompositeTaskInterface;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Code behind the {@code com.google.eclipse.mechanic.tasks} extension point.
 *
 * <p>This class interfaces with the {@link Platform}, reading all extensions of the
 * {@code tasks} extension point, providing a mechanism for translating their
 * implementations to instances of {@link CompositeTaskInterface}.
 *
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public class TasksExtensionPoint {
  private static final String EXTENSION_POINT_NAME = "tasks";
  private static final String TAG_TASK = "task";
  private static final String ATTR_CLASS = "class";
  private static final String ATTR_FORCE_PLUGIN_ACTIVATION = "forcePluginActivation";
  private static final Logger LOG = Logger.getLogger(TasksExtensionPoint.class.getName());

  // Initialization On Demand Holder Idiom
  // http://crazybob.org/2007/01/lazy-loading-singletons.html
  private static class SingletonHolder {
    static List<TaskProxy> instance = init();
  }

  private static List<TaskProxy> getTaskProxies() {
    return SingletonHolder.instance;
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
   * Add instances of {@link CompositeTaskInterface} to the {@code collector}.
   */
  public void getTasks(TaskCollector collector) {
    Iterable<TaskProxy> proxies = getTaskProxies();
    for (TaskProxy proxy : proxies) {
      try {
        String bundleName = proxy.element.getContributor().getName();
        if (isBundleActive(bundleName) || proxy.forcePluginActivation) {
          // This is instantiating the real class from the proxy.
          CompositeTaskInterface task =
              (CompositeTaskInterface) proxy.element.createExecutableExtension(ATTR_CLASS);
          collector.add(task);
        }
      } catch (CoreException e) {
        LOG.log(Level.SEVERE, String.format(
            "Exception creating CompositeTaskInterface %s", proxy.clazz), e);
      } catch (ClassCastException e) {
        LOG.log(Level.SEVERE, String.format(
            "Exception creating CompositeTaskInterface %s", proxy.clazz), e);
      }
    }
  }

  private boolean isBundleActive(String bundleName) {
    return Platform.getBundle(bundleName).getState() == Bundle.ACTIVE;      
  }

  private static List<TaskProxy> init() {
    List<TaskProxy> list = new ArrayList<TaskProxy>();

    // Load the reference to the extension.
    IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
        MechanicPlugin.PLUGIN_ID, EXTENSION_POINT_NAME);

    if (point == null) {
      LOG.log(Level.SEVERE, "Cannot load extension point " + MechanicPlugin.PLUGIN_ID + "/"
           + EXTENSION_POINT_NAME);
    } else {
      // This loads all the registered extensions to this point.
      IExtension[] extensions  = point.getExtensions();
      for (IExtension extension : extensions) {
        IConfigurationElement[] configurationElements = extension.getConfigurationElements();
        for (IConfigurationElement element : configurationElements) {
          TaskProxy proxy = TaskProxy.create(element);
          // If parseType has an error, it returns null.
          if (proxy != null) {
            list.add(proxy);
          }
        }
      }
    }

    return Collections.unmodifiableList(list);
  }
 
  /**
   * Proxy object for a {@link CompositeTaskInterface}.
   *
   * <p>e.g. the example below will be converted to an instance of TaskProxy where
   * {@code clazz} is {@code com.google.team.launch.tasks.PreserveLaunchConfigurations}:
   *
   * <br><pre>{@literal
   *<extension point="com.google.eclipse.mechanic.tasks">
   *  <task class="com.google.team.launch.tasks.PreserveLaunchConfigurations"/>
   *</extension>
   * }</pre><p>
   * 
   * <p>Rather than instantiating classes defined in the extension point, the
   * proxy is used in its place. This is typical Eclipse OSGI extension behavior:
   * it prevents superfluous plug-in activation, et cetera.
   */
  private static final class TaskProxy {

   private final String clazz;
    private final boolean forcePluginActivation;
    private final IConfigurationElement element;

    /**
     * Creates a TaskProxy.
     *
     * @throws NullPointerException if {@code element} or {@code clazz} are {@code null} 
     */
    private TaskProxy(IConfigurationElement element, String clazz, boolean forcePluginActivation) {
      this.element = Preconditions.checkNotNull(element, "'element' should not be null");
      this.clazz = Preconditions.checkNotNull(clazz, "'clazz' should not be null");
      this.forcePluginActivation = forcePluginActivation;
    }

    /**
     * Creates a TaskProxy instance from a configuration element.
     *
     * <p>(Configuration elements are proxies to entries in plugin.xml)
     */
    public static TaskProxy create(IConfigurationElement element) {
      if (!element.getName().equals(TAG_TASK)) {
        return null;
      }
      String className = element.getAttribute(ATTR_CLASS);
      if (className == null) {
        LOG.log(Level.WARNING, "Missing attribute " + ATTR_CLASS);
        return null;
      }
      String attribute = element.getAttribute(ATTR_FORCE_PLUGIN_ACTIVATION);
      boolean forcePluginActivation = Boolean.parseBoolean(attribute);

      return new TaskProxy(element, className, forcePluginActivation);
    }
  }
}
