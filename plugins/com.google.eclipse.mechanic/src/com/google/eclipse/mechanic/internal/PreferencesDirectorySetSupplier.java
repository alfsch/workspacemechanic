// Copyright 2010 Google Inc. All Rights Reserved.

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;
import com.google.eclipse.mechanic.plugin.core.MechanicPreferences;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Supplies a set of safe-ish directories configured in the main Mechanic
 * preferences panel.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class PreferencesDirectorySetSupplier implements DirectorySetSupplier {

  private static final ILog log = MechanicPlugin.getDefault().getLog();
  private static final PreferencesDirectorySetSupplier instance
      = new PreferencesDirectorySetSupplier();

  public static PreferencesDirectorySetSupplier getInstance() {
    return instance;
  }

  public List<File> get() {
    // This removes duplicates, but ensures insertion order.
    Set<File> dirs = new LinkedHashSet<File>();
    for (File dir : MechanicPreferences.getTaskSources()) {
      if (!dir.exists()) {
        log.log(new Status(IStatus.WARNING, MechanicPlugin.PLUGIN_ID,
            String.format("Directory '%s' does not exist.", dir)));
        continue;
      }

      if (!dir.canRead()) {
        log.log(new Status(IStatus.WARNING, MechanicPlugin.PLUGIN_ID,
            String.format("Directory '%s' cannot be read.", dir)));
        continue;
      }

      dirs.add(dir);
    }

    // To ensure clients don't mutate the list.
    List<File> list = Util.newArrayList();
    list.addAll(dirs);
    return Collections.unmodifiableList(list);
  }
}
