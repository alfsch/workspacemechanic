/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsManualFormatter.BindingType;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;

import java.util.Map;

/**
 * This class has a 1-to-1 correspondence with Eclipse's {@link Binding}, except
 * with primitives (Strings) instead of Eclipse data structure.
 *
 * <p>Like {@link Binding}, this supports a null command (whereas
 * {@link KbaBinding} will not).
 *
 * <p>This only exists because these Eclipse data structures are not unit-testable.
 *
 * @author zorzella
 */
class EclBinding {
  
  private final String cid;
  private final Map<String, String> paramMap;
  private final String schemeId;
  private final String platform;
  private final String contextId;
  private final String keySequence;
  private final BindingType type;
  
  public EclBinding(final String cid,
      final Map<String, String> paramMap,
      final String schemeId,
      final String platform,
      final String contextId,
      final String keySequence,
      final BindingType type) {
    this.cid = cid;
    this.paramMap = paramMap;
    this.schemeId = schemeId;
    this.platform = platform;
    this.contextId = contextId;
    this.keySequence = keySequence;
    this.type = type;
  }
  
  public EclBinding(Binding b) {
    this(calculateCid(b),
        KeyBindings.commandParamMap(b.getParameterizedCommand()),
        b.getSchemeId(),
        b.getPlatform(),
        b.getContextId(),
        b.getTriggerSequence().format(),
        BindingType.from(b));
  }

  private static String calculateCid(Binding b) {
    ParameterizedCommand parameterizedCommand = b.getParameterizedCommand();
    return parameterizedCommand == null ? null : parameterizedCommand.getId();
  }

  public boolean hasCommand () {
    return cid != null;
  }
  
  public String getCid() {
    return cid;
  }
  
  public String getContextId() {
    return contextId;
  }
  
  public Map<String, String> getParamMap() {
    return paramMap;
  }
  
  public String getPlatform() {
    return platform;
  }
  
  public String getSchemeId() {
    return schemeId;
  }

  public BindingType getType() {
    return type;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof EclBinding)) {
      return false;
    }
    EclBinding that = (EclBinding)obj;
    return Objects.equal(this.cid, that.cid)
        && Objects.equal(this.contextId, that.contextId)
        && Objects.equal(this.keySequence, that.keySequence)
        && Objects.equal(this.paramMap, that.paramMap)
        && Objects.equal(this.platform, that.platform)
        && Objects.equal(this.schemeId, that.schemeId)
        && Objects.equal(this.type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.cid,
        this.contextId,
        this.keySequence,
        this.paramMap,
        this.platform,
        this.schemeId,
        this.type);
  }
  
  private static final Function<Binding, EclBinding> TRANSFORM = new Function<Binding, EclBinding>() {
    public EclBinding apply(Binding b) {
      return new EclBinding(b);
    }
  };

  public KbaChangeSetQualifier with(Action action) {
    return new KbaChangeSetQualifier(schemeId, platform, contextId, action);
  }
  
  public KbaBinding toKbaBinding() {
    if (!hasCommand()) {
      throw new IllegalStateException("Undefined for remove bindings.");
    }
    return new KbaBinding(keySequence, cid, paramMap);
  }
  
  public static Iterable<EclBinding> from(Iterable<Binding> bindingList) {
    return Iterables.transform(bindingList, TRANSFORM);
  }

  /**
   * @return the keySequence
   */
  public String getKeySequence() {
    return keySequence;
  }
  
  @Override
  public String toString() {
    return String.format("type: '%s', scheme: '%s', platform: '%s', context: '%s', keys: '%s', cid: '%s', paramMap: '%s'",
        this.type, this.schemeId, this.platform, this.contextId, this.keySequence, this.cid, this.paramMap);
  }
}
