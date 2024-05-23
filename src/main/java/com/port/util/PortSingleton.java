package com.port.util;

import com.port.service.Port;

public class PortSingleton {
  private static volatile Port instance;

  private PortSingleton() {
  }

  public static Port getInstance(int dockCount, int warehouseCapacity) {
    if (instance == null) {
      synchronized (PortSingleton.class) {
        if (instance == null) {
          instance = new Port(dockCount, warehouseCapacity);
        }
      }
    }
    return instance;
  }
}