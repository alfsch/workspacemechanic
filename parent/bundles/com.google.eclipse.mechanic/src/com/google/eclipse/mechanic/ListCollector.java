package com.google.eclipse.mechanic;

import java.util.List;

import com.google.common.collect.Lists;

public class ListCollector<T> implements ICollector<T> {
  private final List<T> list = Lists.newArrayList();

  public static <T> ListCollector<T> create() {
    return new ListCollector<T>();
  }

  public void collect(T element) {
    list.add(element);
  }

  public List<T> get() {
    return list;
  }
}
