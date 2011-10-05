/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.ByteArrayOutputStream;
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
   * Reads everything from an InputStream into a byte array.
   * Does not close the input stream.
   *
   * @param in The input stream to consume. Must not be null.
   * @return A new byte array containing all the bytes from the stream.
   */
  public static byte[] readAll(InputStream in) throws IOException {
    return readToEOF(in);
  }

  /**
   * Reads everything from src and returns it as an array of bytes.
   * @return all of the data read from 'src'. Will not be null.
   */
  public static byte[] readToEOF(InputStream src) throws IOException {
    ByteArrayOutputStream content = new ByteArrayOutputStream(BUF_SIZE);
    copyStream(src, content);
    return content.toByteArray();
  }

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

  /**
   * Given an InputStream, return its contents as a byte array.
   */
  public static byte[] getBytes(InputStream is) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    int count;
    byte[] data = new byte[8192];
    while ((count = is.read(data, 0, data.length)) != -1) {
      os.write(data, 0, count);
    }
    os.flush();
    return os.toByteArray();
  }
}
