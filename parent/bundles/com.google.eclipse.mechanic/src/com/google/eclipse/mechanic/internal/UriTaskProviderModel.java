/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import java.net.URI;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Model for URI-based task provider.
 */
public class UriTaskProviderModel {

  public static class Metadata {
    private final String name;
    private final String description;
    private final String contact;
    public Metadata(String name, String description, String contact) {
      this.description = Preconditions.checkNotNull(description, "description is missing");
      this.name = name;
      this.contact = contact;
    }

    public String getDescription() {
      return description;
    }
    public String getName() {
      return name;
    }
    public String getContact() {
      return contact;
    }

    @Override
    public String toString() {
      return "Metadata [name=" + name + ", description=" + description
          + ", contact=" + contact + "]";
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name, contact, description);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof Metadata)) {
        return false;
      }
      Metadata that = (Metadata) obj;
      return Objects.equal(this.name,  that.name) &&
          Objects.equal(this.description,  that.description) &&
          Objects.equal(this.contact,  that.contact);
    }
  }

  private final Metadata metadata;
  private final List<URI> tasks;

  public UriTaskProviderModel(Metadata metadata, List<URI> tasks) {
    this.metadata = metadata;
    this.tasks = tasks;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public List<URI> getTasks() {
    return tasks;
  }

  @Override
  public String toString() {
    return "UriTaskModel [metadata=" + metadata + ", tasks=" + tasks + "]";
  }

  @Override
  public int hashCode() {
    return metadata.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof UriTaskProviderModel)) {
      return false;
    }
    UriTaskProviderModel that = (UriTaskProviderModel) obj;
    return this.metadata.equals(that.metadata) && this.tasks.equals(that.tasks);
  }
}
