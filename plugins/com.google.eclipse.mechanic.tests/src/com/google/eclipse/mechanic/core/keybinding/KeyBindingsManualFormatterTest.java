/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.KbaBindingList;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsManualFormatter.BindingType;

import org.eclipse.jface.bindings.Binding;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * @author zorzella
 */
public class KeyBindingsManualFormatterTest {

//  @Ignore("Remove trailing commas")
  @Test
  public void testARoundTrip() {
    BindingType bindingType = BindingType.USER;
    Multimap<KbaChangeSetQualifier, Binding> bindings = ArrayListMultimap.create();
    String contextId = "org.eclipse.ui.contexts.window";
    String schemeId = "org.eclipse.ui.defaultAcceleratorConfiguration";
    String platform = null;
    KbaChangeSetQualifier q = new KbaChangeSetQualifier(
        schemeId,
        platform,
        contextId,
        Action.ADD.toString());
    
    Map<KbaChangeSetQualifier, KbaChangeSet> m = Maps.newHashMap();
    Map<String,String> params = ImmutableMap.<String,String>builder()
        .put("org.eclipse.ui.views.showView.viewId", "org.eclipse.jdt.debug.ui.DisplayView")
        .build();
    
    List<KbaBinding> list = Lists.newArrayList();
    KbaBinding binding = new KbaBinding(
        "Shift+Alt+Q I",
        "org.eclipse.ui.views.showView",
        params);
    list.add(binding);
    KbaBindingList bindingSpecList = new KbaBindingList(list);
    
    KbaChangeSet value = new KbaChangeSet(schemeId, platform, contextId, Action.ADD.toString(), bindingSpecList);
    m.put(q, value);
    
    Map<KbaChangeSetQualifier, KbaChangeSet> n = null;
    String json = KeyBindingsManualFormatter.getBindingsPrintout(bindingType, m);

    KeyBindingsAudit kbaFromJson = KeyBindingsParser.deSerialize(new StringReader(json));
  }
}
