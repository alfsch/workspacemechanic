/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Parser for json-formatted information about a URI task provider.
 */
public class UriTaskProviderModelParser {
  private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(UriTaskProviderModel.Metadata.class, new MetaDataAdapter())
      .registerTypeAdapter(UriTaskProviderModel.class, new UriTaskModelAdapter())
      .create();

  private static final class Types {
    static final Type metadata = new TypeToken<UriTaskProviderModel.Metadata>(){}.getType();
    public static Type uriArray = new TypeToken<URI[]>(){}.getType();
  }

  private static class MetaDataAdapter implements JsonDeserializer<UriTaskProviderModel.Metadata> {
    public UriTaskProviderModel.Metadata deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      String description = deserialize(jo, "description");
      if (description == null) {
        throw new JsonParseException("description is missing");
      }
      return new UriTaskProviderModel.Metadata(
          deserialize(jo, "name"),
          description,
          deserialize(jo, "contact"));
    }

    private String deserialize(JsonObject jo, String field) {
      JsonElement val = jo.get(field);
      if (val == null) {
        return null;
      }
      return val.getAsString();
    }
  }

  private static class UriTaskModelAdapter implements JsonDeserializer<UriTaskProviderModel> {
    private static final String TYPE = "com.google.eclipse.mechanic.UriTaskProviderModel";

    public UriTaskProviderModel deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      JsonObject jo = json.getAsJsonObject();

      JsonElement je = jo.get("type");
      if (je == null || !je.getAsString().equals(TYPE)) {
        throw new IllegalArgumentException(
            String.format("URI task models must have an entry type='%s'", TYPE));
      }
      UriTaskProviderModel.Metadata metadata = (UriTaskProviderModel.Metadata) context.deserialize(
          jo.get("metadata"), Types.metadata);
      if (metadata == null) {
        throw new JsonParseException("metadata is required");
      }
      URI[] uriArray = (URI[]) context.deserialize(jo.get("tasks"), Types.uriArray);
      if (uriArray == null) {
        throw new JsonParseException("tasks is requried");
      }
      List<URI> tasks = Arrays.asList(uriArray);
      return new UriTaskProviderModel(metadata, tasks);
    }
  }

  public static UriTaskProviderModel read(InputStream is) throws JsonSyntaxException, JsonIOException{
    return GSON.fromJson(new InputStreamReader(is), UriTaskProviderModel.class);
  }

  /**
   * Parse a model from a string.
   *
   * This has to exist here because GSON isn't made available outside this
   * plug-in, and the {@link #read(InputStream)} function throws
   * JsonSyntaxException and JsonIOException.
   */
  public static UriTaskProviderModel readForTests(String text) throws RuntimeException {
    return read(new ByteArrayInputStream(text.getBytes()));
  }
}
