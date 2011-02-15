/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.ui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * This isn't really a test because it doesn't run like a test.
 */
@RunAsJUnitTest
public class MechanicPopupTest {
  public boolean opened = false;
  public boolean correctConfigurationIssuesCalled = false;
  public boolean doNotShowPopupCalled = false;
  public boolean closed = false;

  class PopupUnderTest extends MechanicPopup {
    public PopupUnderTest(Display display) {
      super(display);
    }

    @Override
    public void open() {
      super.open();
      opened = true;
    }
  
    @Override
    public void correctConfigurationIssues() {
      correctConfigurationIssuesCalled = true;
    }
  
    @Override
    public void doNotShowPopup() {
      doNotShowPopupCalled = true;
    }
  
    @Override
    public void close() {
      super.close();
      closed = true;
    }
  }

  public void testSanity() throws Exception {
    Display display = new Display();
    PopupUnderTest popup = new PopupUnderTest(display);
    popup.setDisplayTimeMillis(0);
    popup.open();

    Shell shell = popup.getShell();
    File file = copyWidgetToFile(shell);
    popup.close();
  }

  private static final File tmpdir;
  static {
    File x = new File(System.getenv("TEMP"));
    if (x.exists()) {
      tmpdir = x;
    } else {
      tmpdir = new File("/tmp");
    }
  }

  public File copyWidgetToFile(Control control) throws IOException {
    File file = tmpdir.createTempFile("MechanicalPopupTest", "jpg");
    file.deleteOnExit();
    Point size = control.getSize();
    GC gc = new GC(control);
    Image image = null;
    try {
      image = new Image(control.getDisplay(), size.x, size.y);
      gc.copyArea(image, 0, 0);
      ImageLoader loader = new ImageLoader();
      loader.data = new ImageData[] {image.getImageData()};
      loader.save(file.getPath(), SWT.IMAGE_JPEG);
    } finally {
      gc.dispose();
      if (image != null) {
        image.dispose();
      }
    }
    return file;
  }
}
