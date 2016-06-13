package com.google.eclipse.mechanic.internal;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.eclipse.mechanic.ICollector;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.IResourceTaskReference;

/**
 * Collects from multiple task providers in one go.
 */
public class CompositeResourceTaskProvider implements IResourceTaskProvider {

  private final List<IResourceTaskProvider> providers;

  public CompositeResourceTaskProvider(Collection<IResourceTaskProvider> providers) {
    this.providers = ImmutableList.copyOf(providers);
  }

  public void collectTaskReferences(String extFilter,
      ICollector<IResourceTaskReference> collector) {
    for (IResourceTaskProvider provider : providers) {
      provider.collectTaskReferences(extFilter, collector);
    }
  }

  public void collectTaskReferences(
      String localPath, String extFilter, ICollector<IResourceTaskReference> collector) {
    for (IResourceTaskProvider provider : providers) {
      provider.collectTaskReferences(localPath, extFilter, collector);
    }
  }
}
