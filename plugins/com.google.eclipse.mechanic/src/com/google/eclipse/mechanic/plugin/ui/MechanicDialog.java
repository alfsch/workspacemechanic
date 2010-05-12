/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.plugin.ui;

import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.RepairDecisionProvider.Decision;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dialog box that displays a list of available Tasks. Users user this
 * dialog box to select Tasks to execute.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class MechanicDialog extends TitleAreaDialog {

  // action choice display names
  private static final String YES = "Fix Now";
  private static final String NO = "Fix Later";
  private static final String NEVER = "Never Fix"; 

  
  private final List<Task> items;
  private final Map<Task, Decision> userTaskChoices;

  public MechanicDialog(Shell parent, List<Task> items) {
    super(parent);
    this.items = items;
    this.userTaskChoices = new HashMap<Task, Decision>(items.size());

    // sets the default value for each choice the user must make
    for (Task item : items) {
      userTaskChoices.put(item, Decision.YES);
    }

    // make the dialog resizeable
    setShellStyle(getShellStyle() | SWT.RESIZE);
  }

  /**
   * @return true if user clicked OK button.
   */
  public boolean isOkay() {
    return Window.OK == getReturnCode();
  }

  /**
   * Returns map of {@link Task} to the user's choice for that Task.
   */
  public Map<Task, Decision> getUserChoices() {
    return Collections.unmodifiableMap(userTaskChoices);
  }

  @Override 
  public void create() {
    super.create();
    setTitle("Workspace Mechanic");
    setMessage("Changes suggested for your Eclipse installation:");
  }

  /**
   * Here we fill the center area of the dialog
   */
  @Override 
  protected Control createDialogArea(Composite parent) {
    // call through to our 
    return createForm(parent);
  }

  /**
   * Initilize the buttons on the bottom dialog
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID,
        IDialogConstants.OK_LABEL, true);
    createButton(parent, IDialogConstants.CANCEL_ID,
        IDialogConstants.CANCEL_LABEL, false);
  }

  @Override
  protected Point getInitialSize() {
    return new Point(650, 500);
  }

  /**
   * Add a form to the supplied Composite.
   */
  private Control createForm(Composite parent) {

    final FormToolkit toolkit = new FormToolkit(parent.getDisplay());
    final ScrolledForm form = toolkit.createScrolledForm(parent);

    /*
     * For the life of me I can't understand why I have to supply
     * a GridData instance to the form object in order to get the form
     * to fill the dialog area.
     * 
     * BTW, I only found this out through trial and error.
     */
    form.setLayoutData(new GridData(GridData.FILL_BOTH));

    TableWrapLayout layout = new TableWrapLayout();
    layout.numColumns = 2;
    layout.horizontalSpacing = 15;
    layout.verticalSpacing = 10;

    form.getBody().setLayout(layout);
    form.getBody().setLayoutData(new TableWrapData(
        TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB, 1, 3));

    for (Task item : items) {

      // add an expandable description of the task, with a pretty title
      ExpandableComposite ec = toolkit.createExpandableComposite(form.getBody(),
          ExpandableComposite.TREE_NODE | ExpandableComposite.CLIENT_INDENT);
      ec.setText(item.getTitle());
      Label label = toolkit.createLabel(ec, item.getDescription(), SWT.WRAP);
      ec.setClient(label);
      ec.addExpansionListener(new ExpansionAdapter() {
        @Override 
        public void expansionStateChanged(ExpansionEvent e) {
          form.reflow(true);
        }
      });
      ec.setExpanded(true);
      ec.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

      // add a combo box allowing the user to select the repair action to take
      createDecisionCombo(form.getBody(), item);
    }

    return parent;
  }

  /**
   * Creates a new combo box, initilizes the enty values, and configures
   * it with a listener capable of updating the right entry in our
   * map of item->decision.
   */
  private Combo createDecisionCombo(Composite parent, Task item) {
    Combo combo = new Combo(parent, SWT.READ_ONLY);
    combo.add(YES);
    combo.add(NO);
    combo.add(NEVER);
    combo.select(0);

    combo.addSelectionListener(new ComboListener(item));
    return combo;
  }

  /**
   * Listens to combo box "selection" events. Updates the entry in the
   * outer classes map of item->decision.
   */
  private class ComboListener extends SelectionAdapter {
    
    private final Task item;

    public ComboListener(Task item) {
      this.item = item;
    }

    @Override 
    public void widgetSelected(SelectionEvent e) {
      Combo combo = (Combo) e.getSource();
      int index = combo.getSelectionIndex();
      Decision choice = Decision.valueOf(index);
      userTaskChoices.put(item, choice);
    }
  }
}
