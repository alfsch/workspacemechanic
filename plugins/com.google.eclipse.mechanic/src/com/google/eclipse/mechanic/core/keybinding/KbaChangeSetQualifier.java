// Copyright 2011 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.base.Preconditions;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.internal.Util;

/**
 * Qualifies a binding by scheme/platform/context/action
 * 
 * <p>If platform is {@code null}, this applies to all platforms.
 */
// TODO: leverage this class in KbaChangeSet
final class KbaChangeSetQualifier {
  final String scheme;
  final String platform;
  final String context;
  final String action;

  public KbaChangeSetQualifier(
      final String scheme,
      final String platform,
      final String context,
      final String actionLabel) {
    this(scheme,
        platform,
        context,
        Action.fromLabel(actionLabel));
  }

  public KbaChangeSetQualifier(
      final String scheme,
      final String platform,
      final String context,
      final Action action) {
    this.scheme = Preconditions.checkNotNull(scheme);
    this.platform = platform;
    this.context = Preconditions.checkNotNull(context);
    this.action = action.toString();
  }

  public Action getAction() {
    return Action.fromLabel(action);
  }
 
  @Override
  public String toString() {
    return String.format(
        "scheme: '%s', platform: '%s', context: '%s', action: '%s'",
        scheme, platform, context, action);
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