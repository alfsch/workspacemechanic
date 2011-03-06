/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.IValueVariableInitializer;

/**
 * Variable initializer that sets the initial value to the home directory of the user hosting
 * this environment. 
 *
 * @author konigsberg@google.com (Robert Konigsberg)
 */
public class UserHomeVariableInitializer implements IValueVariableInitializer {
  public void initialize(IValueVariable variable) {
    variable.setValue(System.getProperty("user.home"));
  }
}
