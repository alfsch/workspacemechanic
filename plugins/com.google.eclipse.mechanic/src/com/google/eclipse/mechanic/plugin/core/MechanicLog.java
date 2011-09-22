/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.core;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

/**
 * Abstraction on top of {@link ILog}.
 */
public class MechanicLog {
  private static MechanicLog DEFAULT;

  private final ILog log;

  /**
   * Get the default instance.
   */
  public synchronized static MechanicLog getDefault() {
    if (DEFAULT == null) {
      DEFAULT = new MechanicLog(MechanicPlugin.getDefault());
    }
    return DEFAULT;
  }

  MechanicLog(Plugin plugin) {
    this(plugin.getLog());
  }

  public MechanicLog(ILog log) {
    this.log = log;
  }

  /**
   * Log a status.
   */
  public void log(IStatus status) {
    log.log(status);
  }

  /**
   * Log an error to the Eclipse log.
   *
   * @param t the throwable.
   * @param fmt string format
   * @param args args that accompany the string format. If this is empty, fmt is assumed to be
   * an unformatted string.
   */
  public void logError(Throwable t, String fmt, Object... args) {
    String text = (args.length > 0) ? String.format(fmt, args) : fmt;
    log(new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID, text, t));
  }

  /**
   * Log an error to the Eclipse log, using the exception's message as the log message text.
   *
   * @param t the throwable.
   */
  public void logError(Throwable t) {
    logError(t, t.getMessage());
  }

  /**
   * Log info to the Eclipse log.
   *
   * @param fmt string format
   * @param args args that accompany the string format. If this is empty, fmt is assumed to be
   * an unformatted string.
   */
  public void logInfo(String fmt, Object... args) {
    log(IStatus.INFO, fmt, args);
  }

  /**
   * Log warning to the Eclipse log.
   *
   * @param fmt string format
   * @param args args that accompany the string format. If this is empty, fmt is assumed to be
   * an unformatted string.
   */
  public void logWarning(String fmt, Object... args) {
    log(IStatus.WARNING, fmt, args);
  }


  /**
   * Log a message to the Eclipse log.
   *
   * @param severity message severity. Reference {@link IStatus#getSeverity()}.
   * @param fmt string format
   * @param args args that accompany the string format. If this is empty, fmt is assumed to be
   * an unformatted string.
   */
  public void log(int severity, String fmt, Object... args) {
    String text = (args.length > 0) ? String.format(fmt, args) : fmt;
    log(new Status(severity, MechanicPlugin.PLUGIN_ID, text));
  }
}
