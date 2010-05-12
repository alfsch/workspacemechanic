/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.keybinding;

import com.google.eclipse.mechanic.internal.TaskType;
import com.google.eclipse.mechanic.internal.Util;

import org.mortbay.util.ajax.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Reads a JSON file containing the mechanic diagnostics, and returns that in
 * the internal format.
 * 
 * <p>The changes will be taken from a file with a format like below:
 * 
 * <pre>
 *

{
  "metadata" : {
    "shortDescription" : "",
    "description" : "",
    "type" : "LASTMOD",
  }, 
  "changeSets" : [
    {
      "scheme" : "org.eclipse.ui.emacsAcceleratorConfiguration",
      "platform" : "",
      "context" : "org.eclipse.ui.contexts.window",
      "bindings" : [
        {"action" : "rem", "keys" : "Shift+Alt+Q X"},
        {"action" : "add", "keys" : "Shift+Alt+Q T", "command" : {"id" : "a.b.c.d.e"}},
      ]
    },
    {
      "scheme" : "org.eclipse.ui.defaultAcceleratorConfiguration",
      "platform" : "",
      "context" : "org.eclipse.ui.contexts.window",
      "bindings" : [
        {"action" : "rem", "keys" : "Shift+Alt+Q X"},
        {"action" : "add", "keys" : "Shift+Alt+Q T", "command" : {"id" : "a.b.c.d.e"}},
      ]
    },
  ]
} 

 * </pre>
 * 
 * This file starts out with metadata (for mechanic's own use), then it has a 
 * "changes" section, that being a list of what we internally call
 * {@link KeyBindingChangeSet}, each specific to a single scheme/platform/context,
 * and containing a list of binding changes (internally split into two lists of
 * {@link KeyBindingSpec}, one for bindings to add, another for bindings to
 * remove). 
 * 
 * @author zorzella@google.com
 */
public class KeyBindingsParser {

  //TODO: read from JSON file
  //TODO: take in a "stream" as parameter?
  public static List<KeyBindingChangeSet> buildKeyChangeSets() {
    ArrayList<KeyBindingChangeSet> result = Util.newArrayList();
    
    List<KeyBindingSpec> toAdd = Util.newArrayList();
    toAdd.add(new KeyBindingSpec(
        "org.eclipse.ui.edit.text.cut.line.to.end", 
        "Shift+Ctrl+Delete"));
    toAdd.add(new KeyBindingSpec(
        "org.eclipse.ui.navigate.showIn",
        "Shift+Alt+Q A B C")
          .withParam("org.eclipse.ui.navigate.showIn.targetId", "org.eclipse.pde.runtime.RegistryBrowser"));
  
    List<KeyBindingSpec> toRemove = Util.newArrayList();
    toRemove.add(new KeyBindingSpec(
        "org.eclipse.ui.edit.text.cut.line.to.end", 
        "Shift+Ctrl+Alt+Delete"));
    toRemove.add(new KeyBindingSpec(
        "org.eclipse.jdt.ui.navigate.open.type",
        "Shift+Ctrl+T"));
    toRemove.add(new KeyBindingSpec(
        "org.eclipse.ui.views.showView",
        "Shift+Alt+Q X")
          .withParam("org.eclipse.ui.views.showView.viewId",
              //TODO(zorzella): wrong!
              "org.eclipse.ui.views.ProblemView"));
    
    result.add(new KeyBindingChangeSet(
        "org.eclipse.ui.defaultAcceleratorConfiguration", 
        "", 
        "org.eclipse.ui.contexts.window", 
        toAdd, 
        toRemove));
    
    return result;
  }

  private static final JSON JSON_LIB = new JSON();
  
  private static Object deSerializeFromJson(CharSequence json) {
    return JSON_LIB.fromJSON(json.toString());
  }
  
  public static KeyBindingsTask deSerialize(CharSequence json) {
    List<KeyBindingChangeSet> changeSets = Util.newArrayList();
    
    Map<String,Object> map = castToMap(deSerializeFromJson(json));
    
    Map<String, Object> metadataJson = castToMap(map.get("metadata"));
    
    KeyBindingsTask.MetaData metadata = 
      new KeyBindingsTask.MetaData(
          (String)metadataJson.get("shortDescription"), 
          (String)metadataJson.get("description"), 
          type(metadataJson.get("type")));
    
    
    Map<String, Object>[] changeSetsJson = castToMapArray(map.get("changeSets"));
    for (Map<String, Object> changeSetJson : changeSetsJson) {
      Collection<KeyBindingSpec> toRemove = Util.newArrayList();
      Collection<KeyBindingSpec> toAdd = Util.newArrayList();
      Map<String, Object>[] bindings = castToMapArray(changeSetJson.get("bindings"));
      for (Map<String,Object> bindingMap : bindings) {
        Action action = actionForLabel(bindingMap.get("action"));
        switch(action) {
          case ADD:
            Map<String, Object> command = castToMap(bindingMap.get("command"));

            KeyBindingSpec bindingSpec = new KeyBindingSpec(
                command.get("id").toString(), 
                (String)bindingMap.get("keys"));
    
            Map<String, Object> params = castToMap(command.get("parameters"));
            if (params != null) {
              for (String param : params.keySet()) {
                bindingSpec.withParam(param, (String)params.get(param));
              }
            }
            toAdd.add(bindingSpec);
            break;
          case REMOVE:
            toRemove.add(new KeyBindingSpec(null, (String)bindingMap.get("keys")));
            break;
          default: 
            throw new UnsupportedOperationException();
        }

      }
      changeSets.add(
          new KeyBindingChangeSet(
              (String)changeSetJson.get("scheme"), 
              (String)changeSetJson.get("platform"), 
              (String)changeSetJson.get("context"), 
              toAdd,
              toRemove));
    }
    return new KeyBindingsTask(changeSets, metadata);
  }
  
  private static TaskType type(Object object) {
    for (TaskType taskType : TaskType.values()) {
      if (taskType.toString().equals(object)) {
        return taskType;
      }
    }
    throw new IllegalArgumentException(object + "");
  }

  private enum Action {
    ADD("add"),
    REMOVE("rem"),
    ;
    
    private final String label;

    Action(String label) {
      this.label = label;
    }
    
    static Action forLabel(String label) {
      for (Action action : values()) {
        if (action.label.equals(label)) {
          return action;
        }
      }
      throw new IllegalArgumentException(label);
    }
  }
  
  private static Action actionForLabel(Object label) {
    return Action.forLabel((String)label);
  }

  @SuppressWarnings("unchecked")
  private static Map<String,Object> castToMap(Object map) {
    if (map == null) {
      return null;
    }
    return (Map<String,Object>)map;
  }
  
  @SuppressWarnings("unchecked")
  private static Map<String, Object>[] castToMapArray(Object array) {
    if (array == null) {
      return null;
    }
    Object[] ary = (Object[]) array;
    Map[] mapArray = new Map[ary.length];
    System.arraycopy(array, 0, mapArray, 0, ary.length);
    return mapArray;
  }
}
