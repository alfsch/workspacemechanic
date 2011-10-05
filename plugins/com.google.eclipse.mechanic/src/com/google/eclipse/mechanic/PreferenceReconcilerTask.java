/*******************************************************************************
 * Copyright (C) 2007, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic;

import static java.lang.String.format;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.eclipse.mechanic.internal.Util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

import java.io.File;
import java.util.List;

/**
 * Provides a base class for Tasks that reconcile values in the
 * underlying preferences system.
 *
 * <p>
 *
 * Example:
 * <pre>
 * public class FancyTask extends PreferenceReconcilerTask {
 *
 *   public FancyTask() {
 *     addReconciler(createReconciler(
 *         "/instance/com.google.eclipse.mechanic/ham=fancy"));
 *   }
 *
 *   public String getDescription() {
 *     return "Reconciles HAM";
 *   }
 *
 *   public String getTitle() {
 *     return getDescription();
 *   }
 * }
 * </pre>
 *
 *
 * TODO(smckay): cooperate with core mechanic to wake up on changes
 * to preferences we're configured to analyze.
 *
 * @author smckay@google.com (Steve McKay)
 */
public abstract class PreferenceReconcilerTask extends CompositeTask {

  private final IEclipsePreferences prefsRoot;
  private final List<Reconciler> reconcilers = Lists.newArrayList();

  /**
   * Constructs a new instance with the supplied instance
   * of {@link IEclipsePreferences}.
   */
  public PreferenceReconcilerTask(IEclipsePreferences root) {
    this.prefsRoot = root;
  }

  /**
   * Constructs a new instance, providing a default implementation of
   * {@link IEclipsePreferences} as provided
   * by {@code Platform.getPreferencesService().getRootNode()}.
   */
  public PreferenceReconcilerTask() {
    this(Platform.getPreferencesService().getRootNode());
  }

  /**
   * Adds a new Reconciler to the list of reconcilers to be applied
   * by this task.
   */
  public void addReconciler(Reconciler r) {
    reconcilers.add(r);
  }

  /**
   * Fails if any of Reconcilers need reconciling.
   */
  public boolean evaluate() {
    for (Reconciler r : reconcilers) {
      if (!r.isReconciled()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Reconciles all un-reconciled tasks.
   */
  public void run() {
    for (Reconciler r : reconcilers) {

      // We only bother to reconcile un-reconciled prefs.
      if (!r.isReconciled()) {
        r.reconcile();
      }
    }
  }

  /**
   * Creates a Reconciler given an opaque preference string as exported
   * by Eclipse in an EPF file.
   */
  public Reconciler createReconciler(String pref) {
    return createReconciler(parsePreferenceString(pref));
  }

  /**
   * Returns a new Reconciler instance that will reconcile prefs that do
   * not match the value exactly.
   */
  public Reconciler createReconciler(String path, String key,
      String value) {
    return createReconciler(new ImmutablePreference(path, key, value));
  }

  /**
   * Returns a new Reconciler instance that will reconcile prefs that do
   * not match the value exactly.
   */
  public Reconciler createReconciler(Preference pref) {
    return createReconciler(pref,
        new EqualsMatcher(pref),
        new SimpleResolver(pref));
  }

  /**
   * Returns a new Reconciler instance that will reconcile prefs that do
   * not match the value exactly.
   */
  public Reconciler createReconciler(Preference pref, Matcher matcher,
      Resolver resolver) {
    return new CompositeReconciler(prefsRoot, pref, matcher, resolver);
  }

  /**
   * Parses the supplied string preference. Throws IllegalArgumentExcepiton
   * when and if a parse error occurs.
   */
  static final Preference parsePreferenceString(String pref) {

    Preconditions.checkNotNull(pref, "'pref' cannot be null.");
    Preconditions.checkArgument(pref.length() > 0, "'pref' cannot be empty string.");

    int eqi = pref.indexOf("=");
    Preconditions.checkArgument(eqi != -1, format("'pref' must contain an equals sign. Bad val: '%s'", pref));

    // assumes a value
    String value = pref.length() > eqi + 1
        ? Util.unquote(pref.substring(eqi + 1))
        : "";

    // makes a lot of assumptions about the preference values
    String id = pref.substring(0, eqi);
    int sli = id.lastIndexOf("/");

    Preconditions.checkArgument(sli != -1, format("'pref' must contain a slash in the identifier portion "
    + "of the preference. Bad val: '%s'", id));
    String path = id.substring(0, sli);

    Preconditions.checkArgument(id.length() > sli + 1, format("'pref' must contain a name after slash in the "
    + "identifier portion of the preference. Bad val: '%s'", id));
    String key = id.substring(sli + 1);

    return new ImmutablePreference(path, key, value);
  }

  /**
   * Groups together the Path, Key, and Value parts of a preference.
   */
  public static interface Preference {

    /**
     * Returns the path value.
     */
    public String getPath();

    /**
     * Returns the key value indicating the preference field identified by
     * this Preference instance.
     */
    public String getKey();

    /**
     * Returns the actual preference value.
     */
    public String getValue();

  }

  /**
   * Reconciles declarations of preferences with the actual values stored
   * in the prefs system.
   */
  public static interface Reconciler {

    /**
     * Returns true if the preference contains the "correct" value.
     */
    public boolean isReconciled();

    /**
     * Updates the preference so that isReconciled will return true.
     */
    public void reconcile();
  }

  /**
   * Provides an interface for matching preference values.
   */
  public static interface Matcher {
    public boolean matches(String subject);
  }

  /**
   * Provides an interface for resolving (i.e. correcting) mis-matched values.
   */
  public interface Resolver {

    /**
     * Resolves a value.
     * @param subject the existing value. Could be null.
     */
    public String resolve(String subject);
  }

  /**
   * Implements Preference interface with out any fancy mojo.
   */
  public static class ImmutablePreference implements Preference {

    private final String path;
    private final String key;
    private final String value;

    public ImmutablePreference(String path, String key, String value) {
      this.path = path;
      this.key = key;
      this.value = value;
    }

    public String getPath() {
      return path;
    }

    public String getKey() {
      return key;
    }

    public String getValue() {
      return value;
    }
  }

  /**
   * Reconciles preferences using a Preference object and a Matcher object.
   */
  public static class CompositeReconciler implements Reconciler {

    private final IEclipsePreferences prefsRoot;
    private final Preference pref;
    private final Matcher matcher;
    private final Resolver resolver;
    private final IEclipsePreferences node;

    public CompositeReconciler(IEclipsePreferences root,
        Preference pref, Matcher matcher, Resolver resolver) {

      this.prefsRoot = root;
      this.pref = pref;
      this.matcher = matcher;
      this.resolver = resolver;
      this.node = getPrefNode(pref.getPath());
    }

    public boolean isReconciled() {
      String value = node.get(pref.getKey(), null);
      return matcher.matches(value);
    }

    public void reconcile() {
      node.put(pref.getKey(), resolver.resolve(node.get(pref.getKey(), null)));
      try {
        node.flush();
      } catch (BackingStoreException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Returns a reference to the preference node specified by the
     * supplied path.
     */
    private IEclipsePreferences getPrefNode(String path) {
      return (IEclipsePreferences) prefsRoot.node(path);
    }
  }

  /**
   * Matches values when they subject values return true from "equals".
   */
  public static class EqualsMatcher implements Matcher {

    private final Preference pref;

    public EqualsMatcher(Preference pref) {
      this.pref = pref;
    }

    public boolean matches(String subject) {
      return pref.getValue().equals(subject);
    }
  }

  /**
   * Matches when the subject contains the supplied value.
   */
  public static class ContainsMatcher implements Matcher {

    private final String value;

    public ContainsMatcher(String value) {
      this.value = value;
    }

    public boolean matches(String subject) {
      return subject != null && subject.contains(this.value);
    }
  }

  /**
   * Resolves to the value of the preference supplied in the constructor.
   */
  public static class SimpleResolver implements Resolver {

    private final Preference pref;

    public SimpleResolver(Preference pref) {
      this.pref = pref;
    }

    public String resolve(String subject) {
      return pref.getValue();
    }
  }

  /**
   * Appends the value from the preference supplied in the constructor with
   * the existing preference value using an arbitrary delimiter.
   */
  public static class ListAppendResolver implements Resolver {

    private final String delim;
    private final Preference pref;

    public ListAppendResolver(String delim, Preference pref) {
      this.delim = delim;
      this.pref = pref;
    }

    /**
     * Appends the value from the preference to the supplied value.
     */
    public String resolve(String subject) {
      String prepend = (subject == null) ? "" : (subject + delim);
      return prepend + pref.getValue();
    }
  }

  /**
   * Appends the value from the preference supplied in the constructor with
   * the existing preference value using {@code File#separator}.
   */
  public static class PathAppendResolver extends ListAppendResolver {

    public PathAppendResolver(Preference pref) {
      super(File.pathSeparator, pref);
    }
  }

  /**
   * Replaces matching strings in the preference with the supplied value.
   */
  public static class StringReplaceResolver implements Resolver {

    private final String regex;
    private final String replace;

    public StringReplaceResolver(String regex, String replace) {
      this.regex = regex;
      this.replace = replace;
    }

    public String resolve(String subject) {
      if (subject != null) {
        return subject.replaceAll(regex, replace);
      }
      return null;
    }
  }
  
}
