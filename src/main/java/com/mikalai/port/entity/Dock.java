package com.mikalai.port.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Dock {
  private static final AtomicInteger idGenerator = new AtomicInteger(1);
  private final int id;
  private Warehouse warehouse;

  public Dock(Warehouse warehouse) {
    this.id = generateId();
    this.warehouse = warehouse;
  }

  private static int generateId() {
    return idGenerator.getAndIncrement() * 10;
  }

  public int getId() {
    return id;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }
}