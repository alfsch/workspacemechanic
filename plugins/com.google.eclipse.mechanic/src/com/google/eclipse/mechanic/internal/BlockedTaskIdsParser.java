/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

/**
 * Parser for the list of blocked task IDs stored as preferences.
 */
public class BlockedTaskIdsParser {
  // TODO(konigsberg): Make |parse| and |unparse| work with Sets instead of arrays.

  private static final Gson gson = new Gson();

  public final String[] parse(String text) {
    if (text == null) {
      return new String[0];
    }

    if (!text.startsWith("[")) {
      // I would use Splitter, but I won't use split.
      StringTokenizer st = new StringTokenizer(text, File.pathSeparator);
      List<String> list = Lists.newArrayList();
      while (st.hasMoreElements()) {
        list.add((String) st.nextElement());
      }
      return list.toArray(new String[0]);
    } else {
      return gson.fromJson(text, String[].class);
    }
  }

  public final String unparse(String... items) {
    return gson.toJson(items);
  }
}
