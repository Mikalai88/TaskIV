package com.mikalai.port.service;

import com.mikalai.port.entity.Dock;
import com.mikalai.port.entity.Warehouse;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
  private final Deque<Dock> docks;
  private final Lock lock = new ReentrantLock();

  private Port(int dockCount) {
    docks = new ArrayDeque<>();
    Warehouse warehouse = Warehouse.getInstance();
    for (int i = 0; i < dockCount; i++) {
      docks.add(new Dock(warehouse));
    }
  }

  private static class PortHolder {
    private static final Port INSTANCE = new Port(5);
  }

  public static Port getInstance() {
    return PortHolder.INSTANCE;
  }

  public Dock requestDock() {
    lock.lock();
    try {
      if (!docks.isEmpty()) {
        return docks.poll();
      }
    } finally {
      lock.unlock();
    }
    return null;
  }

  public void releaseDock(Dock dock) {
    lock.lock();
    try {
      docks.offer(dock);
    } finally {
      lock.unlock();
    }
  }
}