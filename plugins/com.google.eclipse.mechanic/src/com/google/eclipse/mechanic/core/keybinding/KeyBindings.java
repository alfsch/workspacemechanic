/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.core.keybinding;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.eclipse.mechanic.core.keybinding.KbaChangeSet.Action;
import com.google.eclipse.mechanic.internal.Util;
import com.google.eclipse.mechanic.plugin.core.MechanicLog;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeySequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Model for keyboard binding configuration.
 *
 * @author zorzella@google.com
 * @author konigsberg@google.com
 */
class KeyBindings {

  /*
   * If you're trying to interpret key bindings, this comment block will be useful.
   *
   * There are two kinds of bindings. USER and SYSTEM. You'll see we've split out user
   * and system bindings in the model implementation.
   *
   * One of the tricks of managing keyboard bindings is that of removing a system binding. To
   * remove a system binding from scheme+platform+context+triggerSequence to command+params,
   * we create a 'user' binding from scheme+platform+context+triggerSequence to a null command.
   * The system binding still exists, we've just created a user binding that overrides it.
   *
   * This has some implications for both adding and removing bindings, discussed further, below.
   */

  final MechanicLog log;
  private final List<Binding> userBindings;
  private final List<Binding> systemBindings;
  // At the moment, at least, the maps start drifting away from the lists as
  // the lists are modified
  final Multimap<KbaChangeSetQualifier, Binding> userBindingsMap;
  final Multimap<KbaChangeSetQualifier, Binding> systemBindingsMap;
  
  final List<Binding> addedBindings = Lists.newArrayList();
  final List<Binding> removedBindings = Lists.newArrayList();

  /**
   * Creates a new instance from a defined set of bindings.
   */
  public KeyBindings(Binding[] bindings) {
    this(MechanicLog.getDefault(), bindings);
  }
  
  KeyBindings(MechanicLog log, Binding[] bindings) {
    this.log = log;
    List<Binding> ub = new ArrayList<Binding>();
    List<Binding> sb = new ArrayList<Binding>();

    for (Binding binding : bindings) {
      if (binding.getType() == Binding.USER) {
        ub.add(binding);
      } else if (binding.getType() == Binding.SYSTEM) {
        sb.add(binding);
      } else {
        throw new UnsupportedOperationException("Unexpected binding type: " + binding.getType());
      }
    }
    this.userBindings = ub;
    this.systemBindings = Collections.unmodifiableList(sb);
    this.userBindingsMap = buildQualifierToBindingMap(userBindings);
    this.systemBindingsMap = buildQualifierToBindingMap(systemBindings);
  }

  static Multimap<KbaChangeSetQualifier,Binding> buildQualifierToBindingMap(List<Binding> bindings) {
    Multimap<KbaChangeSetQualifier,Binding> result = ArrayListMultimap.create();
    for (Binding binding : bindings) {
      result.put(
          qualifierForBinding(binding, Action.ADD),
          binding);
    }
    return result;
  }

  static KbaChangeSetQualifier qualifierForBinding(Binding binding, Action action) {
    return new KbaChangeSetQualifier(
        binding.getSchemeId(),
        binding.getPlatform(),
        binding.getContextId(),
        action.toString());
  }

  public boolean isDirty() {
    return addedBindings.size() > 0 || removedBindings.size() > 0;
  }
  
  /**
   * Bind a scheme / platform / context / trigger sequence to a command.
   */
  public void addIfNotPresent(
      Scheme scheme,
      String platform,
      String contextId,
      KeySequence triggerSequence,
      ParameterizedCommand command) {
    Map<String,String> params = commandParamMap(command);
    Binding binding = find(scheme, platform, triggerSequence, command.getId(), params);
    // If no binding exists, create the user binding, add it and return true.
    if (binding == null) {
      Binding bindingToAdd = createBinding(scheme, platform, contextId, triggerSequence, command);
      addUserBinding(bindingToAdd);
      addedBindings.add(bindingToAdd);
      return;
    }

    /*
     * If a system binding exists for this scheme / sequence, find out if there's a
     * user binding hiding it, and if so remove it.
     */
    if ((binding.getType() == Binding.SYSTEM)) {
      // Finding a user binding to a null command.
      // ZORZELLA: do we even need to supply params?
      Binding userBinding = find(scheme, platform, triggerSequence, null, params, userBindings);
      if (userBinding != null) {
        userBindings.remove(userBinding);
        return;
      }
    }
    return;
  }

  private static final Map<String, String> EMPTY_MAP = Collections.<String,String>emptyMap();

  static Map<String,String> commandParamMap(ParameterizedCommand command) {
    if (command == null) {
      return EMPTY_MAP;
    }
    @SuppressWarnings("unchecked")
    Map<String,String> result = ImmutableMap.copyOf(command.getParameterMap());
    return result == null ? EMPTY_MAP : result;
  }

  /**
   * Remove a binding.
   */
  public void removeBindingIfPresent(
      Scheme scheme,
      String platform,
      String contextId,
      KeySequence triggerSequence,
      Command command,
      Map<String, String> params) {

    Binding binding = find(scheme, platform, triggerSequence, command.getId(), params, userBindings);

    if (binding != null) {
      userBindings.remove(binding);
      return;
    }

    binding = find(scheme, platform, triggerSequence, command.getId(), params, systemBindings);
    if (binding != null) {
      if (find(scheme, platform, triggerSequence, null, params, userBindings) == null) {
        Binding bindingToRemoveWithNullCommand = createBinding(scheme, platform, contextId, triggerSequence, null);
        // Removing a system binding means creating a weird system binding with
        // a null command
        addUserBinding(bindingToRemoveWithNullCommand);
        return;
      }
    }
    return;
  }

  private void addUserBinding(Binding binding) {
    if (binding.getType() != Binding.USER) {
      throw new IllegalArgumentException("Can only accept user bindings.");
    }
    userBindings.add(binding);
  }

  private Binding find(
      Scheme scheme,
      String platform,
      KeySequence triggerSequence,
      String cid,
      Map<String, String> params) {
    Binding userBinding = find(scheme, platform, triggerSequence, cid, params, userBindings);
    if (userBinding != null) {
      return userBinding;
    }
    return find(scheme, platform, triggerSequence, cid, params, systemBindings);
  }

  /**
   * Finds a binding in {@code list} that matches the given
   * {@code triggerSequence}, {@code scheme} and {@code cid}. If not found,
   * return {@code null}.
   */
  private static Binding find(
      Scheme scheme,
      String platform,
      KeySequence triggerSequence,
      String cid,
      Map<String,String> params,
      List<Binding> list) {
    for (Binding binding : list) {
      if (binding.getSchemeId().equals(scheme.getId())
        && (binding.getTriggerSequence().equals(triggerSequence))
        && Util.equals(binding.getPlatform(), platform)) {
          ParameterizedCommand param = binding.getParameterizedCommand();
          if (param == null) {
            if (cid == null) {
              return binding;
            }
            continue;
          }
          Command command = param.getCommand();
          if (cid == null) {
            return command == null ? binding : null;
          }
          if (cid.equals(command.getId())) {
            Map<String,String> temp = commandParamMap(param);
            if (equalMaps(temp, params)) {
              return binding;
            }
          }
        }
    }
    return null;
  }

  /**
   * compares first to second, where an empty map substitues for null.
   */
  private static boolean equalMaps(
      Map<String,String> first,
      Map<String,String> second) {
    if (first == null) {
      first = Collections.emptyMap();
    }
    if (second == null) {
      second = Collections.emptyMap();
    }
    return second.equals(first);
  }

  private Binding createBinding(
      Scheme scheme,
      String platform,
      String contextId,
      KeySequence triggerSequence,
      ParameterizedCommand parameterizedCommand) {

    Binding newBinding =
        new KeyBinding(triggerSequence, parameterizedCommand, scheme.getId(),
            contextId, null, platform, null, Binding.USER);

    return newBinding;
  }

 /**
   * Provides the full set of bindings as an array, to be supplied by the binding service.
   */
  public Binding[] toArray() {
    Binding[] result = new Binding[userBindings.size() + systemBindings.size()];
    for (int i = 0; i < userBindings.size(); i++) {
      result[i] = userBindings.get(i);
    }
    int offset = userBindings.size();
    for (int i = 0; i < systemBindings.size(); i++) {
      result[i + offset] = systemBindings.get(i);
    }
    return result;
  }
}