/*******************************************************************************
 * Copyright (C) 2011, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.google.eclipse.mechanic.internal;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

import com.google.eclipse.mechanic.IMechanicService;
import com.google.eclipse.mechanic.IStatusChangeListener;
import com.google.eclipse.mechanic.MechanicStatus;
import com.google.eclipse.mechanic.RepairDecisionProvider;
import com.google.eclipse.mechanic.RepairManager;
import com.google.eclipse.mechanic.StatusChangedEvent;
import com.google.eclipse.mechanic.Task;
import com.google.eclipse.mechanic.tests.internal.RunAsJUnitTest;

/**
 * Tests for {@link StateSensitiveCache}.
 */
@RunAsJUnitTest
public class StateSensitiveCacheTest extends TestCase {

  /**
   * Contains some canned entries that prevent actually fetching content from the web.
   */
  static class TestInner extends ThreadsafeUriContentCache {

    private final Map<String, String> map = Util.newHashMap();
    // Number of cache requests.
    public int count;

    public TestInner() {
      super(0);
      map.put("http://www.google.com", "asdf");
      map.put("http://www.imdb.com", "qwerty");
    }

    @Override
    InputStream openStream(URI uri) throws IOException, MalformedURLException {
      count++;
      return new ByteArrayInputStream(map.get(uri.toASCIIString()).getBytes());
    }
  }

  static class TestService implements IMechanicService {
    public IStatusChangeListener listener;

    public void start() {
    }

    public void stop() {
    }

    public boolean isStopped() {
      return false;
    }

    public void addTaskStatusChangeListener(
        IStatusChangeListener statusChangeListener) {
      this.listener = statusChangeListener;
    }

    public void removeTaskStatusChangeListener(
        IStatusChangeListener statusChangeListener) {
    }

    public RepairManager getRepairManager(RepairDecisionProvider rdp) {
      return null;
    }

    public Collection<? extends Task> getAllKnownTasks() {
      return null;
    }

    public int getFailingItemCount() {
      return 0;
    }
  }

  public void testNPE() {
    try {
      new StateSensitiveCache(null);
      fail("npe expected");
    } catch(NullPointerException e) {
    }
  }

  public void testInitialize() {
    IMechanicService service = createMock(IMechanicService.class);
    service.addTaskStatusChangeListener((IStatusChangeListener) anyObject());
    expectLastCall();
    replay(service);
    StateSensitiveCache cache = new StateSensitiveCache(service);
    cache.initialize();
    verify(service);
  }


  public void testDispose() {
    IMechanicService service = createMock(IMechanicService.class);
    service.addTaskStatusChangeListener((IStatusChangeListener) anyObject());
    expectLastCall();

    service.removeTaskStatusChangeListener((IStatusChangeListener) anyObject());
    expectLastCall();
    replay(service);
    StateSensitiveCache cache = new StateSensitiveCache(service);
    cache.initialize();
    cache.dispose();
    verify(service);
  }

  public void testGet_cache() throws Exception {
    IMechanicService service = createMock(IMechanicService.class);
    TestInner inner = new TestInner();
    StateSensitiveCache cache = new StateSensitiveCache(service, inner);
    assertEquals(0, inner.count);
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, inner.count);
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, inner.count);
    cache.clear();
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(2, inner.count);
  }

  public void testGet_clear() throws Exception {
    TestService service = new TestService();
    TestInner inner = new TestInner();
    StateSensitiveCache cache = new StateSensitiveCache(service, inner);

    cache.initialize();

    assertEquals(0, inner.count);
    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, inner.count);

    // This status doesn't clear the cache.
    service.listener.statusChanged(new StatusChangedEvent(MechanicStatus.FAILED));

    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, inner.count);

    // This call will cause the cache to clear, and so, the
    // inner raw reading count will increase.
    service.listener.statusChanged(new StatusChangedEvent(MechanicStatus.UPDATING));

    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(2, inner.count);
  }

  private String read(InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    return reader.readLine();
  }
}
