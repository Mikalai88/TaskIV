package com.mikalai.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse {
  private static final Logger logger = LogManager.getLogger(Warehouse.class);
  private static volatile Warehouse instance;
  private static final Object lock = new Object();

  private final int capacity;
  private final AtomicInteger containers;

  private Warehouse(int capacity) {
    this.capacity = capacity;
    this.containers = new AtomicInteger(0);
    logger.info("Warehouse created with capacity: {}", capacity);
  }

  public static void initializeInstance(int capacity) {
    if (instance == null) {
      synchronized (lock) {
        if (instance == null) {
          instance = new Warehouse(capacity);
          logger.info("Warehouse initialized with capacity: {}", capacity);
        }
      }
    }
  }

  public static Warehouse getInstance() {
    if (instance == null) {
      throw new IllegalStateException("Warehouse is not initialized. Call initializeInstance() first.");
    }
    logger.debug("Returning instance of Warehouse");
    return instance;
  }

  public int getFreeSpace() {
    int freeSpace = capacity - containers.get();
    logger.debug("Getting free space: {}", freeSpace);
    return freeSpace;
  }

  public int getContainers() {
    int currentContainers = containers.get();
    logger.debug("Getting current containers: {}", currentContainers);
    return currentContainers;
  }

  public void addContainers(int count) {
    containers.addAndGet(count);
    logger.info("Added containers: {}. Current containers: {}", count, containers.get());
  }

  public void removeContainers(int count) {
    containers.addAndGet(-count);
    logger.info("Removed containers: {}. Current containers: {}", count, containers.get());
  }
}