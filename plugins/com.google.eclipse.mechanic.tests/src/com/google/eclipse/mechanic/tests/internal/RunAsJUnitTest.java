/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.tests.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a test as one that can be run as a JUnit Test, without plug-in test
 * infrastructure.
 * 
 * @author konigsberg@google.com (Robert Konigsberg)
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RunAsJUnitTest {
}
