/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

/**
 * An enum that describes a task's policy for overwriting existing 
 * preferences.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public enum TaskType {
  /**
   * Indicates the task should only overwrite preferences if the file's 
   * modification date is earlier than the changes.
   */
  LASTMOD,
  
  /**
   * Indicates the task should overwrite any preferences that are different 
   * from the values listed the one written here.
   */
  RECONCILE
}