/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.util.Collection;

import com.google.eclipse.mechanic.internal.Util;
import com.google.gson.annotations.SerializedName;

/**
 * See {@link KeyBindingsParser}. This class represents a collection of key
 * bindings to add and another collection to remove, all of which share the
 * same scheme, plaform and contextId.
 *
 * @author zorzella@google.com
 */
final class KeyBindingChangeSet {

  static final class Bindings {
    private final Collection<KeyBindingSpec> toAdd;
    private final Collection<KeyBindingSpec> toRemove;

    public Bindings(Collection<KeyBindingSpec> toAdd,
        Collection<KeyBindingSpec> toRemove) {
      // TODO: ImmutableList
      this.toAdd = Util.checkNotNull(toAdd);
      // TODO: ImmutableList
      this.toRemove = Util.checkNotNull(toRemove);
    }

    @Override
    public int hashCode() {
      return Util.hashCode(toAdd, toRemove);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Bindings)) {
        return false;
      }
      Bindings that = (Bindings) obj;
      return this.toAdd.equals(that.toAdd)
          && this.toRemove.equals(that.toRemove);
    }
    @Override
    public String toString() {
      return String.format(
          "toAdd: \ntoRemove: %s",
          this.toAdd, this.toRemove);
    }

    public Collection<KeyBindingSpec> toAdd() {
      return toAdd;
    }

    public Collection<KeyBindingSpec> toRemove() {
      return toRemove;
    }
  }

  @SerializedName("scheme")
  private final String schemeId;
  private final String platform;
  @SerializedName("context")
  private final String contextId;
  private final Bindings bindings;

  KeyBindingChangeSet(
      String schemeId,
      String platform,
      String contextId,
      Bindings bindings) {
    this.schemeId = Util.checkNotNull(schemeId);
    this.platform = Util.checkNotNull(platform);
    this.contextId = Util.checkNotNull(contextId);
    this.bindings = Util.checkNotNull(bindings);
  }

  KeyBindingChangeSet(
      String schemeId,
      String platform,
      String contextId,
      Collection<KeyBindingSpec> toAdd,
      Collection<KeyBindingSpec> toRemove) {
    this(schemeId, platform, contextId, new Bindings(toAdd, toRemove));
  }

  public String getSchemeId() {
    return schemeId;
  }

  public String getPlatform() {
    return platform;
  }

  public String getContextId() {
    return contextId;
  }

  public Collection<KeyBindingSpec> toAdd() {
    return bindings.toAdd();
  }

  public Collection<KeyBindingSpec> toRemove() {
    return bindings.toRemove();
  }

  @Override
  public int hashCode() {
    return Util.hashCode(schemeId, platform, contextId, bindings);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KeyBindingChangeSet)) {
      return false;
    }
    KeyBindingChangeSet that = (KeyBindingChangeSet) obj;
    return this.schemeId.equals(that.schemeId)
        && this.platform.equals(that.platform)
        && this.contextId.equals(that.contextId)
        && this.bindings.equals(that.bindings);
  }

  @Override
  public String toString() {
    return String.format(
        "schemeId: %s\nplatform: %s\ncontextId: %s\nbindings: %s",
        this.schemeId, this.platform, this.contextId, this.bindings);
  }
}