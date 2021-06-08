package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.validRandomCard;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.__Main;
import org.junit.Test;

public class __InsertCard_EjectCard {

  @Test
  public void test_ejectCard_only_shouldExpectError() {
    IATMController controller = __Main.__ATMController();
    assertNotNull(controller.ejectCard().getError());
  }

  @Test
  public void test_insertCard_ejectCard_shouldNotExpectError() {
    IATMController controller = __Main.__ATMController();
    assertNull(controller.insertCard(validRandomCard()).getError());
    assertNull(controller.ejectCard().getError());
  }

  @Test
  public void test_insertCard_ejectCard_repeat_shouldNotExpectError_01() {
    IATMController controller = __Main.__ATMController();
    assertNull(controller.insertCard(validRandomCard()).getError());
    assertNull(controller.ejectCard().getError());
    assertNull(controller.insertCard(validRandomCard()).getError());
  }

  @Test
  public void test_insertCard_ejectCard_repeat_shouldNotExpectError_02() {
    IATMController controller = __Main.__ATMController();
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      assertNull(controller.insertCard(validRandomCard()).getError());
      assertNull(controller.ejectCard().getError());
    }
  }
}
