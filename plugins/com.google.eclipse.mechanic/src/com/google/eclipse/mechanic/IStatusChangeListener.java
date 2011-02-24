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
 * Listener for changes to the TaskService status. Register one of these
 * with the TaskService to get notifications of status change.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface IStatusChangeListener {

  void statusChanged(StatusChangedEvent event);

}
