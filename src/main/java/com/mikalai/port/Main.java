package com.mikalai.port;

import com.mikalai.port.entity.Ship;
import com.mikalai.port.factory.ShipFactory;
import com.mikalai.port.service.Port;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    Port port = Port.getInstance();
    List<Ship> ships = ShipFactory.createShips(30, port);

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    for (Ship ship : ships) {
      executorService.execute(() -> ship.process(port));
    }

    executorService.shutdown();
    while (!executorService.isTerminated()) {
    }

    System.out.println("All ships have been processed.");
  }
}