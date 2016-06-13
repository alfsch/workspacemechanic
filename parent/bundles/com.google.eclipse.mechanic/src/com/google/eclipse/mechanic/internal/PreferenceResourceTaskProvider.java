/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.eclipse.mechanic.ICollector;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.plugin.core.OldMechanicPreferences;

/**
 * This provider is loaded by the extension mechanism only, and should not
 * be instantiated by anything else.
 */
public class PreferenceResourceTaskProvider implements IResourceTaskProvider {
  // TODO(konigsberg): install a preference listener to reduce reading
  // preferences all the time.
  private IResourceTaskProvider get() {
    // This removes duplicates, but ensures insertion order.
     Set<IResourceTaskProvider> providers = Sets.newLinkedHashSet();
     for (IResourceTaskProvider provider : OldMechanicPreferences.getTaskProviders()) {
       providers.add(provider);
     }

     return new CompositeResourceTaskProvider(providers);
   }

  public void collectTaskReferences(String extFilter,
      ICollector<IResourceTaskReference> collector) {
    get().collectTaskReferences(extFilter, collector);
  }

  public void collectTaskReferences(String localPath, String extFilter,
      ICollector<IResourceTaskReference> collector) {
    get().collectTaskReferences(localPath, extFilter, collector);
  }
}
