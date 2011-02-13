/*******************************************************************************
 * Copyright (C) 2011, Google.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * UI-portion of the error popup.
 */
public class AbstractPopup {
  private final Display display;
  private final Shell shell;

  private final Color backgroundColor;

  /*
   * Display time in milliseconds. When zero, the display doesn't automatically
   * close.
   */
  private int displayTimeMillis = 0;

  public AbstractPopup(Display display) {
    this(display, SWT.BORDER);
  }

  public AbstractPopup(Display display, int type) {
    this.display = display;
    this.shell = new Shell(display, type);
    backgroundColor = new Color(display, 230, 230, 255);
  }

  public AbstractPopup setDisplayTimeMillis(int displayTimeMillis) {
    this.displayTimeMillis = displayTimeMillis;
    return this;
  }

  public Shell getShell() {
    return this.shell;
  }

  protected Display getDisplay() {
    return display;
  }

  public void open() {
    shell.setLayout(new FillLayout());
    shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
    shell.setBackground(backgroundColor);
    Composite top = new Group(shell, SWT.NONE);
    // Closes when clicking in some whitespace, but not enough.
    // TODO(konigsberg): close when clicking anywhere but whitespace.
    top.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseUp(MouseEvent e) {
        close();
      }
    });

    initializeLayout(top);

    Composite titleBar = new Composite(top, SWT.NONE);
    initializeLayout(titleBar);
    createTitleBar(titleBar);

    Composite popupArea = new Composite(top, SWT.NONE);
    initializeLayout(popupArea);
    createContents(popupArea);

    shell.pack();

    setPosition(shell);
    shell.setVisible(true);
    if (displayTimeMillis > 0) {
      display.timerExec(displayTimeMillis, new Runnable() {
        public void run() {
          close();
        }
      });
    }
  }

  protected void createTitleBar(Composite parent) {
  }

  public void createContents(Composite parent) {
  }

  private void initializeLayout(Composite widget) {
    RowLayout layout = new RowLayout();
    layout.type = SWT.VERTICAL;
    layout.marginTop = 5;
    layout.marginBottom = 5;
    layout.spacing = 5;
    layout.fill = true;
    widget.setLayout(layout);
  }

  public void close() {
    shell.close();
    dispose();
  }

  public void dispose() {
    if (!backgroundColor.isDisposed()) {
      backgroundColor.dispose();
    }
    if (!shell.isDisposed()) {
      shell.dispose();
    }
  }

  private Monitor selectMonitor(Display display) {
    Shell activeShell = display.getActiveShell();
    if (activeShell != null && activeShell.getMonitor() != null) {
      return activeShell.getMonitor();
    }

    // Fall-back
    Monitor[] monitors = display.getMonitors();
    if (monitors.length > 0) {
      return monitors[0];
    }

    // Worst case. What? Can this happen?
    return null;
  }

  /*
   * Positions the widget in the lower corner of the screen.
   *
   * Intentionally adjusted by two pixels in order to view the
   * entire widget.
   */
  private void setPosition(Shell shell) {
    Monitor monitor = selectMonitor(display);
    if (monitor != null) {
      Rectangle clientArea = monitor.getClientArea();
      Point size = shell.getSize();
      int x = (clientArea.x + clientArea.width - size.x) - 2;
      int y = (clientArea.y + clientArea.height - size.y) - 2;
      shell.setLocation(x, y);
    }
  }
}
