/*******************************************************************************
 * Copyright (C) 2014, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.samples;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.google.eclipse.mechanic.CompositeTask;
import com.google.eclipse.mechanic.IResourceTaskProvider;
import com.google.eclipse.mechanic.IResourceTaskReference;
import com.google.eclipse.mechanic.ListCollector;
import com.google.eclipse.mechanic.ResourceTaskScanner;
import com.google.eclipse.mechanic.TaskCollector;

/**
 * Processes files with the extension '.showview'. Each file contains
 * view ids which will be opened when run.
 *
 * When the task is executed, the view will either appear, or hide.
 */
public class ShowViewScanner extends ResourceTaskScanner {
  private static final Logger LOG = Logger.getLogger(ShowViewScanner.class.getName());

  @Override
  protected void scan(IResourceTaskProvider source, TaskCollector collector) {
    /**
     * Scan our source. Add a new Task for each EPF found.
     */
    ListCollector<IResourceTaskReference> taskCollector = ListCollector.create();
    source.collectTaskReferences(".showview", taskCollector);
    for (IResourceTaskReference taskRef : taskCollector.get()) {
      processTaskRef(collector, taskRef);
    }
  }

  public void processTaskRef(
      TaskCollector collector,
      IResourceTaskReference taskRef) {
    try {
      InputStream inputStream = taskRef.newInputStream();
      InputStreamReader reader = new InputStreamReader(inputStream, Charsets.UTF_8);
      String inputText = CharStreams.toString(reader);
      // This example doesn't deal with Windows newlines.
      List<String> list = Lists.newArrayList();
      for (String line : Splitter.on("\n").split(inputText)) {
        line = line.trim();
        if (line.startsWith("#") || line.length() == 0) {
          continue;
        }
        list.add(line);
      }
      collector.collect(new ShowViewTask(taskRef.getName(), list));
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "Can't process task " + taskRef, e);
    } catch (RuntimeException e) {
      LOG.log(Level.SEVERE, "Can't process task " + taskRef, e);
    }
  }

  public class ShowViewTask extends CompositeTask {

    private final String name;
    private final ImmutableList<String> list;

    public ShowViewTask(String name, List<String> list) {
      this.name = name;
      this.list = ImmutableList.copyOf(list);
    }

    @Override
    public String getId() {
      return ShowViewTask.class.getName() + ":" + name;
    }

    @Override
    public String getTitle() {
      return "Show views: " + name;
    }

    @Override
    public String getDescription() {
      return "Enables and disables views. More details.";
    }

    @Override
    public boolean evaluate() {
      if (!PlatformUI.isWorkbenchRunning()) {
        return true;
      }
      final IWorkbench workbench = PlatformUI.getWorkbench();
      if (workbench == null) {
        return true;
      }

      final Set<String> remaining = Sets.newHashSet(list);
      Display.getDefault().syncExec(new Runnable() {
        @Override
        public void run() {
          for (IWorkbenchWindow workbenchWindow : workbench.getWorkbenchWindows()) {
            for (IWorkbenchPage workbenchPage : workbenchWindow.getPages()) {
              for (IViewReference viewReference : workbenchPage.getViewReferences()) {
                remaining.remove(viewReference.getId());
              }
            }
          }
        }
      });
      return remaining.isEmpty();
    }

    @Override
    public void run() {
      Display.getDefault().syncExec(new Runnable() {
        @Override
        public void run() {
          IWorkbench workbench = PlatformUI.getWorkbench();
          IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
          IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
          for (String viewId : list) {
            try {
              activePage.showView(viewId);
            } catch (PartInitException e) {
              LOG.log(Level.SEVERE, "Can't open view " + viewId, e);
            }
          }
        }
      });
    }
  }
}
