/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.common.base.Preconditions;

/**
 * Misc util methods as previously provided by collections and base.
 */
public final class Util {

  // no instantiation
  private Util() {}

  /**
   * Removes trailing and leading quotes, and unescapes any embedded quotes
   */
  public static String unquote(String value) {
    Preconditions.checkNotNull(value, "'value' cannot be null.");
    if (value.length() > 0 && value.charAt(0) == '"') {
      Preconditions.checkState(value.charAt(value.length() - 1) == '"', "Pref value with beginning quote but no end quote");
      value = value.substring(1, value.length() - 1);
      value = value.replace("\\\"", "\"");
    }
    return value;
  }
}
