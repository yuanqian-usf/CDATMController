package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.generateBankDB;
import static com.circulardollar.cdatm.__TestBase.getRandomExistingLogin;
import static org.junit.Assert.assertNull;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.__Main;
import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.network.NonCSNetworkClientV2;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class __InsertCard_VerifyPin_EjectCard {

  private final Map<String, IPinRecord> cardNumberLoginRecordTable = new HashMap<>();
  private final Map<String, Map<String, IAccountRecord>> cardNumberAccountNumberAccountRecordTable =
      new HashMap<>();
  private final Map<ILoginRecord, IAccountsRecord> accountsRecordTable = new HashMap<>();

  @Before
  public void setup() {
    generateBankDB(
        accountsRecordTable, cardNumberLoginRecordTable, cardNumberAccountNumberAccountRecordTable);
  }

  @Test
  public void test_insertCard_verifyPin_ejectCard_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNull(controller.verifyPin(login.getPin()).getError());
    assertNull(controller.ejectCard().getError());
  }

  @Test
  public void test_insertCard_verifyPin_ejectCard_repeat_shouldNotExpectError_01() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
      assertNull(controller.insertCard(login.getCard()).getError());
      assertNull(controller.verifyPin(login.getPin()).getError());
      assertNull(controller.ejectCard().getError());
    }
  }

  @Test
  public void test_insertCard_verifyPin_ejectCard_repeat_shouldNotExpectError_02() {
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      IATMController controller =
          __Main.createATMController(
              NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
      ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
      assertNull(controller.insertCard(login.getCard()).getError());
      assertNull(controller.verifyPin(login.getPin()).getError());
      assertNull(controller.ejectCard().getError());
    }
  }
}
