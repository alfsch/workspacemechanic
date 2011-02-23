/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.ui.MechanicStatusControlContribution.DisplayStatus;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Helper class for accessing and managing images.
 * 
 * @author smckay@google.com (Steve McKay)
 */
public final class ImageUtil {
  private static ImageRegistry registry;
  
  private ImageUtil() {
    // no instantiation
  }

  public static Image getImage(DisplayStatus status) {
    return getImageRegistry().get(status.name().toLowerCase());
  }

  private static ImageRegistry getImageRegistry() {
    if (registry == null) {
      registry = new ImageRegistry();
      for (DisplayStatus status : DisplayStatus.values()) {
        registerIcon(status);
      }
    }
    return registry;
  }

  private static void registerIcon(DisplayStatus status) {
    String path = String.format("icons/%s.gif", status.name().toLowerCase());
    ImageDescriptor desc = MechanicPlugin.getImageDescriptor(path);
    registry.put(status.name(), desc);
  }
}
