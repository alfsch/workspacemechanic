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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.gson.annotations.SerializedName;

/**
 * A java representation of a .kbd task, abbreviated as KBA.
 *
 * <p>This, in disk, is represented as a JSON string.
 * 
 * <p>This class operates with primitive types (Strings, ints, lists), not with
 * Eclipse constructs.
 *
 * @author zorzella@google.com
 */
class KeyBindingsModel {

  @SerializedName(KeyBindingsParser.METADATA_JSON_KEY)
  private final KbaMetaData kbaMetadata;
  @SerializedName(KeyBindingsParser.CHANGE_SETS_JSON_KEY)
  private final ImmutableList<KbaChangeSet> kbaChangeSetList;

  public KeyBindingsModel(List<KbaChangeSet> changeSetList, KbaMetaData metadata) {
    this.kbaChangeSetList = filteredChangeSetList(changeSetList);
    this.kbaMetadata = metadata;
  }

  private static ImmutableList<KbaChangeSet> filteredChangeSetList(
      List<KbaChangeSet> changeSetList) {
    Predicate<KbaChangeSet> filterOutRemoveActions = new Predicate<KbaChangeSet>() {
      public boolean apply(KbaChangeSet source) {
        // TODO: support remove
        if (KeyboardBindingsTask.ENABLE_EXP_REM()) {
          return true;
        }
        return source.getAction() == Action.ADD;
      }
    };
    return ImmutableList.copyOf(Iterables.filter(changeSetList, filterOutRemoveActions));
  }

  public Iterable<KbaChangeSet> getKeyBindingsChangeSetsWith(Action action) {
    return Iterables.filter(kbaChangeSetList, matches(action));
  }
  
  private static Predicate<KbaChangeSet> matches(final Action action) {
    return new Predicate<KbaChangeSet>() {
      public boolean apply(KbaChangeSet toTest) {
        return (toTest.getAction() == action);
      }
    };
  }

  public Iterable<KbaChangeSet> getKeyBindingsChangeSets() {
    return kbaChangeSetList;
  }

  public KbaMetaData getMetadata() {
    return kbaMetadata;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.kbaChangeSetList, this.kbaMetadata);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KeyBindingsModel)) {
      return false;
    }
    KeyBindingsModel that = (KeyBindingsModel)obj;
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

    private final String description;

    public KbaMetaData(String description) {
      this.description = Preconditions.checkNotNull(description);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(description);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof KbaMetaData)) {
        return false;
      }
      KbaMetaData that = (KbaMetaData)obj;
      return
        this.description.equals(that.description);
    }

    @Override
    public String toString() {
      return String.format(
          "description: %s",
          this.description);
    }

    public String getDescription() {
      return description;
    }
  }
}
