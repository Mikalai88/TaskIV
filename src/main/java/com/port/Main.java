package com.port;

import com.port.entity.Ship;
import com.port.service.Port;
import com.port.util.PortSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    Port port = PortSingleton.getInstance(3, 100);

    List<Ship> ships = new ArrayList<>();
    ships.add(new Ship("Ship1", 10, 50, port));
    ships.add(new Ship("Ship2", 20, 70, port));
    ships.add(new Ship("Ship3", 30, 100, port));

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    List<Future<Void>> futures = new ArrayList<>();
    for (Ship ship : ships) {
      futures.add(executorService.submit(ship));
    }

    for (Future<Void> future : futures) {
      future.get();
    }

    executorService.shutdown();
    logger.info("All ships have been processed.");
  }
}