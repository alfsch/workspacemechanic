/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.keybinding;

import com.google.eclipse.mechanic.internal.Util;

import java.util.Collection;

/**
 * See {@link KeyBindingsParser}. This class represents a collection of key
 * bindings to add and another collection to remove, all of which share the
 * same scheme, plaform and contextId.
 * 
 * @author zorzella@google.com
 */
// TODO: make package-protected
public final class KeyBindingChangeSet {

  private final String schemeId;
  private final String platform;
  private final String contextId;
  private final Collection<KeyBindingSpec> toAdd;
  private final Collection<KeyBindingSpec> toRemove;
  
  KeyBindingChangeSet(
      String schemeId, 
      String platform, 
      String contextId,
      Collection<KeyBindingSpec> toAdd, 
      Collection<KeyBindingSpec> toRemove) {
    this.schemeId = Util.checkNotNull(schemeId);
    this.platform = Util.checkNotNull(platform);
    this.contextId = Util.checkNotNull(contextId);
    // TODO: ImmutableList
    this.toAdd = Util.checkNotNull(toAdd);
    // TODO: ImmutableList
    this.toRemove = Util.checkNotNull(toRemove);
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
    return toAdd;
  }

  public Collection<KeyBindingSpec> toRemove() {
    return toRemove;
  }

  @Override
  public int hashCode() {
    return Util.hashCode(schemeId, platform, contextId, toAdd, toRemove);
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
        && this.toAdd.equals(that.toAdd)
        && this.toRemove.equals(that.toRemove);
  }
  
  @Override
  public String toString() {
    return String.format(
        "schemeId: %s\nplatform: %s\ncontextId: %s\ntoAdd: \ntoRemove: %s",
        this.schemeId, this.platform, this.contextId, this.toAdd, this.toRemove);
  }
}