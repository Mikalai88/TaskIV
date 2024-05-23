package com.port.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse {
  private final int capacity;
  private final AtomicInteger containers;

  public Warehouse(int capacity) {
    this.capacity = capacity;
    this.containers = new AtomicInteger(0);
  }

  public int getFreeSpace() {
    return capacity - containers.get();
  }

  public int getContainers() {
    return containers.get();
  }

  public void addContainers(int count) {
    containers.addAndGet(count);
  }

  public void removeContainers(int count) {
    containers.addAndGet(-count);
  }
}