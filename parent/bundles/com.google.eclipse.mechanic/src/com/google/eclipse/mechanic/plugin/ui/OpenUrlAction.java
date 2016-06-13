/*******************************************************************************
 * Copyright (C) 2008, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Action that opens a URL in a browser.
 * 
 * <p>Do something like this:
 * <pre>
 * private static final Action urlAction = new OpenUrlAction(
 *     "http://dohickey.com/", "Doohickey");
 * </pre>
 * 
 * <p>Then add the action to a context menu using:
 * {@code IContributionManager#add(IAction)}. FYI, {@code IMenuManager} extends
 * {@code IContributionManager}.
 * 
 * @author smckay@google.com (Steve McKay)
 */
public final class OpenUrlAction extends Action {

  // URL to open when action is activated.
  private final URL url;

  /**
   * Creates a new instance using the supplied url and
   * label text.
   */
  public OpenUrlAction(URL url, String text) {
    this.url = url;
    setText(text);
  }

  /**
   * Creates a new instance using the supplied string url and
   * label text.
   * @throws MalformedURLException when the URL text is invalid.
   *
   * @throws IllegalArgumentException when the URL is invalid.
   */
  public OpenUrlAction(String url, String text) throws MalformedURLException {
    this.url = new URL(url);
    setText(text);
  }

  @Override
  public void run() {
    IWorkbenchBrowserSupport support
        = PlatformUI.getWorkbench().getBrowserSupport();
    try {
      support.getExternalBrowser().openURL(url);
    } catch (PartInitException e) {
      throw new IllegalStateException(
          "Eeep! Coudn't initialize part.", e);
    }
  }
}
