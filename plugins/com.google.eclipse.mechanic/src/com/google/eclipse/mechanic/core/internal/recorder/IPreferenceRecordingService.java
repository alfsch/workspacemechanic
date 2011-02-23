/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.internal.recorder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * An interface to a service that allows preference changes that occur between 
 * calls to {@link #startRecording()} and {@link #endRecording(ChangeVisitor)} 
 * to be recorded and visited.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public interface IPreferenceRecordingService {
  /**
   * An interface to a visitor over recorded changes. This is intended to be
   * implemented by clients. 
   */
  public interface ChangeVisitor {
    /**
     * Called to indicate that a preference node was added.
     * 
     * @param parentPath The path to the parent of the added child
     * @param childName The name of the new child
     */
    void nodeAdded(IPath parentPath, String childName);
    
    /**
     * Called to indicate that a preference node was removed.
     * 
     * @param parentPath The path to the parent of the removed child
     * @param childName The name of the removed child
     */
    void nodeRemoved(IPath parentPath, String childName);
    
    /**
     * Called to indicate that a preference in a node was modified.
     * 
     * @param nodePath The path to the node whose preference was modified.
     * @param key The key of the modified preference.
     * @param value The new value of the preference.
     */
    void preferenceChanged(IPath nodePath, String key, String value);
  }
  
  /**
   * Starts recording preference changes. This process is ended by 
   * {@link #endRecording(ChangeVisitor)}.
   * 
   * @throws CoreException thrown if called when the service is already recording.
   */
  void startRecording() throws CoreException;
  
  /**
   * Returns true if this service is currently in the middle of a recording 
   * process.
   */
  boolean isRecording();
  
  /**
   * Ends recording preference changes started from the previous 
   * {@link #startRecording()}. Each change that occurred during this recording
   * session is reported to {@code visitor} in the order they occurred.
   * 
   * @param visitor a visitor object to which changes since the last 
   *     {@link #startRecording()} will be reported.
   * @throws CoreException thrown if called when a recording session has not
   *     been started.
   */
  void endRecording(ChangeVisitor visitor) throws CoreException;
}
