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
 * Abstract Task for simple implementations of composite
 * (Task/Evaluator/RepairAction)s
 *
 * @author smckay@google.com (Steve McKay)
 */
public abstract class CompositeTask implements CompositeTaskInterface {

  public String getId() {
    return this.getClass().getName();
  }

  public Evaluator getEvaluator() {
    return this;
  }

  public RepairAction getRepairAction() {
    return this;
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  @Override
  public String toString() {
    return getId();
  }

  /**
   * Returns true if the supplied Object is an Task with the same
   * id.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof Task) {
      return getId().equals(((Task) obj).getId());
    }
    return false;
  }
}
