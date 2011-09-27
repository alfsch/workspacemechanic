/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.eclipse.mechanic.internal.Util;

import java.util.Collections;
import java.util.Map;

/**
 * Abstracts a keybinding data to either add or remove.
 *
 * <p>Note that not all the information necessary to define a key binding is
 * present in this structure. This "missing" information is rather part of the
 * containing {@link KbaChangeSet}.
 *
 * <p>This class is immutable, but provides the "immutable self-builder" pattern
 * for specifying parameters. See {@link #withParam(String, String)}.
 *
 * @author zorzella@google.com
 */
public final class KbaBinding {

  private final String cid;
  private final String keySequence;
  private final Map<String,String> parameters;

  public KbaBinding(
      String keySequence,
      String cid) {
    this(keySequence, cid, Util.<String, String>newHashMap());
  }

  public KbaBinding(
      String keySequence,
      String cid,
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
  public KbaBinding withParam(String id, String value) {
    Map<String, String> temp = Util.newHashMap();
    temp.putAll(parameters);
    temp.put(id, value);
    return new KbaBinding(this.keySequence, this.cid, temp);
  }

  @Override
  public int hashCode() {
    return Util.hashCode(cid, keySequence, parameters);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KbaBinding)) {
      return false;
    }
    KbaBinding that = (KbaBinding)obj;
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
