package com.mikalai.port.state;

import com.mikalai.port.entity.Ship;

public interface ShipState {
  void handle(Ship ship);
}
