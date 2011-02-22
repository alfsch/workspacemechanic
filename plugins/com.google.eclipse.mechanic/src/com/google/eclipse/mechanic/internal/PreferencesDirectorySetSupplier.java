// Copyright 2010 Google Inc. All Rights Reserved.

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
public class PreferencesDirectorySetSupplier implements TaskSourcesSupplier {

  private static final PreferencesDirectorySetSupplier instance
      = new PreferencesDirectorySetSupplier();

  public static PreferencesDirectorySetSupplier getInstance() {
    return instance;
  }

  public List<ResourceTaskProvider> get() {
    // This removes duplicates, but ensures insertion order.
    Set<ResourceTaskProvider> sources = new LinkedHashSet<ResourceTaskProvider>();
    for (ResourceTaskProvider source : MechanicPreferences.getTaskSources()) {
      sources.add(source);
    }

    // To ensure clients don't mutate the list.
    List<ResourceTaskProvider> list = Util.newArrayList();
    list.addAll(sources);
    return Collections.unmodifiableList(list);
  }
}
