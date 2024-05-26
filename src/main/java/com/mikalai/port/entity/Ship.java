package com.mikalai.port.entity;

import com.mikalai.port.state.ShipState;
import com.mikalai.port.state.ShipStateImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship implements Runnable {
  private static final Logger logger = LogManager.getLogger(Ship.class);
  private static final AtomicInteger idGenerator = new AtomicInteger(100);
  private final int id;
  private final String name;
  private int containers;
  private final int capacity;
  private ShipState state;
  private final Action action;
  private final int containersToLoad;
  private final int containersToUnload;

  public enum Action {
    LOAD, UNLOAD, LOAD_UNLOAD
  }

  public Ship(String name, int containers, int capacity, Action action, int containersToLoad, int containersToUnload) {
    this.id = generateId();
    this.name = name;
    this.containers = containers;
    this.capacity = capacity;
    this.action = action;
    this.containersToLoad = containersToLoad;
    this.containersToUnload = containersToUnload;
    this.state = new ShipStateImpl();  // начальное состояние
    logger.info("Ship created: {}", this);
  }

  private static int generateId() {
    return idGenerator.getAndIncrement();
  }

  @Override
  public void run() {
    while (!((ShipStateImpl) state).getCurrentState().equals(ShipStateImpl.State.READY_TO_DEPART)) {
      state.handle(this);
    }
    logger.info("Ship {} has completed its process and is ready to depart.", name);
  }

  public void setState(ShipState state) {
    this.state = state;
    logger.debug("Ship {} changed state to {}", name, state.getClass().getSimpleName());
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

  public void unload(int count) {
    containers -= count;
    logger.info("Ship {} unloaded {} containers. Current load: {}", name, count, containers);
  }

  public void load(int count) {
    containers += count;
    logger.info("Ship {} loaded {} containers. Current load: {}", name, count, containers);
  }

  public int getContainersToLoad() {
    return containersToLoad;
  }

  public int getContainersToUnload() {
    return containersToUnload;
  }
}
