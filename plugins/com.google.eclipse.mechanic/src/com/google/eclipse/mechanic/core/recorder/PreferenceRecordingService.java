/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.recorder;

import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * An implementation of the {@link IPreferenceRecordingService}.
 * 
 * @author brianchin@google.com (BrianChin)
 */
public class PreferenceRecordingService implements IPreferenceRecordingService{

  private PreferenceRecorder preferenceRecorder;

  public synchronized void startRecording() throws CoreException {
    if (isRecording()) {
      throw new CoreException(new Status(
          IStatus.ERROR, 
          MechanicPlugin.PLUGIN_ID, 
          "Tried to start recording when already recording"));
    }
    preferenceRecorder = new PreferenceRecorder();
    preferenceRecorder.startRecording();
  }

  public synchronized boolean isRecording() {
    return preferenceRecorder != null;
  }

  public synchronized void endRecording(ChangeVisitor visitor) throws CoreException {
    if (!isRecording()) {
      throw new CoreException(new Status(
          IStatus.ERROR,
          MechanicPlugin.PLUGIN_ID,
          "Tried to end recording when not recording."));
    }
    preferenceRecorder.endRecording();
    preferenceRecorder.visitChanges(visitor);
    preferenceRecorder = null;
  }

}
