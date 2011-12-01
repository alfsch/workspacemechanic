/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.util.Properties;

import junit.framework.TestCase;

import org.eclipse.core.variables.IValueVariable;

import com.google.eclipse.mechanic.tests.internal.RunAsPluginTest;

/**
 * Tests for {@link MechanicConfigurationVariableInitializer}.
 */
@RunAsPluginTest
public class MechanicConfigurationVariableInitializerTest extends TestCase {
  public void testSanity() {
    IValueVariable variable = new FakeValueVariable();
    final Properties properties = new Properties();
    properties.put("file.separator", "!");
    properties.put("osgi.install.area", "file:/path/to/eclipse/");

    new MechanicConfigurationVariableInitializer() {
      @Override
      protected Properties getProperties() {
        return properties;
      }
    }.initialize(variable);
    assertEquals("/path/to/eclipse!configuration!com.google.eclipse.mechanic",
        variable.getValue());
  }
}
