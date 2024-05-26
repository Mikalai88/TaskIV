package com.mikalai.port;

import com.mikalai.port.entity.Ship;
import com.mikalai.port.entity.Warehouse;
import com.mikalai.port.factory.ShipFactory;
import com.mikalai.port.service.Port;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Initializing warehouse...");
    Warehouse.initializeInstance(100);  // Инициализация singleton Warehouse

    logger.info("Initializing port...");
    Port port = Port.getInstance(5);  // Пример использования getInstance с параметрами

    logger.info("Creating ships...");
    List<Ship> ships = ShipFactory.createShips(30, port);

    logger.info("Starting ship processing...");
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    for (Ship ship : ships) {
      executorService.execute(ship);
    }

    executorService.shutdown();
    while (!executorService.isTerminated()) {
      // Ждем завершения всех задач
    }

    logger.info("All ships have been processed.");
  }
}
