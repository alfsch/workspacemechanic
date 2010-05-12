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
 * Event supplied to StatusChangeListeners.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class StatusChangedEvent {

  private final MechanicStatus status;

  public StatusChangedEvent(MechanicStatus status) {
    this.status = status;
  }

  public MechanicStatus getStatus() {
    return status;
  }
}
