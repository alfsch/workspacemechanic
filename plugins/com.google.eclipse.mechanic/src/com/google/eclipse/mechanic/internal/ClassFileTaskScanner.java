/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.DirectoryIteratingTaskScanner;
import com.google.eclipse.mechanic.SuffixFileFilter;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Scans for {@link Task}s defined as Java class files.
 *
 * <p>Classes can be loaded from arbitrary locations on disk. Currently the
 * classes must be declared in the {@code com.google.eclipse.mechanic.ext}
 * package.
 *
 * @author smckay@google.com (Steve McKay)
 */
public final class ClassFileTaskScanner extends DirectoryIteratingTaskScanner {

  private static final Logger DEBUGLOG = Logger.getLogger(
      ClassFileTaskScanner.class.getName());

  private static final ILog LOG = MechanicPlugin.getDefault().getLog();

  private static final FileFilter CLASS_FILTER = new SuffixFileFilter(".class");

  // where we look for Java class tasks
  private static final String EXT_PATH = "com/google/eclipse/mechanic/ext";

  private static final String EXT_PACKAGE = EXT_PATH.replace('/', '.');

  private final FileClassLoader loader;

  public ClassFileTaskScanner() {
    loader = new FileClassLoader(
        MechanicPlugin.getDefault().getClass().getClassLoader());
  }

  /**
   * Adds tasks to the supplied List.
   */
  @Override
  protected void scan(File dir, TaskCollector collector) {
    // dir points to the root of the task directory, we
    // add the PACKAGE PATH to point to the dir with classes.
    dir = new File(createPath(dir.getAbsolutePath(), EXT_PATH));

    /*
     * List of all task classes. We populate this list will
     * loading and resolving classes. Later, once all classes have been
     * resolved, we'll create new instances of these classes and add
     * them to the supplied list of Tasks.
     */
    List<Class<?>> taskClasses = Util.newArrayList();

    File[] filesInDir = dir.listFiles(CLASS_FILTER);
    if (filesInDir == null) {
      // listFiles() can return null if the directory doesn't exist or an IO error occurs.
      LOG.log(new Status(IStatus.WARNING, MechanicPlugin.PLUGIN_ID,
          "Unable to listFiles for directory '" + dir + "'"));
      return;
    }
    for (File file : filesInDir) {
      Class<?> clazz = null;
      try {

        /*
         * Load all classes we find. This enables a more full bodied
         * experience for the Task writer by providing support for
         * not just AutitItem classes, but any other class they throw into
         * the package directory, and any inner classes they've included
         * in their Tasks.
         */
        DEBUGLOG.log(Level.FINE, "Loading class from {0}", file);
        clazz = loader.classForFile(file);

      /*
       * We're attempting to load a stranger, so we need to protect against
       * all possible Throwable thingies.
       */
      } catch (ClassNotFoundException e) {
        logExceptionLoadingTask(e, file);
      } catch (RuntimeException e) {
        logExceptionLoadingTask(e, file);
      } catch (Error e) {
        logExceptionLoadingTask(e, file);
      }

      /*
       * Finally, if the class is an Task, keep a reference to it. We'll
       * want to create instances later. All other classes are either inner
       * classes are Util classes. We don't instantiate them directly, so
       * we don't keep a reference.
       */
      if (clazz != null && Task.class.isAssignableFrom(clazz)) {
        taskClasses.add(clazz);
      }
    }

    /*
     * Once we've resolved all of the classes in the source directory we can
     * go about creating instances of the Tasks.
     */
    for (Class<?> clazz : taskClasses) {
      try {
        Task item = createInstance(clazz);
        if (item != null) {
          DEBUGLOG.log(Level.FINE, "Instantiated task: {0}", clazz);
          collector.add(item);
        }
      } catch (RuntimeException e) {
        LOG.log(new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID,
            String.format("Couldn't instantiate class: %s", clazz), e));
      }
    }
  }

  private void logExceptionLoadingTask(Throwable t, File file) {
    LOG.log(new Status(IStatus.ERROR, MechanicPlugin.PLUGIN_ID,
        String.format("Couldn't load class from file: %s (%s)",
            file.getName(), file),
        t));
  }

  /**
   * @param clazz The Class defining the task to be instantiated.
   * @return An instance of Task as defined in the file.
   * @throws RuntimeException when any problem occurs loading the class
   */
  private Task createInstance(Class<?> clazz) {
    Task task = null;
    try {
      task = (Task) clazz.newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (LinkageError e) {
      throw new RuntimeException(e);
    }
    return task;
  }

  /**
   * Loads classes from arbitrary locations on disk.
   *
   * Extends ClassLoader in order to gain access to protected utility methods:
   * defineClass and resolveClass.
   */
  private static class FileClassLoader extends ClassLoader {

    public FileClassLoader(ClassLoader parent) {
      super(parent);
    }

    /**
     * Returns a Class for the supplied file.
     *
     * @throws ClassNotFoundException
     */
    public Class<?> classForFile(File file) throws ClassNotFoundException {
      try {
        String name = getClassName(file);

        // slurp up the bytes of the class file
        InputStream in = new FileInputStream(file.getCanonicalPath());
        byte[] bytes = Util.readAll(in);

        // define the new class using ClassLoaders support methods
        Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
        resolveClass(clazz); // because the docs say we have to

        return clazz;
      } catch (IOException e) {
        throw new ClassNotFoundException(
            "Couldn't load class for file: " + file);
      }
    }

    /**
     * Returns the java name including the package. Is assumed to be in the
     * package defined in EXT_PACKAGE.
     */
    private String getClassName(File file) {
      String name = file.getName();
      int end = name.indexOf(".class");
      return String.format("%s.%s", EXT_PACKAGE, name.substring(0, end));
    }
  }

  /**
   * Returns all elements joined by the local OS's path segment separator.
   */
  private static String createPath(String... elems) {
    return Util.join(File.separator, elems);
  }
}
