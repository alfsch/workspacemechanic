/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import com.google.eclipse.mechanic.RepairDecisionProvider.Decision;
import com.google.eclipse.mechanic.RepairDecisionProvider.ResponseStatus;
import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import java.util.List;
import java.util.Map;

/**
 * Provides a controller for the process of selecting tasks to be repaired,
 * then repairing them.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class RepairManager implements Runnable {

  private static final ILog LOG = MechanicPlugin.getDefault().getLog();

  private final MechanicService service;
  private final List<Task> failing;
  private final RepairDecisionProvider choiceProvider;

  /**
   * @param service
   * @param failing list of failing {@link Task}s. Doesn't modify the list.
   * @param choiceProvider a mechanism for determining how to react to failed tasks.
   */
  public RepairManager(MechanicService service, List<Task> failing,
      RepairDecisionProvider choiceProvider) {

    Util.checkArgument(!failing.isEmpty(), "'failing' cannot be empty");
    this.service = service;
    this.failing = failing;
    this.choiceProvider = choiceProvider;
  }

  /**
   * Collects list of tasks to be repaired, then repairs them.
   *
   * @throws IllegalStateException if the current status is UPDATING.
   */
  public void run() {

    ResponseStatus userStatus = choiceProvider.initialize(failing);

    // the user can cancel or approve the operation. When the operation is
    // canceled, we should not do anything.
    if (userStatus == ResponseStatus.OK) {

      // Repair and block individual actions as specified by the user.
      for (Map.Entry<Task, Decision> entry
          : choiceProvider.getDecisions().entrySet()) {

        switch (entry.getValue()) {
          case YES:
            repairItem(entry.getKey());
            break;
          case NO:
            // don't do anything this time
            break;
          case NEVER:
            MechanicPreferences.blockItem(entry.getKey());
            break;
          default:
            throw new RuntimeException("Unhandled Decision value.");
        }
      }

      // attempt to re-run the Mechanic immediately, to verify our work
      service.start();
    }
  }

  /**
   * Execute action associated with the supplied Task.
   */
  private void repairItem(Task item) {

    LOG.log(new Status(IStatus.INFO, MechanicPlugin.PLUGIN_ID, 
        String.format("Repairing item: %s", item)));
    try {
      item.getRepairAction().run();
    } catch (RuntimeException e) {
      LOG.log(new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID,
          String.format("RepairAction failed: %s", e.getMessage()), e));
      
      // TODO(smckay): would be nice to log an error to a server where we
      // can review them at our leisure.
    }
  }
}
