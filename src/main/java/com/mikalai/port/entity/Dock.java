package com.mikalai.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Dock {
  private static final Logger logger = LogManager.getLogger(Dock.class);
  private static final AtomicInteger idGenerator = new AtomicInteger(1);
  private final int id;
  private Warehouse warehouse;

  public Dock(Warehouse warehouse) {
    this.id = generateId();
    this.warehouse = warehouse;
    logger.info("Dock created with id: {}", id);
  }

  private static int generateId() {
    int generatedId = idGenerator.getAndIncrement() * 10;
    logger.debug("Generated id: {}", generatedId);
    return generatedId;
  }

  public int getId() {
    logger.debug("Getting id: {}", id);
    return id;
  }

  public Warehouse getWarehouse() {
    logger.debug("Getting warehouse for dock id: {}", id);
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
    logger.info("Warehouse set for dock id: {}", id);
  }
}