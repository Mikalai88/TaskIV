package com.port.service;

import com.port.entity.Dock;
import com.port.entity.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
  private final List<Dock> docks;
  private final Lock lock = new ReentrantLock();

  public Port(int dockCount, int warehouseCapacity) {
    docks = new ArrayList<>();
    Warehouse warehouse = new Warehouse(warehouseCapacity);
    for (int i = 0; i < dockCount; i++) {
      docks.add(new Dock(i, warehouse));
    }
  }

  public Dock requestDock() {
    lock.lock();
    try {
      for (Dock dock : docks) {
        if (dock.getWarehouse() != null) {
          return dock;
        }
      }
    } finally {
      lock.unlock();
    }
    return null;
  }

  public void releaseDock(Dock dock) {
    // no-op
  }
}