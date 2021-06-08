package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.constant.ATMStates;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

import static com.circulardollar.cdatm.constant.ATMStates.*;
import static org.junit.Assert.*;

public class ATMStateControllerTest {

  @Test
  public void getStateId() {
    assertEquals(new ATMStateController().getStateId().intValue(), 1);
  }

  @Test
  public void canGoToNextState() {
    assertFalse(new ATMStateController().canGoToNextState(INSERT_CARD.getId()).isPresent());
    assertTrue(new ATMStateController().canGoToNextState(SELECT_ACCOUNT.getId()).isPresent());
    assertTrue(new ATMStateController().canGoToNextState(UNSPECIFIED.getId()).isPresent());
  }

  @Test
  public void nextState_01() {
    ATMStateController atmStateController = new ATMStateController();
    atmStateController.nextState(INSERT_CARD.getId());
    assertEquals(atmStateController.getStateId().intValue(), INSERT_CARD.getId().intValue());
  }

  @Test
  public void nextState_02() {
    ATMStateController atmStateController = new ATMStateController();
    atmStateController.nextState(INSERT_CARD.getId());
    atmStateController.nextState(UNSPECIFIED.getId());
    assertEquals(atmStateController.getStateId().intValue(), INSERT_CARD.getId().intValue());
  }

  @Test
  public void nextState_03() {
    ATMStateController atmStateController = new ATMStateController();
    atmStateController.nextState(INSERT_CARD.getId());
    atmStateController.nextState(SELECT_ACCOUNT.getId());
    assertEquals(atmStateController.getStateId().intValue(), INSERT_CARD.getId().intValue());
  }

  @Test
  public void availableStates() {
    assertEquals(
        new ATMStateController().availableStates(),
        new HashSet<>(Collections.singletonList(INSERT_CARD)));
  }
}
