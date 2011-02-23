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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;
import com.google.eclipse.mechanic.plugin.core.ResourceTaskProvider;

/**
 * Supplies a set of safe-ish directories configured in the main Mechanic
 * preferences panel.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class RegisteredTaskProvidersSupplier implements
    Supplier<List<ResourceTaskProvider>> {

  private static final RegisteredTaskProvidersSupplier instance
      = new RegisteredTaskProvidersSupplier();

  public static RegisteredTaskProvidersSupplier getInstance() {
    return instance;
  }

  public List<ResourceTaskProvider> get() {
   // This removes duplicates, but ensures insertion order.
    Set<ResourceTaskProvider> providers = new LinkedHashSet<ResourceTaskProvider>();
    for (ResourceTaskProvider provider : MechanicPreferences.getTaskProviders()) {
      providers.add(provider);
    }

    // To ensure clients don't mutate the list.
    List<ResourceTaskProvider> list = Util.newArrayList();
    list.addAll(providers);
    return Collections.unmodifiableList(list);
  }
}
