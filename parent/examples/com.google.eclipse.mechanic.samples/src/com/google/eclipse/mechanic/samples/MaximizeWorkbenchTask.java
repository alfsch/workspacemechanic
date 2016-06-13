/*******************************************************************************
 * Copyright (C) 2014, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.samples;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.eclipse.mechanic.CompositeTask;

/**
 * Complete tasks should always subclass {@link CompositeTask}. There is no need to
 * directly implement {@link com.google.eclipse.mechanic.CompositeTaskInterface}.
 *
 * <p>By and large they should implement {@link #getTitle()}, {@link #getId()},
 * {@link #getDescription()}, {@link #evaluate()} and {@link #run()}, and that's it.
 */
public class MaximizeWorkbenchTask extends CompositeTask {

  public String getTitle() {
    return "Workbench window is not maximized";
  }

  public String getDescription() {
    return "Fix me to maximize the workbench.";
  }

  public String getId() {
    return getClass().getName();
  }

  /**
   * Called to evaluate whether the environment adheres to this test.
   */
  public boolean evaluate() {
    if (!PlatformUI.isWorkbenchRunning()) {
      return true;
    }
    final IWorkbench workbench = PlatformUI.getWorkbench();
    if (workbench == null) {
      return true;
    }

    // local array hack.
    final boolean[] result = new boolean[] { true };
    Display.getDefault().syncExec(new Runnable() {
      public void run() {
        IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
        if (activeWorkbenchWindow == null) {
          return;
        }
        Shell shell = activeWorkbenchWindow.getShell();
        if (shell == null) {
          return;
        }
        result[0] = shell.getMaximized();
      };
    });
    return result[0];
  }

  /**
   * Called to bring the environment in compliance with this test.
   */
  public void run() {
    Display.getDefault().syncExec(new Runnable() {
      public void run() {
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setMaximized(true);
      }
    });
  }
}
