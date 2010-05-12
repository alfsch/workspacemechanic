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
 * Describes a single test for verifying that the environment is compliant
 * with the author's notion of correctness. A Evaluator should be written
 * in conjunction with a {@link RepairAction} capable of bringing the
 * environment into compliance.
 *
 * @author smckay@google.com (Steve McKay)
 */
public interface Evaluator {

  /**
   * @return true if the environment adheres to this test.
   */
  public boolean evaluate();

}
