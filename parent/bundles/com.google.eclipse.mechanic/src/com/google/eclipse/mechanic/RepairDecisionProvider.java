/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import java.util.List;
import java.util.Map;

/**
 * Implement this interface to provide {@link Decision}s to the
 * {@link MechanicService} specifying which failing {@link Task}s should
 * be repaired.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface RepairDecisionProvider {

  /**
   * Represents a user's decision to proceed or abort.
   */
  public enum ResponseStatus {
    OK,
    CANCEL
  }

  /**
   * Represents the possible actions a user can select for an Task.
   */
  public enum Decision {

    YES,
    NO,
    NEVER;

    public static Decision valueOf(int ord) {
      return Decision.values()[ord];
    }
  }

  /**
   * Provides implementations an opportunity to collect information
   * from the user. Will be called before getDecisions() is called.
   * 
   * Returns the response status for this repair action. If the status is
   * {@code ResponseStatus.OK}, then the service should proceed to evaluate
   * individual repair decisions, else, the service should abort the operation.
   */
  public ResponseStatus initialize(List<Task> failing);

  /**
   * Returns map of {@link Task} to the user's choice for that Task.
   */
  public Map<Task, Decision> getDecisions();

}
