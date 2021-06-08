package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.constant.ATMStates;

import com.circulardollar.cdatm.constant.UserInterface;
import java.util.*;

import static com.circulardollar.cdatm.constant.ATMStates.*;

public class ATMStateController implements IATMStateController {

  private static final Map<ATMStates, Set<ATMStates>> STATE_MAP = new HashMap<>();

  static {
    Set<ATMStates> active = new HashSet<>(Collections.singletonList(INSERT_CARD));
    Set<ATMStates> insertCard = new HashSet<>(Arrays.asList(ACTIVE, VERIFY_PIN));
    Set<ATMStates> verifyPin = new HashSet<>(Arrays.asList(SELECT_ACCOUNT, ACTIVE));
    Set<ATMStates> selectAccount =
        new HashSet<>(Arrays.asList(SELECT_ACCOUNT, CHECK_BALANCE, DEPOSIT, WITHDRAW, ACTIVE));
    Set<ATMStates> checkBalance =
        new HashSet<>(Arrays.asList(SELECT_ACCOUNT, CHECK_BALANCE, DEPOSIT, WITHDRAW, ACTIVE));
    Set<ATMStates> deposit =
        new HashSet<>(Arrays.asList(SELECT_ACCOUNT, CHECK_BALANCE, DEPOSIT, WITHDRAW, ACTIVE));
    Set<ATMStates> withdraw =
        new HashSet<>(Arrays.asList(SELECT_ACCOUNT, CHECK_BALANCE, DEPOSIT, WITHDRAW, ACTIVE));

    STATE_MAP.put(ACTIVE, active);
    STATE_MAP.put(INSERT_CARD, insertCard);
    STATE_MAP.put(VERIFY_PIN, verifyPin);
    STATE_MAP.put(SELECT_ACCOUNT, selectAccount);
    STATE_MAP.put(CHECK_BALANCE, checkBalance);
    STATE_MAP.put(DEPOSIT, deposit);
    STATE_MAP.put(WITHDRAW, withdraw);
  }

  private ATMStates state = ACTIVE;

  @Override
  public Integer getStateId() {
    return state.getId();
  }

  @Override
  public Optional<IError> canGoToNextState(Integer proposedNextStateId) {
    ATMStates proposedNextState = ATMStates.ofId(proposedNextStateId);
    Optional<IError> error = Optional.of(Error.of(
        IATMStateController.class,
        Collections.singletonList(UserInterface.OPERATION_NOT_ALLOWED.getValue())));
    if (ATMStates.UNSPECIFIED == proposedNextState) return error;
    Set<ATMStates> availableStates = availableStates();
    return availableStates.contains(proposedNextState) ? Optional.empty() : error;
  }

  @Override
  public void nextState(Integer proposedNextStateId) {
    ATMStates proposedNextState = ATMStates.ofId(proposedNextStateId);
    if (ATMStates.UNSPECIFIED == proposedNextState) return;
    Set<ATMStates> availableStates = availableStates();
    if (availableStates.contains(proposedNextState)) {
      state = proposedNextState;
    }
  }

  @Override
  public Set<ATMStates> availableStates() {
    return STATE_MAP.get(state);
  }
}
