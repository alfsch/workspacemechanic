/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.recorder;

import org.eclipse.core.runtime.IPath;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Collects change events from the recording service into a
 * single list of changed preferences. The resulting list does not have any
 * preferences for nodes which were deleted, and ensures later changes override
 * earlier ones.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public class ChangeCollector implements IPreferenceRecordingService.ChangeVisitor {

  private Map<IPath, String> preferences = new HashMap<IPath, String>();

  public void nodeAdded(IPath parentPath, String childName) {
    // Do nothing
  }
  
  public void nodeRemoved(IPath parentPath, String childName) {
    // Remove all preference changes rooted at this node or below
    Set<IPath> removedKeys = new HashSet<IPath>();
    for (Map.Entry<IPath, String> entry : preferences.entrySet()) {
      IPath keyPath = entry.getKey();
      if (parentPath.isPrefixOf(keyPath)) {
        removedKeys.add(keyPath);
      }
    }

    for (IPath key : removedKeys) {
      preferences.remove(key);
    }
  }

  public void preferenceChanged(IPath nodePath, String key, String value) {
    preferences.put(nodePath.append(key), value);
  }

  /**
   * Get the preferences which were set by the collected changes.
   * 
   * @return a map from preference keys to values. Keys are in path format.
   */
  public Map<String, String> getPreferences() {
    Map<String, String> result = new HashMap<String, String>();
    for (Map.Entry<IPath, String> e : preferences.entrySet()) {
      result.put(e.getKey().toPortableString(), e.getValue());
    }
    return Collections.unmodifiableMap(result);
  }
}
