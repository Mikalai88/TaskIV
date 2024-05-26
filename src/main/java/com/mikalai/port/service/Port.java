package com.mikalai.port.service;

import com.mikalai.port.entity.Dock;
import com.mikalai.port.entity.Warehouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
  private static final Logger logger = LogManager.getLogger(Port.class);
  private static volatile Port instance;
  private static final Lock instanceLock = new ReentrantLock();

  private final Queue<Dock> docks;
  private final Lock lock = new ReentrantLock();

  private Port(int dockCount) {
    docks = new ArrayDeque<>();
    Warehouse warehouse = Warehouse.getInstance(); // Используем singleton экземпляр Warehouse
    for (int i = 0; i < dockCount; i++) {
      docks.add(new Dock(warehouse));
    }
    logger.info("Port created with {} docks", dockCount);
  }

  public static Port getInstance(int dockCount) {
    if (instance == null) {
      instanceLock.lock();
      try {
        if (instance == null) {
          instance = new Port(dockCount);
          logger.info("Port instance created");
        }
      } finally {
        instanceLock.unlock();
      }
    }
    return instance;
  }

  public Dock requestDock() {
    lock.lock();
    try {
      Dock dock = docks.poll();
      logger.info("Dock requested: {}", dock != null ? dock.getId() : "none available");
      return dock;
    } finally {
      lock.unlock();
    }
  }

  public void releaseDock(Dock dock) {
    lock.lock();
    try {
      docks.offer(dock);
      logger.info("Dock released: {}", dock.getId());
    } finally {
      lock.unlock();
    }
  }
}