package com.port.entity;

import com.port.service.Port;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Ship implements Callable<Void> {
  private final String name;
  private int containers;
  private final int capacity;
  private final Port port;

  public Ship(String name, int containers, int capacity, Port port) {
    this.name = name;
    this.containers = containers;
    this.capacity = capacity;
    this.port = port;
  }

  @Override
  public Void call() throws Exception {
    Dock dock = port.requestDock();
    if (dock != null) {
      unloadContainers(dock);
      loadContainers(dock);
      port.releaseDock(dock);
    }
    return null;
  }

  private void unloadContainers(Dock dock) throws InterruptedException {
    int unloadCount = Math.min(containers, dock.getWarehouse().getFreeSpace());
    containers -= unloadCount;
    dock.getWarehouse().addContainers(unloadCount);
    TimeUnit.SECONDS.sleep(1);
  }

  private void loadContainers(Dock dock) throws InterruptedException {
    int loadCount = Math.min(capacity - containers, dock.getWarehouse().getContainers());
    containers += loadCount;
    dock.getWarehouse().removeContainers(loadCount);
    TimeUnit.SECONDS.sleep(1);
  }

  public String getName() {
    return name;
  }

  public int getContainers() {
    return containers;
  }
}