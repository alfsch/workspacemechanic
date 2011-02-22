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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Misc util methods as previously provided by collections and base.
 *
 * @author smckay@google.com (Steve McKay)
 */
public final class Util {

  private static final int BUF_SIZE = 4096;

  // no instantiation
  private Util() {}

  /**
   * Splits a string defined by a delimiter. If source is empty or if no instances of delimiter
   * are found, the original string is returned.
   *
   * This implementation is preferred in place of String.split because of some of String.split's
   * strange behavior. See http://konigsberg.blogspot.com/2009/11/java-puzzler-splitting-hairs.html.
   */
  public static String[] split(String source, String delimiter) {
    List<String> list = newArrayList();
    int fromIndex = 0;
    while (fromIndex < source.length()) {
      int index = source.indexOf(delimiter, fromIndex);
      if (index == -1) {
        index = source.length();
      }
      // Creating a new String because the substring method retains a reference to the
      // original string, making it hard to dispose.
      String substring = new String(source.substring(fromIndex, index));
      list.add(substring);
      fromIndex = index + delimiter.length();
    }

    return list.toArray(new String[0]);
  }

  public static String join(String sep, Object[] elems) {
    StringBuilder b = new StringBuilder();
    for (Object elem : elems) {
      if (b.length() > 0) {
        b.append(sep);
      }
      b.append(elem);
    }
    return b.toString();
  }

  /**
   * Ensures that {@code expression} is not {@code null}.
   *
   * @param expression any expression involving an argument to the current
   *     method.
   * @param message a message object which will be converted using
   *     {@link Object#toString} and included in the exception message if the
   *     check fails
   * @returns {@code expression}, unmodified, unless it is null.
   * @throws NullPointerException if {@code expression} is {@code null}
   */
  public static <T> T checkNotNull(T expression, String message) {
    if (expression == null) {
      throw new NullPointerException(message);
    }
    return expression;
  }

  /**
   * Ensures that {@code expression} is not {@code null}.
   *
   * @param expression any expression involving an argument to the current
   *     method.
   * @returns {@code expression}, unmodified, unless it is null.
   * @throws NullPointerException if {@code expression} is {@code null}
   */
  public static <T> T checkNotNull(T expression) {
    if (expression == null) {
      throw new NullPointerException();
    }
    return expression;
  }
  
  /**
   * Ensures that {@code expression} is {@code true}.
   *
   * @param expression any boolean expression involving an argument to the
   *     current method
   * @param message a message object which will be converted using
   *     {@link Object#toString} and included in the exception message if the
   *     check fails
   * @throws IllegalArgumentException if {@code expression} is {@code false}
   */
  public static void checkArgument(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Ensures that {@code expression} is {@code true}.
   *
   * @param expression any boolean expression involving a state
   * @param message a message object which will be converted using
   *     {@link Object#toString} and included in the exception message if the
   *     check fails
   * @throws IllegalStateException if {@code expression} is {@code false}
   */
  public static void checkState(boolean expression, String message) {
    if (!expression) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Creates an empty {@code ArrayList} instance.
   */
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<E>();
  }

  /**
   * Creates a {@code HashMap} instance.
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<K, V>();
  }

  /**
   * Creates an empty HashSet instance.
   */
  public static <E> HashSet<E> newHashSet() {
    return new HashSet<E>();
  }

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

    checkNotNull(value, "'value' cannot be null.");
    if (value.length() > 0 && value.charAt(0) == '"') {
      checkState(value.charAt(value.length() - 1) == '"',
          "Pref value with beginning quote but no end quote");
      value = value.substring(1, value.length() - 1);
      value = value.replace("\\\"", "\"");
    }
    return value;
  }

  public static int hashCode(Object... objects) {
    return Arrays.hashCode(objects);
  }

  /**
   * Null-sensitive version of equals.
   */
  public static boolean equals(Object one, Object two) {
    return ((one == null && two == null)) || (one != null && one.equals(two));
  }
}
