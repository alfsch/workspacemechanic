/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.plugin.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Business end of the popup.
 */
public class MechanicPopup extends AbstractPopup {

  private Font boldFont;

  public MechanicPopup(Display display) {
    super(display);
  }

  public MechanicPopup(Display display, int type) {
    super(display, type);
  }

  @Override
  protected void createTitleBar(Composite parent) {
    GridLayout layout = new GridLayout(2, false);
    parent.setLayout(layout);

    Label label = new Label(parent, SWT.WRAP);
    Font font = parent.getFont();
    this.boldFont = createBoldFont(font);
    label.setFont(boldFont);
    label.setText("Workspace Mechanic");
    GridData gridData = new GridData();
    gridData.horizontalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    label.setLayoutData(gridData);

    // Yes, I'm using an 'x' as a close hint.
    // TODO(konigsberg): replace with image.
    // TODO(konigsberg) Move to AbstractPopup.
    label = new Label(parent, SWT.WRAP);
    label.setText("x");
    gridData = new GridData(GridData.END, GridData.CENTER, false, false);
    label.setLayoutData(gridData);
    label.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseUp(MouseEvent e) {
        close();
      }
    });
  }

  private Font createBoldFont(Font font) {
    FontData[] fontDatas = font.getFontData();
    for (FontData fd: fontDatas) {
      fd.setStyle(SWT.BOLD);
    }
    return new Font(font.getDevice(), fontDatas);
  }

  @Override
  public void createContents(Composite parent) {
    Label label = new Label(parent, SWT.WRAP);
    label.setText(
        "The Workspace Mechanic found\nissues that need your attention.");

    createHyperlink(parent, "View and correct configuration issues", new Runnable() {
      public void run() {
        close();
        correctConfigurationIssues();
      }
    });

    createHyperlink(parent, "Disable this popup", new Runnable() {
      public void run() {
        close();
        doNotShowPopup();
      }
    });
  }

  private Hyperlink createHyperlink(Composite parent, String text,
      final Runnable runnable) {
    Hyperlink hyperlink = new Hyperlink(parent, 0) {
      @Override
      protected void paint(PaintEvent e) {
        // From half of Hyperlink.paint, this removes the part that
        // draws the focus border.
        GC gc = e.gc;
        Rectangle clientArea = getClientArea();
        if (clientArea.width == 0 || clientArea.height == 0) {
          return;
        }
        paintHyperlink(gc);
      }
    };
    hyperlink.setText(text);
    hyperlink.setUnderlined(true);
    hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
      @Override
      public void linkActivated(HyperlinkEvent e) {
        getDisplay().syncExec(runnable);
      }
    });
    return hyperlink;
  }

  @Override
  public void dispose() {
    if (boldFont != null && !boldFont.isDisposed()) {
      boldFont.dispose();
    }
    super.dispose();
  }

  /**
   * Implement in subclass to correct configuration issues.
   */
  public void correctConfigurationIssues() {
  }

  /**
   * Implement in subclass to hide popup
   */
  public void doNotShowPopup() {
  }
}
