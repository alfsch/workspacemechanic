/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.collect.ImmutableMap;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.KbaBindingList;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsManualFormatter.BindingType;

import org.junit.Test;

import java.io.StringReader;
import java.util.Map;

/**
 * @author zorzella
 */
public class KeyBindingsManualFormatterTest {

  private static final KbaChangeSetQualifier QUALIFIER = new KbaChangeSetQualifier(
      "org.eclipse.ui.defaultAcceleratorConfiguration",
      null, // platform
      "org.eclipse.ui.contexts.window",
      Action.ADD.toString());
  
  @Test
  public void testARoundTripCommandWithParams() {
    
    Map<KbaChangeSetQualifier, KbaChangeSet> map = kbaMap(new KbaBindingList(
        kbaBindingCommandWithParams()));
    
    String json = KeyBindingsManualFormatter.getBindingsPrintout(BindingType.USER, map);
    KeyBindingsAudit kbaFromJson = KeyBindingsParser.deSerialize(new StringReader(json));
  }

  @Test
  public void testARoundTripCommandWithNoParams() {
    
    Map<KbaChangeSetQualifier, KbaChangeSet> map = kbaMap(new KbaBindingList(
        kbaBindingCommandWithNoParams()));
    
    String json = KeyBindingsManualFormatter.getBindingsPrintout(BindingType.USER, map);
    KeyBindingsAudit kbaFromJson = KeyBindingsParser.deSerialize(new StringReader(json));
  }
  
  private static ImmutableMap<KbaChangeSetQualifier, KbaChangeSet> kbaMap(
      KbaBindingList kbaBindingList) {
    return ImmutableMap.<KbaChangeSetQualifier, KbaChangeSet>builder()
        .put(QUALIFIER, kbaChangeSetFor(kbaBindingList))
        .build();
  }

  private static KbaChangeSet kbaChangeSetFor(KbaBindingList kbaBindingList) {
    return new KbaChangeSet(QUALIFIER.scheme, QUALIFIER.platform, QUALIFIER.context, QUALIFIER.action, kbaBindingList);
  }

  private static KbaBinding kbaBindingCommandWithNoParams() {
    KbaBinding kbaBinding = new KbaBinding(
      "Ctrl+/",
      "org.eclipse.jdt.ui.edit.text.java.toggle.comment");
    return kbaBinding;
  }

  private static KbaBinding kbaBindingCommandWithParams() {
    Map<String,String> params = ImmutableMap.<String,String>builder()
        .put("org.eclipse.ui.views.showView.viewId", "org.eclipse.jdt.debug.ui.DisplayView")
        .build();
    
    KbaBinding kbaBinding = new KbaBinding(
      "Shift+Alt+Q I",
      "org.eclipse.ui.views.showView",
      params);
    return kbaBinding;
  }
}
