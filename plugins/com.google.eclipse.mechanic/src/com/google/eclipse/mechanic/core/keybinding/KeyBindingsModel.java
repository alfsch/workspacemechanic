/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.eclipse.mechanic.internal.TaskType;
import com.google.eclipse.mechanic.internal.Util;
import com.google.gson.annotations.SerializedName;

/**
 * A java representation of a KeyBindings task.
 *
 * <p>This, in disk, is represented as a JSON string.
 *
 * @author zorzella@google.com
 */
class KeyBindingsModel {

  @SerializedName(KeyBindingsParser.CHANGE_SETS_JSON_KEY)
  private final ImmutableList<KeyBindingChangeSet> keyBindingsChangeSets;
  private final MetaData metadata;

  public KeyBindingsModel(List<KeyBindingChangeSet> changeSets, MetaData metadata) {
    this.keyBindingsChangeSets = ImmutableList.copyOf(changeSets);
    this.metadata = metadata;
  }

  public List<KeyBindingChangeSet> getKeyBindingsChangeSets() {
    return keyBindingsChangeSets;
  }

  public MetaData getMetadata() {
    return metadata;
  }

  @Override
  public int hashCode() {
    return Util.hashCode(this.keyBindingsChangeSets, this.metadata);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KeyBindingsModel)) {
      return false;
    }
    KeyBindingsModel that = (KeyBindingsModel)obj;
    return
      this.keyBindingsChangeSets.equals(that.keyBindingsChangeSets)
        &&
      this.metadata.equals(that.metadata);
  }

  @Override
  public String toString() {
    return String.format(
        "metadata: %s, keyBindingsChangeSets: %s",
        this.metadata, this.keyBindingsChangeSets);
  }

  public static final class MetaData {

    private final String shortDescription;
    private final String description;
    // TODO(zorzella): should this always be reconcile?
    private final TaskType type;

    public MetaData(String shortDescription, String description, TaskType type) {
      this.shortDescription = Util.checkNotNull(shortDescription);
      this.description = Util.checkNotNull(description);
      this.type = Util.checkNotNull(type);
    }

    @Override
    public int hashCode() {
      return Util.hashCode(shortDescription, description, type);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof MetaData)) {
        return false;
      }
      MetaData that = (MetaData)obj;
      return
        this.shortDescription.equals(that.shortDescription)
          &&
        this.description.equals(that.description)
          &&
        this.type == that.type;
    }

    @Override
    public String toString() {
      return String.format(
          "shortDescription: %s, description: %s, type: %s",
          this.shortDescription, this.description, this.type);
    }
  }
}
