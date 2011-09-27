// Copyright 2011 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.internal.Util;

/**
 * Qualifies a binding by scheme/platform/context/action
 * 
 * <p>If platform is {@code null}, this applies to all platforms.
 */
final class KbaChangeSetQualifier {
  final String scheme;
  final String platform;
  final String context;
  final String action;

  public KbaChangeSetQualifier(
      final String scheme,
      final String platform,
      final String context,
      final String action) {
    this.scheme = Preconditions.checkNotNull(scheme);
    this.platform = platform;
    this.context = Preconditions.checkNotNull(context);
    this.action = action;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof KbaChangeSetQualifier)) {
      return false;
    }
    KbaChangeSetQualifier that = (KbaChangeSetQualifier)obj;
    return Util.equals(this.scheme, that.scheme) 
        && Util.equals(this.platform, that.platform)
        && Util.equals(this.context, that.context)
        && Util.equals(this.action, that.action);
  }
  
  @Override
  public int hashCode() {
    return Util.hashCode(this.scheme, this.platform, this.context, this.action);
  }
}