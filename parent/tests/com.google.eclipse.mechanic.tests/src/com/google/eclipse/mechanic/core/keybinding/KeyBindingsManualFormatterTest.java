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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.KbaBindingList;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsManualFormatter.BindingType;

import org.junit.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

/**
 * Tests for {@link KeyBindingsManualFormatter}
 */
public class KeyBindingsManualFormatterTest {

  private static final KbaChangeSetQualifier QUALIFIER = new KbaChangeSetQualifier(
      "org.eclipse.ui.defaultAcceleratorConfiguration",
      null, // platform
      "org.eclipse.ui.contexts.window",
      Action.ADD.toString());
  
  @Test
  public void testARoundTripCommandWithParams() {
    
    KbaBinding expectedKbaBinding = kbaBindingCommandWithParams();
    Map<KbaChangeSetQualifier, KbaChangeSet> map = kbaMap(new KbaBindingList(
        expectedKbaBinding));
    
    String json = KeyBindingsManualFormatter.getBindingsPrintout(BindingType.USER, map, "");
    KeyBindingsModel kbaFromJson = KeyBindingsParser.deSerialize(new StringReader(json));
    List<KbaChangeSet> keyBindingsChangeSetsToAdd = Lists.newArrayList(
        kbaFromJson.getKeyBindingsChangeSetsWith(Action.ADD));
    Assert.assertEquals(1, keyBindingsChangeSetsToAdd.size());
    Assert.assertEquals(1, keyBindingsChangeSetsToAdd.get(0).getBindingList().size());
    
    KbaBinding actualKbaBinding = keyBindingsChangeSetsToAdd.get(0).getBindingList().get(0);
    
    Assert.assertEquals(expectedKbaBinding, actualKbaBinding);
  }

  @Test
  public void testARoundTripCommandWithNoParams() {
    
    KbaBinding expectedKbaBinding = kbaBindingCommandWithNoParams();
    Map<KbaChangeSetQualifier, KbaChangeSet> map = kbaMap(new KbaBindingList(
        expectedKbaBinding));
    
    String json = KeyBindingsManualFormatter.getBindingsPrintout(BindingType.USER, map, "");
    KeyBindingsModel kbaFromJson = KeyBindingsParser.deSerialize(new StringReader(json));

    List<KbaChangeSet> keyBindingsChangeSetsToAdd = Lists.newArrayList(
        kbaFromJson.getKeyBindingsChangeSetsWith(Action.ADD));
    Assert.assertEquals(1, keyBindingsChangeSetsToAdd.size());
    Assert.assertEquals(1, keyBindingsChangeSetsToAdd.get(0).getBindingList().size());
    
    KbaBinding actualKbaBinding = keyBindingsChangeSetsToAdd.get(0).getBindingList().get(0);
    
    Assert.assertEquals(expectedKbaBinding, actualKbaBinding);
  }

  @Test
  public void testEscapingDescription() {
    testEscapingDescription("x");
    testEscapingDescription("Joe's Apartment");
    testEscapingDescription("Thank you, \"The Management\"");
    testEscapingDescription("Found on my c:\\ drive");
  }

  private void testEscapingDescription(String description) {
    try {
      String json = KeyBindingsManualFormatter.getBindingsPrintout(
          BindingType.USER,
          ImmutableMap.<KbaChangeSetQualifier, KbaChangeSet>of(),
          description);
  
      KeyBindingsModel kbaFromJson = KeyBindingsParser.deSerialize(new StringReader(json));
      Assert.assertEquals(description, kbaFromJson.getMetadata().getDescription());
    } catch(RuntimeException e) {
      throw new RuntimeException("For description: " + description, e);
    }
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
      "org.eclipse.jdt.ui.edit.text.java.toggle.comment",
      Maps.<String, String>newHashMap());
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
