/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic;

import java.util.Collection;

/**
 * Provides basic services like scanning for and execution of
 * {@link Task}s. The service also provides hooks for other components to
 * interact with the service, display service information and invoke the basic
 * services.
 */
public interface IMechanicService {

  /**
   * Causes the scannning service to start as soon as possible.
   */
  void start();

  /**
   * Causes the scanning service to stop scanning.
   */
  void stop();

  /**
   * Return true if the mechanic service is not running.
   */
  boolean isStopped();

  /**
   * Adds the supplied listener to status change events for the Mechanic (not the
   * Job). UI components can use this facility to get notifications announcing
   * changes in the mechanic service.
   *
   * <p>Callers of this method should note that Mechanic Service will
   * immediately send a notification to the supplied listener when this method
   * is called. Also, callers should remember to unsubscribe listeners when
   * they are disposed (or otherwise reach the end of their life).
   */
  void addTaskStatusChangeListener(IStatusChangeListener statusChangeListener);

  /**
   * Removes supplied listener from our set of listeners if it is contained
   * therein, else does nothing.
   *
   * @param statusChangeListener listener to receive status update notifications.
   */
  void removeTaskStatusChangeListener(IStatusChangeListener statusChangeListener);

  /**
   * Returns a {@link RepairManager} capable of fixing broken Tasks. The
   * returned {@link RepairManager} will use the supplied
   * {@link RepairDecisionProvider} to collect user input needed to determine
   * what actions to take for each failing Task.
   */
  RepairManager getRepairManager(RepairDecisionProvider rdp);

  /**
   * Returns an immutable set of all the currently known tasks, passing or not.
   */
  Collection<Task> getAllKnownTasks();

  /**
   * A temporary solution allowing the trim widget to display the number
   * of failing items to the user.
   */
  int getFailingItemCount();

}
