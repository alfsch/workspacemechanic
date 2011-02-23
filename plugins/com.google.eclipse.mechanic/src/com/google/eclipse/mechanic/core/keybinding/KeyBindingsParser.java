/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.eclipse.mechanic.core.keybinding.KeyBindingChangeSet.Bindings;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsModel.MetaData;
import com.google.eclipse.mechanic.internal.TaskType;
import com.google.eclipse.mechanic.internal.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

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
    "type" : "LASTMOD"
  },
  "changeSets" : [
    {
      "scheme" : "org.eclipse.ui.emacsAcceleratorConfiguration",
      "platform" : "",
      "context" : "org.eclipse.ui.contexts.window",
      "bindings" : [
        {"action" : "rem", "keys" : "Shift+Alt+Q X"},
        {"action" : "add", "keys" : "Shift+Alt+Q T", "command" : {"id" : "a.b.c.d.e"}}
      ]
    },
    {
      "scheme" : "org.eclipse.ui.defaultAcceleratorConfiguration",
      "platform" : "",
      "context" : "org.eclipse.ui.contexts.window",
      "bindings" : [
        {"action" : "rem", "keys" : "Shift+Alt+Q X"},
        {"action" : "add", "keys" : "Shift+Alt+Q T", "command" : {"id" : "a.b.c.d.e"}}
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

  //TODO(konigsberg, zorzella): read from JSON file
  //TODO(konigsberg, zorzella): take in a "stream" as parameter?
  public static List<KeyBindingChangeSet> buildKeyChangeSets() {
    List<KeyBindingChangeSet> result = Util.newArrayList();

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

  private static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .registerTypeAdapter(MetaData.class, new MetaDataAdapter())
      .registerTypeAdapter(KeyBindingsModel.class, new KeyBindingsModelAdapter())
      .registerTypeAdapter(KeyBindingChangeSet.class, new KeyBindingChangeSetAdapter())
      .registerTypeAdapter(KeyBindingChangeSet.Bindings.class, new BindingsAdapter())
      .create();

  public static String serialize(KeyBindingsModel model) {
    return GSON.toJson(model);
  }

  public static KeyBindingsModel deSerialize(Reader reader) {
    return GSON.fromJson(reader, KeyBindingsModel.class);
  }

  @Deprecated
  public static KeyBindingsModel deSerialize(CharSequence json) {
    // TODO(konigsberg): Replace with the other deserialize method
    return GSON.fromJson(json.toString(), KeyBindingsModel.class);
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

  private static final class Types {
    static final Type changeSetsList =
        new TypeToken<List<KeyBindingChangeSet>>(){}.getType();
    static final Type metaData = new TypeToken<MetaData>(){}.getType();
    static final Type string = new TypeToken<String>(){}.getType();
    static final Type bindings = new TypeToken<Bindings>(){}.getType();
    static final Type taskType = new TypeToken<TaskType>(){}.getType();
  }

  public static class MetaDataAdapter
      implements JsonDeserializer<MetaData> {

    public MetaData deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      return new MetaData(
          (String) context.deserialize(jo.get("shortDescription"), Types.string),
          (String) context.deserialize(jo.get("description"), Types.string),
          (TaskType) context.deserialize(jo.get("type"), Types.taskType));
    }
  }

  public static class KeyBindingsModelAdapter
  implements JsonDeserializer<KeyBindingsModel> {

    public KeyBindingsModel deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      @SuppressWarnings("unchecked") // Typecast with generic from Object is required.
      List<KeyBindingChangeSet> changeSet = (List<KeyBindingChangeSet>)
          context.deserialize(jo.get("changeSets"), Types.changeSetsList);

      return new KeyBindingsModel(
          changeSet,
          (MetaData) context.deserialize(jo.get("metadata"), Types.metaData));
    }
  }

  public static class KeyBindingChangeSetAdapter
      implements JsonDeserializer<KeyBindingChangeSet> {

    public KeyBindingChangeSet deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      return new KeyBindingChangeSet(
          (String) context.deserialize(jo.get("scheme"), Types.string),
          (String) context.deserialize(jo.get("platform"), Types.string),
          (String) context.deserialize(jo.get("context"), Types.string),
          (Bindings) context.deserialize(jo.get("bindings"), Types.bindings));
    }
  }

  public static class BindingsAdapter
      implements JsonSerializer<KeyBindingChangeSet.Bindings>,
      JsonDeserializer<KeyBindingChangeSet.Bindings>{

    public JsonElement serialize(Bindings bindings, Type typeOfSrc,
        JsonSerializationContext context) {
      JsonArray array = new JsonArray();

      for (KeyBindingSpec keyBindingSpec : bindings.toAdd()) {
        array.add(serialize("add", keyBindingSpec));
      }

      for (KeyBindingSpec keyBindingSpec : bindings.toRemove()) {
        array.add(serialize("rem", keyBindingSpec));
      }
      return array;
    }

    private JsonElement serialize(String action, KeyBindingSpec keyBindingSpec) {
      JsonObject jo = new JsonObject();
      jo.addProperty("action", action);
      jo.addProperty("keys", keyBindingSpec.getKeySequence());

      JsonObject cjo = new JsonObject();
      if (keyBindingSpec.getCid() != null) {
        cjo.addProperty("id", keyBindingSpec.getCid());
      }
      if (!keyBindingSpec.getParameters().isEmpty()) {
        JsonObject paramjo = new JsonObject();
        for (Map.Entry<String, String> entry : keyBindingSpec.getParameters().entrySet()) {
          paramjo.addProperty(entry.getKey(), entry.getValue());
        }
        cjo.add("parameters", paramjo);
      }
      if (!cjo.entrySet().isEmpty()) {
        jo.add("command", cjo);
      }
      return jo;
    }

    public Bindings deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      List<KeyBindingSpec> toAdd = Util.newArrayList();
      List<KeyBindingSpec> toRemove = Util.newArrayList();
      JsonArray ja = json.getAsJsonArray();
      for (JsonElement jsonElement : ja) {
        JsonObject jo = jsonElement.getAsJsonObject();

        String keySequence = jo.get("keys").getAsString();
        Action action = actionForLabel(jo.get("action").getAsString());

        switch(action) {
          case ADD:
            JsonObject command = jo.getAsJsonObject("command");

          KeyBindingSpec bindingSpec = new KeyBindingSpec(
              command.get("id").getAsString(),
              keySequence);

          JsonObject params = command.getAsJsonObject("parameters");
          if (params != null) {
            for (Entry<String, JsonElement> entry : params.entrySet()) {
              bindingSpec = bindingSpec.withParam(entry.getKey(), entry.getValue().getAsString());
            }
          }
          toAdd.add(bindingSpec);
          break;
        case REMOVE:
          toRemove.add(new KeyBindingSpec(null, keySequence));
          break;
        default:
          throw new UnsupportedOperationException();
        }
      }
      return new Bindings(toAdd, toRemove);
    }
  }
}
