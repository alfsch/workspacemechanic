package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.MechanicService;

public class UriCaches {
  private static final StateSensitiveCache stateSensitive =
      new StateSensitiveCache(MechanicService.getInstance());
  private static final IUriContentProvider lifetime = new ThreadsafeUriContentCache();

  public static void initialize() {
    stateSensitive.initialize();
  }

  public static void destroy() {
    stateSensitive.dispose();
  }

  public static IUriContentProvider getStateSensitiveCache() {
    return stateSensitive;
  }

  public static IUriContentProvider getLifetimeCache() {
    return lifetime;
  }
}
