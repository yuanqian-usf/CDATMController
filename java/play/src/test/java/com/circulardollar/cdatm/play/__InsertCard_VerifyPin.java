package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.RANDOM_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.anyString;
import static com.circulardollar.cdatm.__TestBase.emptyString;
import static com.circulardollar.cdatm.__TestBase.fixedLengthRandomNumber;
import static com.circulardollar.cdatm.__TestBase.generateBankDB;
import static com.circulardollar.cdatm.__TestBase.getRandomExistingLogin;
import static com.circulardollar.cdatm.__TestBase.randomLengthRandomNumber;
import static com.circulardollar.cdatm.__TestBase.validRandomCard;
import static com.circulardollar.cdatm.__TestBase.validRandomPin;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.__Main;
import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.play.network.__NetworkClientV2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class __InsertCard_VerifyPin {

  private final Map<String, IPinRecord> cardNumberLoginRecordTable = new HashMap<>();
  private final Map<String, Map<String, IAccountRecord>> cardNumberAccountNumberAccountRecordTable =
      new HashMap<>();
  private final Map<ILoginRecord, IAccountsRecord> accountsRecordTable = new HashMap<>();
  private Pin.Builder pendingPinNumberPinBuilder;

  @Before
  public void setup() {
    pendingPinNumberPinBuilder = Pin.newBuilder();
    generateBankDB(
        accountsRecordTable, cardNumberLoginRecordTable, cardNumberAccountNumberAccountRecordTable);
  }

  @Test
  public void test_verifyPin_only_shouldExpectError() {
    IATMController controller = __Main.__ATMController();
    assertNotNull(controller.verifyPin(validRandomPin()).getError());
  }

  @Test
  public void test_insertCard_verifyPin_pinNumber_response_null() {
    IATMController controller = __Main.__ATMController();
    controller.insertCard(validRandomCard());
    assertNotNull(controller.verifyPin(null));
  }

  @Test
  public void test_insertCard_verifyPin_response_not_null() {
    IATMController controller = __Main.__ATMController();
    controller.insertCard(validRandomCard());
    assertNotNull(
        controller.verifyPin(pendingPinNumberPinBuilder.setPinNumber(anyString()).build()));
  }

  @Test
  public void test_insertCard_verifyPin_shouldExpectError() {
    IATMController controller = __Main.__ATMController();
    controller.insertCard(validRandomCard());
    assertNotNull(
        controller
            .verifyPin(pendingPinNumberPinBuilder.setPinNumber(anyString()).build())
            .getError());
  }

  @Test
  public void test_insertCard_verifyPin_validate_pinNumber_zeroLength_shouldExpectError() {
    IATMController controller = __Main.__ATMController();
    controller.insertCard(validRandomCard());
    assertNotNull(
        controller
            .verifyPin(pendingPinNumberPinBuilder.setPinNumber(emptyString()).build())
            .getError());
  }

  @Test
  public void
      test_insertCard_verifyPin_validate_pinNumber_invalidLength_shouldExpectError_BELOW_MIN() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      IATMController controller = __Main.__ATMController();
      controller.insertCard(validRandomCard());
      IError error =
          controller
              .verifyPin(
                  pendingPinNumberPinBuilder
                      .setPinNumber(
                          randomLengthRandomNumber(
                              IATMConfigurations.MIN_PIN_LENGTH - 2,
                              IATMConfigurations.MIN_PIN_LENGTH))
                      .build())
              .getError();
      assertNotNull(error);
    }
  }

  @Test
  public void
      test_insertCard_verifyPin_validate_pinNumber_invalidLength_shouldExpectError_ABOVE_MAX() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      IATMController controller = __Main.__ATMController();
      controller.insertCard(validRandomCard());
      IError error =
          controller
              .verifyPin(
                  pendingPinNumberPinBuilder
                      .setPinNumber(fixedLengthRandomNumber(IATMConfigurations.MAX_PIN_LENGTH + 1))
                      .build())
              .getError();
      assertNotNull(error);
    }
  }

  @Test
  public void test_insertCard_verifyPin_pinNumber_validRandomLength_verifyPin_shouldExpectTrue() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      IATMController controller = __Main.__ATMController();
      controller.insertCard(validRandomCard());

      IResponse<List<String>, IError> actual =
          controller.verifyPin(
              pendingPinNumberPinBuilder
                  .setPinNumber(
                      randomLengthRandomNumber(
                          IATMConfigurations.MIN_PIN_LENGTH, IATMConfigurations.MAX_PIN_LENGTH))
                  .build());
      assertNotNull(actual);
    }
  }

  @Test
  public void test_insertCard_verifyPin_repeat_shouldExpectError_01() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNull(controller.verifyPin(login.getPin()).getError());
    assertNotNull(controller.verifyPin(login.getPin()).getError());
  }

  @Test
  public void test_insertCard_verifyPin_first_time_failed_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNotNull(
        controller
            .verifyPin(pendingPinNumberPinBuilder.setPinNumber(anyString()).build())
            .getError());
    assertNull(controller.verifyPin(login.getPin()).getError());
  }

  @Test
  public void test_insertCard_verifyPin_repeat_invalid_pin_validation_shouldExpectError() {
    IATMController controller = __Main.__ATMController();
    assertNull(controller.insertCard(validRandomCard()).getError());
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      assertNotNull(
          controller
              .verifyPin(pendingPinNumberPinBuilder.setPinNumber(anyString()).build())
              .getError());
    }
  }
}
