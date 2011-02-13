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

import java.util.Collections;
import java.util.Map;

/**
 * Abstracts a keybinding data to either add or remove.
 *
 * <p>Note that not all the information necessary to define a key binding is
 * present in this structure. This "missing" information is rather part of the
 * containing {@link KeyBindingChangeSet}.
 *
 * <p>This class is immutable, but provides the "immutable self-builder" pattern
 * for specifying parameters. See {@link #withParam(String, String)}.
 *
 * @author zorzella@google.com
 */
public final class KeyBindingSpec {

  private final String cid;
  private final String keySequence;
  private final Map<String,String> parameters;

  public KeyBindingSpec(
      String cid,
      String keySequence) {
    this(cid, keySequence, Util.<String, String>newHashMap());
  }

  public KeyBindingSpec(
      String cid,
      String keySequence,
      Map<String,String> parameters) {
    this.cid = cid;
    this.keySequence = Util.checkNotNull(keySequence);
    Map<String, String> temp = Util.newHashMap();
    temp.putAll(parameters);
    this.parameters = Collections.unmodifiableMap(temp);
  }

  public String getCid() {
    return cid;
  }

  public String getKeySequence() {
    return keySequence;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  /**
   * Returns a copy of this class, with one parameters map addition of id/value.
   *
   * <p>This is provided for convenience of progressively building this class
   * without requiring a map builder or mutable map.
   */
  public KeyBindingSpec withParam(String id, String value) {
    Map<String, String> temp = Util.newHashMap();
    temp.putAll(parameters);
    temp.put(id, value);
    return new KeyBindingSpec(this.cid, this.keySequence, temp);
  }

  @Override
  public int hashCode() {
    return Util.hashCode(cid, keySequence, parameters);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KeyBindingSpec)) {
      return false;
    }
    KeyBindingSpec that = (KeyBindingSpec)obj;
    if (this.cid == null) {
      if (that.cid != null) {
        return false;
      }
    } else {
      if (!this.cid.equals(that.cid)) {
        return false;
      }
    }
    if (this.parameters == null) {
      if (that.parameters != null) {
        return false;
      }
    } else {
      if (!this.parameters.equals(that.parameters)) {
        return false;
      }
    }
    return this.keySequence.equals(that.keySequence);
  }

  @Override
  public String toString() {
    return String.format(
      "keySequence: %s, cid: %s, parameters: %s",
      keySequence, cid, parameters);
  }
}
