package com.mikalai.port.entity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.mikalai.port.service.Port;

public class Ship implements Runnable {
  private static final AtomicInteger idGenerator = new AtomicInteger(100);
  private final int id;
  private final String name;
  private int containers;
  private final int capacity;
  private final Action action;
  private int containersToLoad;
  private int containersToUnload;

  public Ship(String name, int containers, int capacity, Action action, int containersToLoad, int containersToUnload) {
    this.id = generateId();
    this.name = name;
    this.containers = containers;
    this.capacity = capacity;
    this.action = action;
    this.containersToLoad = containersToLoad;
    this.containersToUnload = containersToUnload;
  }

  private static int generateId() {
    return idGenerator.getAndIncrement();
  }

  public enum Action {
    LOAD, UNLOAD, LOAD_UNLOAD
  }

  @Override
  public void run() {
    // Пустой метод run для соответствия интерфейсу Runnable,
    // фактическая логика будет выполняться через Port
  }

  public void process(Port port) {
    try {
      Dock dock = port.requestDock();
      if (dock != null) {
        switch (action) {
          case LOAD:
            loadContainers(dock);
            break;
          case UNLOAD:
            unloadContainers(dock);
            break;
          case LOAD_UNLOAD:
            unloadContainers(dock);
            loadContainers(dock);
            break;
        }
        port.releaseDock(dock);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Восстановление состояния прерывания
    }
  }

  private void unloadContainers(Dock dock) throws InterruptedException {
    int unloadCount = Math.min(containersToUnload, dock.getWarehouse().getFreeSpace());
    containers -= unloadCount;
    dock.getWarehouse().addContainers(unloadCount);
    TimeUnit.SECONDS.sleep(1);
  }

  private void loadContainers(Dock dock) throws InterruptedException {
    int loadCount = Math.min(containersToLoad, dock.getWarehouse().getContainers());
    containers += loadCount;
    dock.getWarehouse().removeContainers(loadCount);
    TimeUnit.SECONDS.sleep(1);
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public int getContainers() {
    return containers;
  }

  public Action getAction() {
    return action;
  }

  @Override
  public String toString() {
    return "Ship{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", containers=" + containers +
            ", capacity=" + capacity +
            ", action=" + action +
            ", containersToLoad=" + containersToLoad +
            ", containersToUnload=" + containersToUnload +
            '}';
  }
}