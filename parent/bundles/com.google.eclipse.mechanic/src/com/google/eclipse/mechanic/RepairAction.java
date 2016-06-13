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
 * A Runnable by any other name is still a Runnable. Implement a RepairAction
 * to bring the environment into compliance with an {@link Evaluator}'s notion
 * of correctness.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface RepairAction extends Runnable {

}
