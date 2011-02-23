/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.internal.recorder;

import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.osgi.service.prefs.BackingStoreException;

import java.util.List;
import java.util.Set;

/**
 * Records changes to Eclipse's preference tree, and allows the changes to be
 * later visited.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public class PreferenceRecorder {
  private class RecorderListener implements INodeChangeListener, IPreferenceChangeListener {
    public void added(NodeChangeEvent event) {
      synchronized (lock) {
        // It's possible that we've stopped recording, but the listeners have
        // not yet been removed.
        // Ensure that we're still recording before writing new data
        if (currState != State.RECORDING) {
          return;
        }

        IEclipsePreferences childPrefs = (IEclipsePreferences) event.getChild();
        addListener(childPrefs);

        changeLog.add(new NodeChanged(true, new Path(event.getParent().absolutePath()), 
            event.getChild().name()));
      }
    }

    public void removed(NodeChangeEvent event) {
      synchronized (lock) {
        if (currState != State.RECORDING) {
          return;
        }

        recordedNodes.remove(event.getChild());
        changeLog.add(new NodeChanged(false, new Path(event.getParent().absolutePath()), 
            event.getChild().name()));
      }
    }

    public void preferenceChange(PreferenceChangeEvent event) {
      synchronized (lock) {
        if (currState != State.RECORDING) {
          return;
        }
        changeLog.add(new PreferenceChanged(new Path(event.getNode().absolutePath()), 
            event.getKey(), (String) event.getNewValue()));
      }
    }
  }

  private static interface PreferenceChange {
    void visit(IPreferenceRecordingService.ChangeVisitor visitor);
  }

  private static class NodeChanged implements PreferenceChange {
    private boolean added;
    private IPath parentPath;
    private String childName;

    public NodeChanged(boolean added, IPath parentPath, String childName) {
      this.added = added;
      this.parentPath = parentPath;
      this.childName = childName;
    }

    public void visit(IPreferenceRecordingService.ChangeVisitor visitor) {
      if (added) {
        visitor.nodeAdded(parentPath, childName);
      } else {
        visitor.nodeRemoved(parentPath, childName);
      }
    }
  }

  private static class PreferenceChanged implements PreferenceChange {
    private IPath nodePath;
    private String preferenceKey;
    private String preferenceValue;

    public PreferenceChanged(IPath nodePath, String key, String value) {
      this.nodePath = nodePath;
      this.preferenceKey = key;
      this.preferenceValue = value;
    }

    public void visit(IPreferenceRecordingService.ChangeVisitor visitor) {
      visitor.preferenceChanged(nodePath, preferenceKey, preferenceValue);
    }
  }

  private enum State {
    IDLE, RECORDING, COMPLETE
  }

  private final Object lock = new Object();
  private volatile State currState = State.IDLE;
  private final RecorderListener listener = new RecorderListener();
  private Set<IEclipsePreferences> recordedNodes = Util.newHashSet();
  private List<PreferenceChange> changeLog = Util.newArrayList();

  /**
   * Starts the process of recording changes to the eclipse preference tree.
   * Recording may only occur once per recorder.
   * 
   * @throws CoreException if there is a problem with the preference tree
   *         itself.
   * @throws IllegalStateException if this object has already been used to
   *         record.
   */
  public void startRecording() throws CoreException {
    synchronized (lock) {
      Util.checkState(currState == State.IDLE,
          "Recorder object has already recorded preference changes.");

      currState = State.RECORDING;

      this.addListeners(Platform.getPreferencesService().getRootNode());
    }
  }

  private void addListeners(IEclipsePreferences node) throws CoreException {
    addListener(node);

    try {
      for (String childName : node.childrenNames()) {
        IEclipsePreferences child = (IEclipsePreferences) node.node(childName);
        this.addListeners(child);
      }
    } catch (BackingStoreException e) {
      throw new CoreException(new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID,
          "Could not read children for preference node", e));
    }
  }

  /**
   * Ends the recording process. No other changes to the preference tree are
   * recorded after this method is called.
   * 
   * @throws IllegalStateException if {@code startRecording()} had not been
   *         previously called, or if recording has already completed on this
   *         object.
   */
  public void endRecording() {
    synchronized (lock) {
      Util.checkState(currState == State.RECORDING, "Recorder object is not currently recording");

      currState = State.COMPLETE;

      for (IEclipsePreferences node : recordedNodes) {
        node.removeNodeChangeListener(listener);
        node.removePreferenceChangeListener(listener);
      }

      recordedNodes.clear();
    }
  }

  /**
   * Returns true if this object is currently recording.
   */
  public boolean isRecording() {
    return this.currState == State.RECORDING;
  }

  /**
   * Returns true if this object is finished recording, and its results are
   * ready to be used.
   */
  public boolean hasRecorded() {
    return this.currState == State.COMPLETE;
  }

  /**
   * Visits each change in turn, and calls the appropriate method on {@code 
   * visitor} for that change in the order the changes occured.
   */
  public void visitChanges(IPreferenceRecordingService.ChangeVisitor visitor) {
    synchronized (lock) {
      Util.checkState(currState == State.COMPLETE, 
          "Recorder object has not yet finished recording");

      for (PreferenceChange change : changeLog) {
        change.visit(visitor);
      }
    }
  }

  private void addListener(IEclipsePreferences node) {
    node.addNodeChangeListener(listener);
    node.addPreferenceChangeListener(listener);
    recordedNodes.add(node);
  }
}
