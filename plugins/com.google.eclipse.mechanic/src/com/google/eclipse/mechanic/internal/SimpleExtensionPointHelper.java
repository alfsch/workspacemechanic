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
public class SimpleExtensionPointHelper {
  private static final Logger LOG = Logger.getLogger(ScannersExtensionPoint.class.getName());

  public static <T> List<SimpleProxy<T>> createProxies(String extensionPointName,
      Class<T> klass, String elementName, String classNameAttr, String forcePluginActivationAttr) {
    List<SimpleProxy<T>> proxies = Lists.newArrayList();

    // Load the reference to the extension.
    IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
        MechanicPlugin.PLUGIN_ID, extensionPointName);

    if (point == null) {
      LOG.log(Level.SEVERE, "Cannot load extension point " + MechanicPlugin.PLUGIN_ID + "/"
           + extensionPointName);
    } else {
      // This loads all the registered extensions to this point.
      IExtension[] extensions = point.getExtensions();
      for (IExtension extension : extensions) {
        IConfigurationElement[] configurationElements = extension.getConfigurationElements();
        for (IConfigurationElement element : configurationElements) {
          SimpleProxy<T> proxy =
              SimpleProxy.create(klass, element, elementName, classNameAttr, forcePluginActivationAttr);
          // If parseType has an error, it returns null.
          if (proxy != null) {
            proxies.add(proxy);
          }
        }
      }
    }

    return Collections.unmodifiableList(proxies);

  }

  public static <T> List<T> activateProxies(Collection<SimpleProxy<T>> proxies) {
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
