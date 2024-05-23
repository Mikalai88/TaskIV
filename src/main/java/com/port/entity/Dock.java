package com.port.entity;

public class Dock {
  private final int id;
  private final Warehouse warehouse;

  public Dock(int id, Warehouse warehouse) {
    this.id = id;
    this.warehouse = warehouse;
  }

  public int getId() {
    return id;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }
}
