/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.TaskScanner;
import com.google.eclipse.mechanic.CompositeTaskInterface;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Code behind the {@code com.google.eclipse.mechanic.scanners} extension point.
 *
 * <p>This class interfaces with the {@link Platform}, reading all extensions of the
 * {@code scanners} extension point, providing a mechanism for translating their
 * implementations to instances of {@link TaskScanner}.
 *
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public class ScannersExtensionPoint {
  private static final String EXTENSION_POINT_NAME = "scanners";
  private static final String TAG_SCANNER = "scanner";
  private static final String ATTR_CLASS = "class";
  private static final Logger LOG = Logger.getLogger(ScannersExtensionPoint.class.getName());

  // Initialization On Demand Holder Idiom
  // http://crazybob.org/2007/01/lazy-loading-singletons.html
  private static class SingletonHolder {
    static List<ScannerProxy> instance = init();    
  }

  private static List<ScannerProxy> getScannerProxies() {
    return SingletonHolder.instance;
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
   * Adds instances of {@link CompositeTaskInterface} to the {@code collector}.
   */
  public List<TaskScanner> getScanners() {
    List<TaskScanner> scanners = new ArrayList<TaskScanner>();
    Iterable<ScannerProxy> proxies = getScannerProxies();
    for (ScannerProxy proxy : proxies) {
      try {
        // This is instantiating the real class from the proxy.
        TaskScanner scanner =
            (TaskScanner) proxy.element.createExecutableExtension(ATTR_CLASS);
        scanners.add(scanner);
      } catch (CoreException e) {
        LOG.log(Level.SEVERE, String.format(
            "Exception creating CompositeTask %s", proxy.clazz), e);
      } catch (ClassCastException e) {
        LOG.log(Level.SEVERE, String.format(
            "Exception creating CompositeTask %s", proxy.clazz), e);
      }
    }
    return scanners;
  }

  private static List<ScannerProxy> init() {
    List<ScannerProxy> list = Util.newArrayList();

    // Load the reference to the extension.
    IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
        MechanicPlugin.PLUGIN_ID, EXTENSION_POINT_NAME);

    if (point == null) {
      LOG.log(Level.SEVERE, "Cannot load extension point " + MechanicPlugin.PLUGIN_ID + "/"
           + EXTENSION_POINT_NAME);
    } else {
      // This loads all the registered extensions to this point.
      IExtension[] extensions = point.getExtensions();
      for (IExtension extension : extensions) {
        IConfigurationElement[] configurationElements = extension.getConfigurationElements();
        for (IConfigurationElement element : configurationElements) {
          ScannerProxy proxy = ScannerProxy.create(element);
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
   * <p>e.g. the example below will be converted to an instance of ScannerProxy where
   * {@code clazz} is {@code com.google.eclipse.launch.tasks.PreserveLaunchConfigurations}:
   *
   * <br><pre>{@literal
   *<extension point="com.google.eclipse.mechanic.scanners">
   *  <scanner class="com.google.eclipse.mechanic.internal.ClassFileTaskLoader"/>
   *</extension>
   * }</pre>
   * 
   * <p>Rather than instantiating classes defined in the extension point, the
   * proxy is used in its place. This is typical Eclipse OSGI extension behavior:
   * it prevents superfluous plug-in activation, et cetera.
   */
  private static final class ScannerProxy {

    private final String clazz;
    private final IConfigurationElement element;

    /**
     * Creates a ScannerProxy.
     *
     * @throws NullPointerException if {@code element} or {@code clazz} are {@code null} 
     */
    private ScannerProxy(IConfigurationElement element, String clazz) {
      this.element = Util.checkNotNull(element, "'element' should not be null");
      this.clazz = Util.checkNotNull(clazz, "'clazz' should not be null");
    }

    /**
     * Creates an ScannerProxy instance from a configuration element.
     *
     * <p>(Configuration elements are proxies to entries in plugin.xml)
     */
    public static ScannerProxy create(IConfigurationElement element) {
      if (!element.getName().equals(TAG_SCANNER)) {
        return null;
      }
      String className = element.getAttribute(ATTR_CLASS);
      if (className == null) {
        LOG.log(Level.WARNING, "Missing attribute " + ATTR_CLASS);
        return null;
      }

      return new ScannerProxy(element, className);
    }
  }
}
