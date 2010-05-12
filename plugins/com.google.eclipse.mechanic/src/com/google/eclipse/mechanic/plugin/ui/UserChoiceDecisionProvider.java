/*******************************************************************************
 * Copyright (C) 2009, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.RepairDecisionProvider;

import org.eclipse.ui.IWorkbenchWindow;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Adapts MechanicDialog to the RepairDecisionProvider interface as needed by
 * {@link com.google.eclipse.mechanic.RepairManager}.
 *
 * @author smckay@google.com (Steve McKay)
 */
class UserChoiceDecisionProvider implements RepairDecisionProvider {

  private final IWorkbenchWindow window;

  private Map<Task, RepairDecisionProvider.Decision> decisions
      = Collections.emptyMap();

  public UserChoiceDecisionProvider(IWorkbenchWindow window) {
    this.window = window;
  }

  public ResponseStatus initialize(List<Task> failing) {

    ResponseStatus rs = ResponseStatus.CANCEL;

    MechanicDialog dialog = new MechanicDialog(window.getShell(), failing);

    dialog.open(); // blocks

    if (dialog.isOkay()) {
      rs = ResponseStatus.OK;
      decisions = dialog.getUserChoices();
    }

    // finally, return the status
    return rs;
  }

  public Map<Task, Decision> getDecisions() {
    return decisions;
  }
}