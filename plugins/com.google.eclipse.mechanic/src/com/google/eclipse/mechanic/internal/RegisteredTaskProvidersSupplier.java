/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.plugin.core.OldMechanicPreferences;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;

/**
 * Supplies a set of safe-ish directories configured in the main Mechanic
 * preferences panel.
 */
public class RegisteredTaskProvidersSupplier implements
    Supplier<List<IResourceTaskProvider>> {

  private static final RegisteredTaskProvidersSupplier instance
      = new RegisteredTaskProvidersSupplier();

  public static RegisteredTaskProvidersSupplier getInstance() {
    return instance;
  }

  public List<IResourceTaskProvider> get() {
   // This removes duplicates, but ensures insertion order.
    Set<ResourceTaskProvider> providers = Sets.newLinkedHashSet();
    for (ResourceTaskProvider provider : OldMechanicPreferences.getTaskProviders()) {
      providers.add(provider);
    }

    // To ensure clients don't mutate the list.
    List<IResourceTaskProvider> list = Lists.newArrayList();
    list.addAll(providers);
    return Collections.unmodifiableList(list);
  }
}
