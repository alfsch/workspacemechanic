/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.collect.Multimap;
import com.google.eclipse.mechanic.core.keybinding.KeyBindings.Qualifier;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Class that formats the keybindings by sweat and tears.
 * 
 * <p>The reason a JSON formatter is not used is so that the output, which is
 * meant to be read by a person, is carefully tinkered with to be readable.
 *
 * @author zorzella@google.com
 */
class KeyBindingsManualFormatter {

  private static final boolean DEBUG = true;

  private final MechanicLog log;
  // TODO: probably get rid of these two by leveraging userBindingsMap and systemBindingsMap
//  private final List<Binding> userBindings;
//  private final List<Binding> systemBindings;
  private final Multimap<Qualifier, Binding> userBindingsMap;
  private final Multimap<Qualifier, Binding> systemBindingsMap;

  private final KeyBindings keyBindings;

  /**
   * Creates a new instance from a defined set of bindings.
   */
  public KeyBindingsManualFormatter(KeyBindings keyBindings) {
    this.keyBindings = keyBindings;
    this.log = keyBindings.log;
//    this.userBindings = ub;
//    this.systemBindings = Collections.unmodifiableList(sb);
    this.userBindingsMap = keyBindings.userBindingsMap;
    this.systemBindingsMap = keyBindings.systemBindingsMap;

    printBindings();
  }

  private enum BindingType {
    USER,
    SYSTEM,
    ;
  }

  void printBindings() {
    if (!DEBUG) {
      return;
    }
    
    System.out.println("SYSTEM");
    printBindings(BindingType.SYSTEM, systemBindingsMap);
    System.out.println("USER");
    printBindings(BindingType.USER, userBindingsMap);
  }

  
  private void printBindings(BindingType bindingType, Multimap<Qualifier,Binding> bindings) {
    String output = getBindingsPrintout(bindingType, bindings);
//    System.out.println(output);
    try {
      File tempFile = File.createTempFile("CURRENT-" + bindingType + "-", ".kbd");
      PrintStream stream = new PrintStream(new FileOutputStream(tempFile));
      stream.print(output);
      System.out.println("Successfully wrote to: " + tempFile.getName());
      //      deserialize(output);
      //      System.out.println("Successfully deserialized.");
    } catch (Exception e) {
      System.out.println("Error");
      log.logError(e);
    }
  }
  
  private static String i(int indentNumber) {
    StringBuilder result = new StringBuilder();
    for (int i=0; i<indentNumber; i++) {
      result.append("  ");
    }
    return result.toString();
  }
  
  String getBindingsPrintout(BindingType bindingType, Multimap<Qualifier,Binding> bindings) {
    StringBuilder output = new StringBuilder()
        .append("{\n")
        .append(i(1)).append(quote(KeyBindingsParser.METADATA_JSON_KEY)).append(" : {\n")
        .append(i(2)).append(kvcn(KeyBindingsParser.SHORT_DESCRIPTION_JSON_KEY, "Put a short description here"))
        .append(i(2)).append(kvcn(KeyBindingsParser.DESCRIPTION_JSON_KEY, "Put a long description here"))
        .append(i(2)).append(kvn(KeyBindingsParser.TYPE_JSON_KEY, "LASTMOD"))
        .append(i(1)).append("},\n")
        .append(i(1)).append(quote(KeyBindingsParser.CHANGE_SETS_JSON_KEY)).append(" : [\n");
    for (Qualifier q : bindings.keySet()) {
      output
          .append(i(1)).append("{\n")
          .append(i(2)).append(kvcn(KeyBindingsParser.SCHEME_JSON_KEY, q.scheme));
      
      if (q.platform != null) {
        output
            .append(i(2)).append(kvcn(KeyBindingsParser.PLATFORM_JSON_KEY, q.platform));
      }
      output
          .append(i(2)).append(kvcn(KeyBindingsParser.CONTEXT_JSON_KEY, q.context))
          .append(i(2)).append(kvcn(KeyBindingsParser.ACTION_JSON_KEY, KeyBindingsParser.ADD_JSON_KEY))
          .append(i(2)).append(quote(KeyBindingsParser.BINDINGS_JSON_KEY)).append(" : [\n");
      for (Binding b : bindings.get(q)) {
        if (b.getParameterizedCommand() == null) {
          // TODO: Phase II support removing bindings
          continue;
        }
        output.append(serializeToZ(b));
        
        // TODO: GSON is not happy about trailing commas. Either make
        // GSON happy, or suppress trailing whitespaces
      }
      output.append(i(3)).append("]\n")
          .append(i(2)).append("}\n")
          .append(i(1)).append("]\n");
    }
    output.append("}");
    return output.toString();
  }

  private static CharSequence serializeToZ(Binding b) {
      boolean remove = false;
      if (b.getParameterizedCommand() == null) {
        remove = true;
        // TODO implement remove
        throw new UnsupportedOperationException("Implement removing.");
      }
      String platform = b.getPlatform();

      StringBuilder toPrint = new StringBuilder()
          .append(i(3))
              .append("{")
              .append(kvcs(KeyBindingsParser.KEYS_JSON_KEY, b.getTriggerSequence().format()))
//              .append(kd(KeyBindingsParser.COMMAND_JSON_KEY, formatCommand(b)))
              .append(formatCommand(b))
              .append("},\n");
      return toPrint;
  }

  private static String formatCommand(Binding b) {
    ParameterizedCommand parameterizedCommand = b.getParameterizedCommand();
    if (parameterizedCommand == null) {
      return "";
    }
    
    Command command = parameterizedCommand.getCommand();
    StringBuilder result = new StringBuilder()   //"{")
        .append(kvcs(KeyBindingsParser.COMMAND_JSON_KEY, command.getId()));
    
    @SuppressWarnings("unchecked")
    Map<String,String> parameterMap = parameterizedCommand.getParameterMap();
    if (parameterMap.size() > 0) {
      result.append(kd(KeyBindingsParser.COMMAND_PARAMETERS_JSON_KEY, formatParameters(parameterMap)));
    }
//    result.append("}");
    return result.toString();
  }

  private static String formatParameters(Map<String, String> parameterMap) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (String key : parameterMap.keySet()) {
        sb.append(kvc(urlEncoded(key), urlEncoded(parameterMap.get(key))));
    }
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
  
  private static String kvc(String key, String value) {
    return quote(key) + " : " + quote(value) + ",";
  }

  private static String kvcn(String key, String value) {
    return quote(key) + " : " + quote(value) + ",\n";
  }

  private static String quote(String string) {
    return "'" + string + "'";
  }
}