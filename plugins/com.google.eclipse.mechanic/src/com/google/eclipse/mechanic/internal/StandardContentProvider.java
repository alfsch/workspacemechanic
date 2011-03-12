package com.google.eclipse.mechanic.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLConnection;

public final class StandardContentProvider implements IUriContentProvider {

  public InputStream get(URI uri) throws IOException {
    return uri.toURL().openStream();
  }

  public long lastModifiedTime(URI uri) throws IOException {
    URLConnection connection = uri.toURL().openConnection();

    // Using HEAD for Http connections.
    if (connection instanceof HttpURLConnection) {
      ((HttpURLConnection) connection).setRequestMethod("HEAD");
    }

    connection.connect();
    return connection.getLastModified();
  }

  public void clear() {
    // Does nothing.
  }
}
