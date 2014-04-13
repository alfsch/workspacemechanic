package com.google.eclipse.mechanic;

/**
 * Interface for collecting elements.
 */
public interface ICollector<T> {
  /**
   * Add a element to the collector.
   */
  void collect(T element);
}
