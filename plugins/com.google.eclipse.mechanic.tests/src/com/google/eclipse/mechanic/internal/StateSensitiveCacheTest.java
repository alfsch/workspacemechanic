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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collection;

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

  private final TestUriContentProvider delegate = new TestUriContentProvider();

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
      new StateSensitiveCache(null, null);
      fail("npe expected");
    } catch(NullPointerException e) {
    }
    try {
      new StateSensitiveCache(createMock(IMechanicService.class), null);
      fail("npe expected");
    } catch(NullPointerException e) {
    }
    try {
      new StateSensitiveCache(null, delegate);
      fail("npe expected");
    } catch(NullPointerException e) {
    }
  }

  public void testInitialize() {
    IMechanicService service = createMock(IMechanicService.class);
    service.addTaskStatusChangeListener((IStatusChangeListener) anyObject());
    expectLastCall();
    replay(service);
    StateSensitiveCache cache = new StateSensitiveCache(service, delegate);
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
    StateSensitiveCache cache = new StateSensitiveCache(service, delegate);
    cache.initialize();
    cache.dispose();
    verify(service);
  }

  public void testGet_clear() throws Exception {
    TestService service = new TestService();
    StateSensitiveCache cache = new StateSensitiveCache(service, delegate);

    cache.initialize();

    assertEquals(0, delegate.fetchCount());
    assertEquals(0, delegate.clearCount());

    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(1, delegate.fetchCount());
    assertEquals(0, delegate.clearCount());

    // This status doesn't clear the cache.
    service.listener.statusChanged(new StatusChangedEvent(MechanicStatus.FAILED));

    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(2, delegate.fetchCount());
    assertEquals(0, delegate.clearCount());

    // This call will cause the cache to clear, and so, the
    // inner raw reading count will increase.
    service.listener.statusChanged(new StatusChangedEvent(MechanicStatus.UPDATING));

    assertEquals("asdf", read(cache.get(new URI("http://www.google.com"))));
    assertEquals(3, delegate.fetchCount());
    assertEquals(1, delegate.clearCount());
  }

  private String read(InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    return reader.readLine();
  }
}
