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
class KeyBindingsParser {

  static final String METADATA_JSON_KEY = "metadata";
  static final String ADD_JSON_KEY = "add";
  static final String REM_JSON_KEY = "rem";
  static final String BINDINGS_JSON_KEY = "bindings";
  static final String CONTEXT_JSON_KEY = "context";
  static final String PLATFORM_JSON_KEY = "platform";
  static final String SCHEME_JSON_KEY = "scheme";
  static final String TYPE_JSON_KEY = "type";
  static final String DESCRIPTION_JSON_KEY = "description";
  static final String SHORT_DESCRIPTION_JSON_KEY = "shortDescription";
  static final String CHANGE_SETS_JSON_KEY = "changeSets";
  static final String ACTION_JSON_KEY = "action";
  static final String COMMAND_JSON_KEY = "command";
  static final String COMMAND_PARAMETERS_JSON_KEY = "parameters";
  static final String COMMAND_ID_JSON_KEY = "id";
  static final String KEYS_JSON_KEY = "keys";
 
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

  private enum Action {
    ADD(ADD_JSON_KEY),
    REMOVE(REM_JSON_KEY),
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
          (String) context.deserialize(jo.get(SHORT_DESCRIPTION_JSON_KEY), Types.string),
          (String) context.deserialize(jo.get(DESCRIPTION_JSON_KEY), Types.string),
          (TaskType) context.deserialize(jo.get(TYPE_JSON_KEY), Types.taskType));
    }
  }

  public static class KeyBindingsModelAdapter
      implements JsonDeserializer<KeyBindingsModel> {


    public KeyBindingsModel deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      @SuppressWarnings("unchecked") // Typecast with generic from Object is required.
      List<KeyBindingChangeSet> changeSet = (List<KeyBindingChangeSet>)
          context.deserialize(jo.get(CHANGE_SETS_JSON_KEY), Types.changeSetsList);

      return new KeyBindingsModel(
          changeSet,
          (MetaData) context.deserialize(jo.get(METADATA_JSON_KEY), Types.metaData));
    }
  }

  public static class KeyBindingChangeSetAdapter
      implements JsonDeserializer<KeyBindingChangeSet> {


    public KeyBindingChangeSet deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      return new KeyBindingChangeSet(
          (String) context.deserialize(jo.get(SCHEME_JSON_KEY), Types.string),
          (String) context.deserialize(jo.get(PLATFORM_JSON_KEY), Types.string),
          (String) context.deserialize(jo.get(CONTEXT_JSON_KEY), Types.string),
          (Bindings) context.deserialize(jo.get(BINDINGS_JSON_KEY), Types.bindings));
    }
  }

  public static class BindingsAdapter
      implements JsonSerializer<KeyBindingChangeSet.Bindings>,
      JsonDeserializer<KeyBindingChangeSet.Bindings>{

    public JsonElement serialize(Bindings bindings, Type typeOfSrc,
        JsonSerializationContext context) {
      JsonArray array = new JsonArray();

      for (KeyBindingSpec keyBindingSpec : bindings.toAdd()) {
        array.add(serialize(ADD_JSON_KEY, keyBindingSpec));
      }

      for (KeyBindingSpec keyBindingSpec : bindings.toRemove()) {
        array.add(serialize(REM_JSON_KEY, keyBindingSpec));
      }
      return array;
    }

    private JsonElement serialize(String action, KeyBindingSpec keyBindingSpec) {
      JsonObject jo = new JsonObject();
      jo.addProperty(ACTION_JSON_KEY, action);
      jo.addProperty(KEYS_JSON_KEY, keyBindingSpec.getKeySequence());

      JsonObject cjo = new JsonObject();
      if (keyBindingSpec.getCid() != null) {
        cjo.addProperty(COMMAND_ID_JSON_KEY, keyBindingSpec.getCid());
      }
      if (!keyBindingSpec.getParameters().isEmpty()) {
        JsonObject paramjo = new JsonObject();
        for (Map.Entry<String, String> entry : keyBindingSpec.getParameters().entrySet()) {
          paramjo.addProperty(entry.getKey(), entry.getValue());
        }
        cjo.add(COMMAND_PARAMETERS_JSON_KEY, paramjo);
      }
      if (!cjo.entrySet().isEmpty()) {
        jo.add(COMMAND_JSON_KEY, cjo);
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

        String keySequence = jo.get(KEYS_JSON_KEY).getAsString();
        Action action = actionForLabel(jo.get(ACTION_JSON_KEY).getAsString());

        switch(action) {
          case ADD:
            JsonObject command = jo.getAsJsonObject(COMMAND_JSON_KEY);

          KeyBindingSpec bindingSpec = new KeyBindingSpec(
              command.get(COMMAND_ID_JSON_KEY).getAsString(),
              keySequence);

          JsonObject params = command.getAsJsonObject(COMMAND_PARAMETERS_JSON_KEY);
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