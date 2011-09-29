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

import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.KbaBindingList;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsAudit.KbaMetaData;
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
    "description" : "",
    "type" : "LASTMOD"
  },
  "changeSets" : [
    {
      "scheme" : "org.eclipse.ui.emacsAcceleratorConfiguration",
      "platform" : "",
      "context" : "org.eclipse.ui.contexts.window",
      "action" : "add",
      "bindings" : [
        {"keys" : "Shift+Alt+Q T", "cmd" : "a.b.c.d.e"}
      ]
    },
    {
      "scheme" : "org.eclipse.ui.defaultAcceleratorConfiguration",
      "platform" : "",
      "context" : "org.eclipse.ui.contexts.window",
      "action" : "add",
      "bindings" : [
        {'keys' : 'Shift+Alt+Q I', 'cmd' : 'org.eclipse.ui.views.showView', 'params' : {'org.eclipse.ui.views.showView.viewId' : 'org.eclipse.jdt.debug.ui.DisplayView' }},
      ]
    },
  ]
}

 * </pre>
 *
 * This file starts out with metadata (for mechanic's own use), then it has a
 * "changeSets" section, that being a list of what we internally call
 * {@link KbaChangeSet}, each specific to a single scheme/platform/context/action,
 * and containing a list of binding changes.
 *
 * @author zorzella@google.com
 */
class KeyBindingsParser {

  static final String METADATA_JSON_KEY = "metadata";
  static final String ADD_JSON_KEY = "add";
  static final String REM_JSON_KEY = "remove";
  static final String BINDINGS_JSON_KEY = "bindings";
  static final String CONTEXT_JSON_KEY = "context";
  static final String PLATFORM_JSON_KEY = "platform";
  static final String SCHEME_JSON_KEY = "scheme";
  static final String TYPE_JSON_KEY = "type";
  static final String DESCRIPTION_JSON_KEY = "description";
  static final String CHANGE_SETS_JSON_KEY = "changeSets";
  static final String ACTION_JSON_KEY = "action";
  static final String COMMAND_JSON_KEY = "cmd";
  static final String COMMAND_PARAMETERS_JSON_KEY = "params";
  static final String KEYS_JSON_KEY = "keys";
 
  private static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .registerTypeAdapter(KbaMetaData.class, new KbaMetaDataAdapter())
      .registerTypeAdapter(KeyBindingsAudit.class, new KeyBindingsAuditAdapter())
      .registerTypeAdapter(KbaChangeSet.class, new KbaChangeSetAdapter())
      .registerTypeAdapter(KbaChangeSet.KbaBindingList.class, new KbaBindingListAdapter())
      .create();

  public static String serialize(KeyBindingsAudit model) {
    return GSON.toJson(model);
  }

  public static KeyBindingsAudit deSerialize(Reader reader) {
    return GSON.fromJson(reader, KeyBindingsAudit.class);
  }

  private static final class TypeTokens {
    private static final Type KBA_CHANGE_SET_LIST_TYPE_TOKEN =
        new TypeToken<List<KbaChangeSet>>(){}.getType();
    private static final Type KBA_META_DATA_TYPE_TOKEN = new TypeToken<KbaMetaData>(){}.getType();
    private static final Type STRING_TYPE_TOKEN = new TypeToken<String>(){}.getType();
    private static final Type KBA_BINDING_LIST_TYPE_TOKEN = new TypeToken<KbaBindingList>(){}.getType();
    private static final Type TASK_TYPE_TYPE_TOKEN = new TypeToken<TaskType>(){}.getType();
  }

  private static class KbaMetaDataAdapter
      implements JsonDeserializer<KbaMetaData> {

    public KbaMetaData deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      return new KbaMetaData(
          (String) context.deserialize(jo.get(DESCRIPTION_JSON_KEY), TypeTokens.STRING_TYPE_TOKEN),
          (TaskType) context.deserialize(jo.get(TYPE_JSON_KEY), TypeTokens.TASK_TYPE_TYPE_TOKEN));
    }
  }

  private static class KeyBindingsAuditAdapter
      implements JsonDeserializer<KeyBindingsAudit> {

    public KeyBindingsAudit deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      @SuppressWarnings("unchecked") // Typecast with generic from Object is required.
      List<KbaChangeSet> changeSets = (List<KbaChangeSet>)
          context.deserialize(jo.get(CHANGE_SETS_JSON_KEY), TypeTokens.KBA_CHANGE_SET_LIST_TYPE_TOKEN);

      // This allows for a trailing comma in the changeSet
      changeSets.remove(null);
      
      return new KeyBindingsAudit(
          changeSets,
          (KbaMetaData) context.deserialize(jo.get(METADATA_JSON_KEY), TypeTokens.KBA_META_DATA_TYPE_TOKEN));
    }
  }

  private static class KbaChangeSetAdapter
      implements JsonDeserializer<KbaChangeSet> {

    public KbaChangeSet deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      return new KbaChangeSet(
          (String) context.deserialize(jo.get(SCHEME_JSON_KEY), TypeTokens.STRING_TYPE_TOKEN),
          (String) context.deserialize(jo.get(PLATFORM_JSON_KEY), TypeTokens.STRING_TYPE_TOKEN),
          (String) context.deserialize(jo.get(CONTEXT_JSON_KEY), TypeTokens.STRING_TYPE_TOKEN),
          (String) context.deserialize(jo.get(ACTION_JSON_KEY), TypeTokens.STRING_TYPE_TOKEN),
          (KbaBindingList) context.deserialize(jo.get(BINDINGS_JSON_KEY), TypeTokens.KBA_BINDING_LIST_TYPE_TOKEN));
    }
  }

  private static class KbaBindingListAdapter
      implements JsonSerializer<KbaChangeSet.KbaBindingList>,
      JsonDeserializer<KbaChangeSet.KbaBindingList>{

    public JsonElement serialize(KbaBindingList bindings, Type typeOfSrc,
        JsonSerializationContext context) {
      JsonArray array = new JsonArray();

      for (KbaBinding keyBindingSpec : bindings.getList()) {
        array.add(serialize(keyBindingSpec));
      }
      return array;
    }

    private JsonElement serialize(KbaBinding keyBindingSpec) {
      JsonObject jo = new JsonObject();
      jo.addProperty(KEYS_JSON_KEY, keyBindingSpec.getKeySequence());

      if (keyBindingSpec.getCid() != null) {
        jo.addProperty(COMMAND_JSON_KEY, keyBindingSpec.getCid());
      }
      if (!keyBindingSpec.getParameters().isEmpty()) {
        JsonObject paramjo = new JsonObject();
        for (Map.Entry<String, String> entry : keyBindingSpec.getParameters().entrySet()) {
          paramjo.addProperty(entry.getKey(), entry.getValue());
        }
        jo.add(COMMAND_PARAMETERS_JSON_KEY, paramjo);
      }
      return jo;
    }

    public KbaBindingList deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      List<KbaBinding> bindingSpecList = Util.newArrayList();
      JsonArray ja = json.getAsJsonArray();
      for (JsonElement jsonElement : ja) {
        JsonObject jo = jsonElement.getAsJsonObject();

      String keySequence = jo.get(KEYS_JSON_KEY).getAsString();
        String command = jo.get(COMMAND_JSON_KEY).getAsString();

        KbaBinding bindingSpec = new KbaBinding(
            keySequence,
            command);

        JsonObject params = jo.getAsJsonObject(COMMAND_PARAMETERS_JSON_KEY);
        if (params != null) {
          for (Entry<String, JsonElement> entry : params.entrySet()) {
            bindingSpec = bindingSpec.withParam(entry.getKey(), entry.getValue().getAsString());
          }
        }
        bindingSpecList.add(bindingSpec);
        break;
      }
      return new KbaBindingList(bindingSpecList);
    }
  }
}