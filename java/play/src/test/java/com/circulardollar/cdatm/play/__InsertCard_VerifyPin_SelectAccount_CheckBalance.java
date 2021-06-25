package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.generateBankDB;
import static com.circulardollar.cdatm.__TestBase.getRandomAccountNumberForSelection;
import static com.circulardollar.cdatm.__TestBase.getRandomExistingLogin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.__Main;
import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.play.network.__NetworkClientV2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class __InsertCard_VerifyPin_SelectAccount_CheckBalance {
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
  public void test_checkBalance_only_shouldExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    assertNotNull(controller.checkBalance().getError());
  }

  @Test
  public void test_insertCard_checkBalance_shouldExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNotNull(controller.checkBalance().getError());
  }

  @Test
  public void test_insertCard_verifyPin_checkBalance_shouldExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNotNull(controller.verifyPin(login.getPin()).getBody());
    assertNotNull(controller.checkBalance().getError());
  }

  @Test
  public void test_insertCard_verifyPin_selectAccount_checkBalance_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    String randomAccountNumberForSelection = getRandomAccountNumberForSelection(accounts);
    IAccountRecord accountRecord =
        cardNumberAccountNumberAccountRecordTable
            .get(login.getCard().getCardNumber())
            .get(randomAccountNumberForSelection);
    assertEquals(randomAccountNumberForSelection, accountRecord.getAccountNumber());
    assertTrue(controller.selectAccount(randomAccountNumberForSelection).getBody());
    assertEquals(accountRecord.getBalance(), controller.checkBalance().getBody());
  }

  @Test
  public void test_insertCard_verifyPin_selectAccount_repeat_checkBalance_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    String randomAccountNumberForSelection = getRandomAccountNumberForSelection(accounts);
    IAccountRecord accountRecord =
        cardNumberAccountNumberAccountRecordTable
            .get(login.getCard().getCardNumber())
            .get(randomAccountNumberForSelection);
    assertEquals(randomAccountNumberForSelection, accountRecord.getAccountNumber());
    assertTrue(controller.selectAccount(randomAccountNumberForSelection).getBody());
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      assertEquals(accountRecord.getBalance(), controller.checkBalance().getBody());
    }
  }

  @Test
  public void test_insertCard_verifyPin_repeat_selectAccount_checkBalance_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            __NetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      String randomAccountNumberForSelection = getRandomAccountNumberForSelection(accounts);
      IAccountRecord accountRecord =
          cardNumberAccountNumberAccountRecordTable
              .get(login.getCard().getCardNumber())
              .get(randomAccountNumberForSelection);
      assertEquals(randomAccountNumberForSelection, accountRecord.getAccountNumber());
      assertTrue(controller.selectAccount(randomAccountNumberForSelection).getBody());
      assertEquals(accountRecord.getBalance(), controller.checkBalance().getBody());
    }
  }
}
