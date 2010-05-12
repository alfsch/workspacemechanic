/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

/**
 * All possible Mechanic states.
 *
 * @author smckay@google.com (Steve McKay)
 */
public enum MechanicStatus {

  /* there are no failing tasks */
  PASSED,

  /* there are failing tasks */
  FAILED,

  /* mechanic service is updating its set of tasks */
  UPDATING,

  /* mechanic service is not running */
  STOPPED

}
