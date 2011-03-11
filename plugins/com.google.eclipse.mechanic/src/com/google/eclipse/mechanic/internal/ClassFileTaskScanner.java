/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.eclipse.mechanic.DirectoryIteratingTaskScanner;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.TaskCollector;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;
import com.google.eclipse.mechanic.plugin.core.MechanicPlugin;

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

  private final MechanicLog mechanicLog = MechanicLog.getDefault();

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
  protected void scan(IResourceTaskProvider taskSource, TaskCollector collector) {
    /*
     * List of all task classes. We populate this list will
     * loading and resolving classes. Later, once all classes have been
     * resolved, we'll create new instances of these classes and add
     * them to the supplied list of Tasks.
     */
    List<Class<?>> taskClasses = Util.newArrayList();

    // HACK: This isn't really where this logic belongs, but it's a start.
    if (!(taskSource instanceof FileTaskProvider)) {
      DEBUGLOG.log(Level.FINE, "Not loading class tasks from {0}", taskSource);
    }
    List<IResourceTaskReference> taskReferences = taskSource.getTaskReferences(EXT_PATH, ".class");
    if (taskReferences == null) {
      return;
    }
    for (IResourceTaskReference taskRef : taskReferences) {
      Class<?> clazz = null;
      try {

        /*
         * Load all classes we find. This enables a more full bodied
         * experience for the Task writer by providing support for
         * not just AutitItem classes, but any other class they throw into
         * the package directory, and any inner classes they've included
         * in their Tasks.
         */
        DEBUGLOG.log(Level.FINE, "Loading class from {0}", taskRef);

        // HACK: This isn't really where this logic belongs, but it's a start.
        clazz = loader.classForTaskRef(taskRef);

      /*
       * We're attempting to load a stranger, so we need to protect against
       * all possible Throwable thingies.
       */
      } catch (ClassNotFoundException e) {
        logExceptionLoadingTask(e, taskRef);
      } catch (RuntimeException e) {
        logExceptionLoadingTask(e, taskRef);
      } catch (Error e) {
        logExceptionLoadingTask(e, taskRef);
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
        mechanicLog.logError(e, "Couldn't instantiate class: %s", clazz);
      }
    }
  }

  private void logExceptionLoadingTask(Throwable t, IResourceTaskReference taskRef) {
    mechanicLog.logError(t, "Couldn't load class from: %s (%s)", taskRef.getName(), taskRef);
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
    public Class<?> classForTaskRef(IResourceTaskReference taskRef) throws ClassNotFoundException {
      try {
        String name = getClassName(taskRef);

        // slurp up the bytes of the class file
        InputStream in = taskRef.newInputStream();
        byte[] bytes = Util.readAll(in);

        // define the new class using ClassLoaders support methods
        Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
        resolveClass(clazz); // because the docs say we have to

        return clazz;
      } catch (IOException e) {
        throw new ClassNotFoundException(
            "Couldn't load class for " + taskRef);
      }
    }

    /**
     * Returns the java name including the package. Is assumed to be in the
     * package defined in EXT_PACKAGE.
     */
    private String getClassName(IResourceTaskReference taskRef) {
      String name = taskRef.getName();
      int end = name.indexOf(".class");
      return String.format("%s.%s", EXT_PACKAGE, name.substring(0, end));
    }
  }
}
