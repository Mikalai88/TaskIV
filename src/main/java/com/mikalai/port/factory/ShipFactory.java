package com.mikalai.port.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mikalai.port.entity.Ship;
import com.mikalai.port.service.Port;

public class ShipFactory {

  private static final Random RANDOM = new Random();

  public static List<Ship> createShips(int numberOfShips, Port port) {
    List<Ship> ships = new ArrayList<>();

    for (int i = 0; i < numberOfShips; i++) {
      String name = "Ship" + (i + 1);
      int containers = RANDOM.nextInt(50) + 1; // Random number between 1 and 50
      int capacity = RANDOM.nextInt(50) + 51;  // Random number between 51 and 100
      Ship.Action action = Ship.Action.values()[RANDOM.nextInt(Ship.Action.values().length)];
      int containersToLoad = RANDOM.nextInt(25) + 1; // Random number between 1 and 25
      int containersToUnload = RANDOM.nextInt(25) + 1; // Random number between 1 and 25

      Ship ship = new Ship(name, containers, capacity, action, containersToLoad, containersToUnload);
      ships.add(ship);
    }

    return ships;
  }
}
