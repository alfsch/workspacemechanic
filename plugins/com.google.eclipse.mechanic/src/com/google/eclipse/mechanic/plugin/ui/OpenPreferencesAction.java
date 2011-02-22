/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Action that opens the a preferences dialog given a preferences page id.
 * 
 * <p>Do something like this:
 * <pre>
 * // create a new instance of the action
 * private static final Action openPrefsAction
 *    = new OpenPreferencesAction(
 *        OpenPreferencesAction.getPreferencesPageId(MyPlugin.PLUGIN_ID));
 * </pre>
 * 
 * <p>Then add the action to a context menu using:
 * {@code IContributionManager#add(IAction)}. FYI, {@code IMenuManager} extends
 * {@code IContributionManager}.
 * 
 * @author smckay@google.com (Steve McKay)
 */
public final class OpenPreferencesAction extends Action {

  // the default text to be displayed for the menu item
  private static final String DEFAULT_TEXT = "Preferences...";

  // id of the preferences page
  private final String pageId;

  /**
   * Creates a new instance using the supplied page id and
   * label text.
   */
  public OpenPreferencesAction(String pageId, String text) {
    this.pageId = pageId;
    setText(text);
  }

  /**
   * Creates a new instance using the supplied page id, supplying
   * default "Preferences..." label text.
   */
  public OpenPreferencesAction(String pageId) {
    this(pageId, DEFAULT_TEXT);
  }

  @Override
  public void run() {
    openPreferences();
  }

  /**
   * Opens the preferences dialog.
   */
  private void openPreferences() {
    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
        shell, pageId, null, null);
    dialog.open();

    // not sure if I have to do any cleanup. If so, the we probably want to:
    // dialog.blockOnOpen(true), then do the cleanup
  }
}
