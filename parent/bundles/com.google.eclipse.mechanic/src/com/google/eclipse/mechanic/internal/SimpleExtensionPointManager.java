/*******************************************************************************
 * Copyright (C) 2014, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.google.common.collect.Lists;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

/**
 * Utilities for working with simple extension points.
 *
 * <p>These extension points need to be limited to those that take a single
 * {@code class} attribute, with an optional {@code forcePluginActivation} argument.
 */
public class SimpleExtensionPointManager<T> {
  private static final Logger LOG = Logger.getLogger(ScannersExtensionPoint.class.getName());

  private final List<SimpleProxy<T>> proxies;

  private SimpleExtensionPointManager(
      String extensionPointName,
      Class<T> klass,
      String elementName,
      String classNameAttr,
      String forcePluginActivationAttr) {
    this.proxies = createProxies(
        extensionPointName, klass, elementName, classNameAttr, forcePluginActivationAttr);
  }

  /**
   * Create a new instance, which reads all plugin configurations, and prepares for loading
   * instances.
   *
   * @param extensionPointName the name of the extension point.
   * @param klass the base type of all the items in this proxy
   * @param elementName the element name that represents the configuration
   * @param classNameAttr the attribute name representing the class being built.
   * @param forcePluginActivationAttr the attribute name representing whether the class should be
   * instantiated even if the plugin hasn't been activated. When this is {@code null} it means the
   * extension point doesn't support variable plugin activation, and classes will be instantiated
   * even if that means starting 
   * @return A list of {@code SimpleProxy} items which can be passed to {@link #activateProxies(Collection)}
   */
  public static <T> SimpleExtensionPointManager<T> newInstance(
      String extensionPointName,
      Class<T> klass,
      String elementName,
      String classNameAttr,
      String forcePluginActivationAttr) {
    return new SimpleExtensionPointManager<T>(
        extensionPointName, klass, elementName, classNameAttr, forcePluginActivationAttr);
  }

  private List<SimpleProxy<T>> createProxies(
      String extensionPointName,
      Class<T> klass,
      String elementName,
      String classNameAttr,
      String forcePluginActivationAttr) {
    List<SimpleProxy<T>> proxies = Lists.newArrayList();

    // Load the reference to the extension.
    IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
        MechanicPlugin.PLUGIN_ID, extensionPointName);

    if (point == null) {
      LOG.log(Level.SEVERE,
          "Cannot load extension point " + MechanicPlugin.PLUGIN_ID + "/" + extensionPointName);
      return proxies;
    }
    // This loads all the registered extensions to this point.
    IExtension[] extensions = point.getExtensions();
    for (IExtension extension : extensions) {
      IConfigurationElement[] configurationElements = extension.getConfigurationElements();
      for (IConfigurationElement element : configurationElements) {
        if (element.getName().equals(elementName)) {
          SimpleProxy<T> proxy =
              SimpleProxy.create(klass, element, classNameAttr, forcePluginActivationAttr);
          // If parseType has an error, it returns null.
          if (proxy != null) {
            proxies.add(proxy);
          }
        }
      }
    }

    return proxies;
  }

  public List<T> getInstances() {
    List<T> instances = new ArrayList<T>();
    for (SimpleProxy<T> proxy : proxies) {
      T instance = proxy.createInstance();
      if (instance != null) {
        instances.add(instance);
      }
    }
    return Collections.unmodifiableList(instances);
  }
}
