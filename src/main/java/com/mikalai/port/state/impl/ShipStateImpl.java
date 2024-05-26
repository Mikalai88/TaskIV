package com.mikalai.port.state;

import com.mikalai.port.entity.Ship;

public class ShipStateImpl implements ShipState {
  public enum State {
    WAITING,
    UNLOADING,
    LOADING,
    READY_TO_DEPART
  }

  private State currentState;

  public ShipStateImpl() {
    this.currentState = State.WAITING;
  }

  @Override
  public void handle(Ship ship) {
    switch (currentState) {
      case WAITING:
        handleWaiting(ship);
        break;
      case UNLOADING:
        handleUnloading(ship);
        break;
      case LOADING:
        handleLoading(ship);
        break;
      case READY_TO_DEPART:
        handleReadyToDepart(ship);
        break;
    }
  }

  private void handleWaiting(Ship ship) {
    System.out.println(ship.getName() + " is waiting.");
    currentState = State.UNLOADING;
    ship.setState(this);
  }

  private void handleUnloading(Ship ship) {
    System.out.println(ship.getName() + " is unloading.");
    ship.unload(ship.getContainersToUnload());
    currentState = State.LOADING;
    ship.setState(this);
  }

  private void handleLoading(Ship ship) {
    System.out.println(ship.getName() + " is loading.");
    ship.load(ship.getContainersToLoad());
    currentState = State.READY_TO_DEPART;
    ship.setState(this);
  }

  private void handleReadyToDepart(Ship ship) {
    System.out.println(ship.getName() + " is ready to depart.");
    // Завершающее состояние, ничего не делаем
  }

  public State getCurrentState() {
    return currentState;
  }
}
