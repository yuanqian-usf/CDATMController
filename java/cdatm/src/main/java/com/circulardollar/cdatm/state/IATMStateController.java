package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.constant.ATMStates;

import java.util.Set;

public interface IATMStateController {
  Integer getStateId();

  Boolean canGoToNextState(Integer proposedNextStateId);

  void nextState(Integer proposedNextStateId);

  Set<ATMStates> availableStates();
}
