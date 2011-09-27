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
 * A java representation of a .kbd audit, abbreviated as KBA.
 *
 * <p>This, in disk, is represented as a JSON string.
 * 
 * <p>This class operates with primitive types (Strings, ints, lists), not with
 * Eclipse constructs.
 *
 * @author zorzella@google.com
 */
class KeyBindingsAudit {

  @SerializedName(KeyBindingsParser.METADATA_JSON_KEY)
  private final KbaMetaData kbaMetadata;
  @SerializedName(KeyBindingsParser.CHANGE_SETS_JSON_KEY)
  private final ImmutableList<KbaChangeSet> kbaChangeSetList;

  public KeyBindingsAudit(List<KbaChangeSet> changeSets, KbaMetaData metadata) {
    this.kbaChangeSetList = ImmutableList.copyOf(changeSets);
    this.kbaMetadata = metadata;
  }

  public List<KbaChangeSet> getKeyBindingsChangeSets() {
    return kbaChangeSetList;
  }

  public KbaMetaData getMetadata() {
    return kbaMetadata;
  }

  @Override
  public int hashCode() {
    return Util.hashCode(this.kbaChangeSetList, this.kbaMetadata);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KeyBindingsAudit)) {
      return false;
    }
    KeyBindingsAudit that = (KeyBindingsAudit)obj;
    return
      this.kbaChangeSetList.equals(that.kbaChangeSetList)
        &&
      this.kbaMetadata.equals(that.kbaMetadata);
  }

  @Override
  public String toString() {
    return String.format(
        "metadata: %s, keyBindingsChangeSets: %s",
        this.kbaMetadata, this.kbaChangeSetList);
  }

  public static final class KbaMetaData {

    private final String shortDescription;
    private final String description;
    // TODO(zorzella): should this always be reconcile?
    private final TaskType type;

    public KbaMetaData(String shortDescription, String description, TaskType type) {
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
      if (!(obj instanceof KbaMetaData)) {
        return false;
      }
      KbaMetaData that = (KbaMetaData)obj;
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
