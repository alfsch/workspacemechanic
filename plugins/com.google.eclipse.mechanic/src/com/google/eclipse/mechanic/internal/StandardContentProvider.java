package com.google.eclipse.mechanic.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public final class StandardContentProvider implements IUriContentProvider {

  public InputStream get(URI uri) throws IOException {
    return uri.toURL().openStream();
  }

  public void clear() {
    // Does nothing.
  }

}
