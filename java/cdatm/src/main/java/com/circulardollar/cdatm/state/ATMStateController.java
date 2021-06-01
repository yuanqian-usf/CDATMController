package com.circulardollar.cdatm.state;

import static com.circulardollar.cdatm.constant.ATMStates.ACTIVE;
import static com.circulardollar.cdatm.constant.ATMStates.CHECK_BALANCE;
import static com.circulardollar.cdatm.constant.ATMStates.DEPOSIT;
import static com.circulardollar.cdatm.constant.ATMStates.INSERT_CARD;
import static com.circulardollar.cdatm.constant.ATMStates.SELECT_ACCOUNT;
import static com.circulardollar.cdatm.constant.ATMStates.VERIFY_PIN;
import static com.circulardollar.cdatm.constant.ATMStates.WITHDRAW;

import com.circulardollar.cdatm.constant.ATMStates;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
  public Boolean canGoToNextState(Integer proposedNextStateId) {
    ATMStates proposedNextState = ATMStates.ofId(proposedNextStateId);
    if (ATMStates.UNSPECIFIED == proposedNextState) return false;
    Set<ATMStates> availableStates = availableStates();
    return availableStates.contains(proposedNextState);
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
