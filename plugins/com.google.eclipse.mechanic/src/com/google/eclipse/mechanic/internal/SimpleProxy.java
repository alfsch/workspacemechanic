package com.google.eclipse.mechanic.internal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.CompositeTaskInterface;

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
final class SimpleProxy<T> {
  private static final Logger LOG = Logger.getLogger(SimpleProxy.class.getName());

  private final Class<T> clazz;
  private final IConfigurationElement element;
  private final String classAttr;
  private final boolean forcePluginActivation;

  private SimpleProxy(
      Class<T> clazz, IConfigurationElement element, String classAttr,
      boolean forcePluginActivation) {
    this.clazz = clazz;
    this.element = element;
    this.classAttr = classAttr;
    this.forcePluginActivation = forcePluginActivation;
  }

  /**
   * Creates a new instance of {@code T} or returns {@code null} on failure.
   */
  public T createInstance() {
    String className = element.getAttribute(classAttr);
    try {
      String bundleName = element.getContributor().getName();
      if (forcePluginActivation || isBundleActive(bundleName)) {
        // This is instantiating the real class from the proxy.
        Object object = element.createExecutableExtension(classAttr);
        return clazz.cast(object);
      }
    } catch (CoreException e) {
      LOG.log(Level.SEVERE,
          String.format("Exception creating CompositeTask %s", className), e);
    } catch (ClassCastException e) {
      LOG.log(Level.SEVERE,
          String.format("Exception creating CompositeTask %s", className), e);
    }
    return null;
  }

  /**
   * Creates a proxy instance from a configuration element.
   *
   * <p>When the element doesn't match the expected element name, this returns
   * {@code null} and should be ignored. Same for when the className attribute
   * is missing.
   * <p>(Configuration elements are proxies to entries in plugin.xml)
   *
   * @param clazz the type of object this proxy creates.
   * @param element the configuration element
   * @param classNameAttr the element attribute
   * @param forcePluginActivationAttr if not {@code null}, the attribute for
   *   forcing plugin activation.
   * @throws NullPointerException if any non-optional parameter is {@code null}
   */
  public static <T> SimpleProxy<T> create(
      Class<T> clazz, IConfigurationElement element, String classNameAttr,
      String forcePluginActivationAttr) {
    Preconditions.checkNotNull(clazz, "'clazz' should not be null");
    Preconditions.checkNotNull(element, "'element' should not be null");
    Preconditions.checkNotNull(classNameAttr, "'classNameAttr' should not be null");

    String className = element.getAttribute(classNameAttr);
    if (className == null) {
      LOG.log(Level.WARNING, "Missing attribute " + classNameAttr);
      return null;
    }

    boolean forcePluginActivation = true;
    if (forcePluginActivationAttr != null) {
      String attribute = element.getAttribute(forcePluginActivationAttr);
      forcePluginActivation = Boolean.parseBoolean(attribute);
    }

    return new SimpleProxy<T>(clazz, element, classNameAttr, forcePluginActivation);
  }

  private boolean isBundleActive(String bundleName) {
    return Platform.getBundle(bundleName).getState() == Bundle.ACTIVE;      
  }
}