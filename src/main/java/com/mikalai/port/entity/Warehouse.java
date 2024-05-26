package com.mikalai.port.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse {
  private final int capacity;
  private final AtomicInteger containers;

  private Warehouse(int capacity) {
    this.capacity = capacity;
    this.containers = new AtomicInteger(0);
  }

  private static class WarehouseHolder {
    private static final Warehouse INSTANCE = new Warehouse(1000); // Пример: склад на 1000 контейнеров
  }

  public static Warehouse getInstance() {
    return WarehouseHolder.INSTANCE;
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