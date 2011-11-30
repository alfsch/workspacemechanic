/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.core.keybinding.KeyBindingsManualFormatter.BindingType;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.testing.EmptyLog;

/**
 * @author zorzella
 */
public class KbaBootstrapperTest {

  private static final String SCHEME = "scheme";
  private static final String CURRENT_PLATFORM = "gtk";
  private static final String NULL_PLATFORM = null;
  private static final String CONTEXT = "context";
  private static final Map<String, String> EMPTY = Collections.emptyMap();

  private static final KbaChangeSetQualifier ADD_Q_NULL_PLATFORM =
      new KbaChangeSetQualifier(SCHEME, NULL_PLATFORM, CONTEXT, Action.ADD);
  private static final KbaChangeSetQualifier ADD_Q_CURRENT_PLATFORM =
      new KbaChangeSetQualifier(SCHEME, CURRENT_PLATFORM, CONTEXT, Action.ADD);
  private static final KbaChangeSetQualifier REM_Q_NULL_PLATFORM =
      new KbaChangeSetQualifier(SCHEME, NULL_PLATFORM, CONTEXT, Action.REMOVE);
  private static final KbaChangeSetQualifier REM_Q_CURRENT_PLATFORM =
      new KbaChangeSetQualifier(SCHEME, CURRENT_PLATFORM, CONTEXT, Action.REMOVE);

  @Test
  public void testCombo() {
    
    Iterable<EclBinding> sysBs = Lists.newArrayList(
        sysBinding("key0", "cid0"),
        sysBinding("key1", "cid1"),
        sysBinding("key5", "cid1"),
        sysBinding("key4", "cid4", CURRENT_PLATFORM));

    Iterable<EclBinding> userBs = Lists.newArrayList(
        remBinding("key1"),
        remBinding("key4"),
        usrBinding("key2", "cid2"),
        usrBinding("key2", "cid2"), // Should be uniquified in the process
        usrBinding("key3", "cid3")
        );
    
    Map<KbaChangeSetQualifier, KbaChangeSet> systemBindings =
        KbaBootstrapper.buildSystemBindingsMap(sysBs);

    Map<KbaChangeSetQualifier, KbaChangeSet> userBindings =
        KbaBootstrapper.buildUserBindingsMap(CURRENT_PLATFORM, systemBindings,
            userBs, new MechanicLog(new EmptyLog()));

    KbaChangeSet actualSysBindings = systemBindings.get(ADD_Q_NULL_PLATFORM);
    Assert.assertEquals(3, actualSysBindings.getBindingList().size());

    KbaChangeSet actualSysBindingsCurrentPlatform = systemBindings.get(ADD_Q_CURRENT_PLATFORM);
    Assert.assertEquals(1, actualSysBindingsCurrentPlatform.getBindingList().size());
    
    KbaChangeSet actualUserAddBindings = userBindings.get(ADD_Q_NULL_PLATFORM);
    Assert.assertEquals(2, actualUserAddBindings.getBindingList().size());

    KbaChangeSet actualUserRemBindings = userBindings.get(REM_Q_NULL_PLATFORM);
    Assert.assertEquals(2, actualUserRemBindings.getBindingList().size());
    
    Set<KbaBinding> actualRemoved = Sets.newHashSet(actualUserRemBindings.getBindingList());
    Set<KbaBinding> expectedRemoved = Sets.newHashSet(
        new KbaBinding("key1", "cid1"),
        new KbaBinding("key4", "cid4"));
    Assert.assertEquals(expectedRemoved, actualRemoved);
    
    Assert.assertEquals(Action.REMOVE, actualUserRemBindings.getAction());
  }

  @Test
  public void testPurgeCycles() throws Exception {
    Map<KbaChangeSetQualifier, KbaChangeSet> map = Maps.newHashMap();
    KbaChangeSetQualifier addKey = ADD_Q_CURRENT_PLATFORM;
    KbaChangeSetQualifier remKey = REM_Q_CURRENT_PLATFORM;
    KbaBinding fooCommand = new KbaBinding("Ctrl+Alt+F", "com.google.FooCommand");
    KbaBinding barCommand = new KbaBinding("Ctrl+Alt+G", "com.google.BarCommand");
    Iterable<KbaBinding> bindingListToAdd = Lists.newArrayList(
        fooCommand,
        barCommand
        );
    Iterable<KbaBinding> bindingListToRem = Lists.newArrayList(
        fooCommand
        );
    KbaChangeSet changeSetToAdd = new KbaChangeSet(addKey, bindingListToAdd);
    KbaChangeSet changeSetToRem = new KbaChangeSet(remKey, bindingListToRem);
    
    map.put(addKey, changeSetToAdd);
    map.put(remKey, changeSetToRem);
    
    Assert.assertTrue(map.get(addKey).getBindingList().contains(fooCommand));
    Assert.assertTrue(map.get(addKey).getBindingList().contains(barCommand));
    Assert.assertTrue(map.get(remKey).getBindingList().contains(fooCommand));
    Map<KbaChangeSetQualifier, KbaChangeSet> result = KbaBootstrapper.purgeCycles(map);
    Assert.assertTrue(result.get(addKey).getBindingList().contains(fooCommand));
    Assert.assertTrue(result.get(addKey).getBindingList().contains(barCommand));
    Assert.assertFalse(result.get(remKey).getBindingList().contains(fooCommand));
  }
  
  private EclBinding remBinding(String key) {
    return new EclBinding(null, EMPTY, SCHEME, NULL_PLATFORM, CONTEXT, key, BindingType.USER);
  }
  
  private EclBinding usrBinding(String key, String cid) {
    return new EclBinding(cid, EMPTY, SCHEME, NULL_PLATFORM, CONTEXT, key, BindingType.USER);
  }
  
  private EclBinding sysBinding(String key, String cid) {
    return new EclBinding(cid, EMPTY, SCHEME, NULL_PLATFORM, CONTEXT, key, BindingType.SYSTEM);
  }

  private EclBinding sysBinding(String key, String cid, String platform) {
    return new EclBinding(cid, EMPTY, SCHEME, platform, CONTEXT, key, BindingType.SYSTEM);
  }

}
