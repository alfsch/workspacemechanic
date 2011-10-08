/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.base.Preconditions;

/**
 * Misc util methods as previously provided by collections and base.
 *
 * @author smckay@google.com (Steve McKay)
 * 
 * @deprecated Now that we depend on guava, all these methods (which were
 * originally copied from it anyway) have better counterparts.
 */
@Deprecated
public final class Util {

  private static final int BUF_SIZE = 4096;

  // no instantiation
  private Util() {}

  /**
   * Reads everything from src and writes it all to dest.
   * Does not close or flush either stream.
   * @return The number of bytes copied
   */
  public static long copyStream(InputStream src, OutputStream dest)
      throws IOException {
    long total = 0;
    byte[] buf = new byte[BUF_SIZE];
    while (true) {
      int r = src.read(buf);
      if (r == -1) {
        break;
      }
      dest.write(buf, 0, r);
      total += r;
    }

    return total;
  }

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
