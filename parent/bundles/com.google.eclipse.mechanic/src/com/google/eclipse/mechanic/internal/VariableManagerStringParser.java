/*******************************************************************************
 * Copyright (C) 2012, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

import com.google.common.base.Function;

/**
 * Perform variable substitution on a string. Used for translating the task directories,
 * which can contain variables.
 *
 * <p>Comes from the default {@link IStringVariableManager} from the {@link VariablesPlugin}.
 */
public class VariableManagerStringParser implements Function<String, String> {
  public static final VariableManagerStringParser INSTANCE = new VariableManagerStringParser();

  private VariableManagerStringParser() { }

  public String apply(String input) {
    try {
      IStringVariableManager stringManager =
          VariablesPlugin.getDefault().getStringVariableManager();
      return stringManager.performStringSubstitution(input);
    } catch (CoreException e) {
      return "";
    }
  }
}
