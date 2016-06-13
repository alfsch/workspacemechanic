package com.google.eclipse.mechanic.internal;

import java.util.Comparator;

import com.google.eclipse.mechanic.Task;

/**
 * Comparator ordering Task's by their titles in lexicographical order
 * TODO(stevemash) update to use guava's Ordering when guava version is updated
 */
public class TaskByTitleComparator implements Comparator<Task> {

  private static final TaskByTitleComparator instance = new TaskByTitleComparator();
  
  public static Comparator<Task> getInstance() {
    return instance;
  }
  
  public int compare(Task a, Task b) {
    return a.getTitle().compareTo(b.getTitle());
  }
}
