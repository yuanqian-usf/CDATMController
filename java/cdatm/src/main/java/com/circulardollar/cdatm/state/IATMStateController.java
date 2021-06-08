package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.constant.ATMStates;

import java.util.Optional;
import java.util.Set;

public interface IATMStateController {
  Integer getStateId();

  Optional<IError> canGoToNextState(Integer proposedNextStateId);

  void nextState(Integer proposedNextStateId);

  Set<ATMStates> availableStates();
}
