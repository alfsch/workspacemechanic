/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.bindings.Binding;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.io.Closeables;
import com.google.eclipse.mechanic.internal.TaskType;

/**
 * Class that formats the keybindings by sweat and tears.
 * 
 * <p>The reason a JSON formatter is not used is so that the output, which is
 * meant to be read by a person, is carefully tinkered with to be readable.
 *
 * <p>The usage for this class is the following: 1. user creates a few
 * keybindings through the regular Eclipse UI; 2. user wants to create a task
 * based on these bindings; 3. user triggers this code (still TODO) which spits
 * out a file for him/her to bootstrap. This will be the canonical, documented
 * and simplest way to create a .kbd task file.
 *
 * @author zorzella@google.com
 */
class KeyBindingsManualFormatter {

  private final boolean debugDumpSystemBindings;
  private final Map<KbaChangeSetQualifier, KbaChangeSet> userBindingsMap;
  private final Map<KbaChangeSetQualifier, KbaChangeSet> systemBindingsMap;

  public KeyBindingsManualFormatter(
      final Map<KbaChangeSetQualifier, KbaChangeSet> userBindingsMap,
      final Map<KbaChangeSetQualifier, KbaChangeSet> systemBindingsMap) {
    this(System.getProperty("KEYBOARD_MECHANIC_DEBUG_DUMP_SYSTEM_BINDINGS", "false").equals("true"),
        userBindingsMap,
        systemBindingsMap);
  }
  
  public KeyBindingsManualFormatter(
      final boolean debugDumpSystemBindings,
      final Map<KbaChangeSetQualifier, KbaChangeSet> userBindingsMap,
      final Map<KbaChangeSetQualifier, KbaChangeSet> systemBindingsMap) {
    this.debugDumpSystemBindings = debugDumpSystemBindings;
    this.userBindingsMap = userBindingsMap;
    this.systemBindingsMap = systemBindingsMap;
  }

  enum BindingType {
    USER,
    SYSTEM,
    ;
    
    static BindingType from(Binding binding) {
      return from(binding.getType());
    }
    
    static BindingType from(int eclipseBindingType) {
      if (eclipseBindingType == Binding.SYSTEM) {
        return SYSTEM;
      } else if (eclipseBindingType == Binding.USER) {
        return USER;
      } else {
        throw new UnsupportedOperationException("Binding type: " + eclipseBindingType);
      }
    }
    
  }

  void dumpBindingsToFile(IPath outputLocation, String description, TaskType taskType)
      throws FileNotFoundException, IOException {
    if (debugDumpSystemBindings) {
      dumpBindingsToFile(BindingType.SYSTEM, systemBindingsMap, outputLocation,
          description, taskType);
    }
    dumpBindingsToFile(BindingType.USER, userBindingsMap, outputLocation,
        description, taskType);
  }

  
  private void dumpBindingsToFile(
      BindingType bindingType,
      Map<KbaChangeSetQualifier,
      KbaChangeSet> kbaChangeSet,
      IPath outputLocation,
      String description,
      TaskType taskType) throws FileNotFoundException, IOException {
    String output = getBindingsPrintout(bindingType, kbaChangeSet, description, taskType);
    File file = outputLocation.toFile();
    PrintStream stream = null;
    try {
      stream = new PrintStream(new FileOutputStream(file));
      stream.print(output);
    } finally {
      Closeables.closeQuietly(stream);
    }
  }
  
  static String getBindingsPrintout(BindingType bindingType, Map<KbaChangeSetQualifier,KbaChangeSet> bindings,
      String description, TaskType taskType) {
    Preconditions.checkNotNull(description);
    Preconditions.checkNotNull(taskType);
    StringBuilder output = new StringBuilder()
        .append("{\n")
        .append(i(1)).append(quote(KeyBindingsParser.METADATA_JSON_KEY)).append(" : {\n")
        .append(i(2)).append(kvcn(KeyBindingsParser.DESCRIPTION_JSON_KEY, description))
        .append(i(2)).append(kvn(KeyBindingsParser.TYPE_JSON_KEY, taskType.toString()))
        .append(i(1)).append("},\n")
        .append(i(1)).append(quote(KeyBindingsParser.CHANGE_SETS_JSON_KEY)).append(" : [\n");
    for (KbaChangeSetQualifier q : bindings.keySet()) {
      output
          .append(i(2)).append("{\n")
          .append(i(2)).append(kvcn(KeyBindingsParser.SCHEME_JSON_KEY, q.scheme));
      
      if (q.platform != null) {
        output
            .append(i(2)).append(kvcn(KeyBindingsParser.PLATFORM_JSON_KEY, q.platform));
      }
      output
          .append(i(2)).append(kvcn(KeyBindingsParser.CONTEXT_JSON_KEY, q.context))
          .append(i(2)).append(kvcn(KeyBindingsParser.ACTION_JSON_KEY, q.action))
          .append(i(2)).append(quote(KeyBindingsParser.BINDINGS_JSON_KEY)).append(" : [\n");
      for (KbaBinding b : bindings.get(q).getBindingList()) {
        output.append(formatKbaBinding(b));
        
        // TODO: GSON is not happy about trailing commas. Either make
        // GSON happy, or suppress trailing whitespaces
      }
      output.append(i(3)).append("]\n")
          .append(i(2)).append("},\n");
    }
    output.append(i(1)).append("]\n");
    output.append("}");
    return output.toString();
  }

  private static CharSequence formatKbaBinding(KbaBinding b) {
      @SuppressWarnings("unused") // Remove needs to be worked on.
      boolean remove = false;
      StringBuilder toPrint = new StringBuilder()
          .append(i(3))
              .append("{")
              .append(kvcs(KeyBindingsParser.KEYS_JSON_KEY, b.getKeySequence()))
              .append(formatCommand(b))
              .append("},\n");
      return toPrint;
  }

  private static String formatCommand(KbaBinding b) {
    StringBuilder result = new StringBuilder()
        .append(kv(KeyBindingsParser.COMMAND_JSON_KEY, b.getCid()));
    
    Map<String,String> parameterMap = b.getParameters();
    if (parameterMap.size() > 0) {
      result.append(", ")
          .append(kd(KeyBindingsParser.COMMAND_PARAMETERS_JSON_KEY, formatParameters(parameterMap)));
    }
    return result.toString();
  }

  private static String formatParameters(final Map<String, String> parameterMap) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    
    Function<String,String> function = new Function<String, String>() {
      public String apply(String key) {
        return kv(urlEncoded(key), urlEncoded(parameterMap.get(key)));
      }
    };
    Iterable<String> transformed = Iterables.transform(parameterMap.keySet(), function);
    sb.append(Joiner.on(", ").join(transformed));
    
    sb.append("}");
    return sb.toString();
  }

  private static String urlEncoded(String key) {
    try {
      return URLEncoder.encode(key, "US-ASCII");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  // Beging quick-and-dirty methods to help us format the output

  private static String i(int indentNumber) {
    StringBuilder result = new StringBuilder();
    for (int i=0; i<indentNumber; i++) {
      result.append("  ");
    }
    return result.toString();
  }
  
 private static String kd(String key, String data) {
    return quote(key) + " : " + data;
  }
  
  private static String kvn(String key, String value) {
    return quote(key) + " : " + quote(value) + "\n";
  }
  
  private static String kv(String key, String value) {
    return quote(key) + " : " + quote(value);
  }
  
  private static String kvcs(String key, String value) {
    return quote(key) + " : " + quote(value) + ", ";
  }
  
  private static String kvcn(String key, String value) {
    return quote(key) + " : " + quote(value) + ",\n";
  }

  private static String quote(String string) {
    return "'" + string + "'";
  }
}