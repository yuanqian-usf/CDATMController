package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.anyString;
import static com.circulardollar.cdatm.__TestBase.generateBankDB;
import static com.circulardollar.cdatm.__TestBase.getRandomAccountNumberForSelection;
import static com.circulardollar.cdatm.__TestBase.getRandomExistingAccountNumberFromAccountsRecords;
import static com.circulardollar.cdatm.__TestBase.getRandomExistingLogin;
import static com.circulardollar.cdatm.__TestBase.randomString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.__Main;
import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.network.NonCSNetworkClientV2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class __InsertCard_VerifyPin_SelectAccount {

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
  public void test_selectAccount_only_shouldExpectError() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    assertNotNull(
        controller
            .selectAccount(
                getRandomExistingAccountNumberFromAccountsRecords(
                    cardNumberAccountNumberAccountRecordTable))
            .getError());
  }

  @Test
  public void test_insertCard_verifyPin_selectAccount_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    IResponse<List<String>, IError> accountsPerCard = controller.verifyPin(login.getPin());
    assertNull(accountsPerCard.getError());
    assertNotNull(accountsPerCard.getBody());
    String cardNumber = login.getCard().getCardNumber();

    List<String> accounts = accountsPerCard.getBody();
    Set<String> accountNumberSet = new HashSet<>(accounts);
    Map<String, IAccountRecord> accountsRecordMap =
        cardNumberAccountNumberAccountRecordTable.get(cardNumber);
    assertEquals(accounts.size(), accountsRecordMap.keySet().size());

    for (Map.Entry<String, IAccountRecord> entry : accountsRecordMap.entrySet()) {
      assertTrue(accountNumberSet.contains(entry.getKey()));
    }
    IResponse<Boolean, IError> selectAccountResponse =
        controller.selectAccount(getRandomAccountNumberForSelection(accounts));
    assertNotNull(selectAccountResponse);
    assertTrue(selectAccountResponse.getBody());
    assertNull(selectAccountResponse.getError());
  }

  @Test
  public void test_insertCard_verifyPin_selectAccount_invalid_accountNumber_shouldExpectError_01() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNull(controller.verifyPin(login.getPin()).getError());
    IResponse<Boolean, IError> selectAccountResponse = controller.selectAccount(anyString());
    assertNotNull(selectAccountResponse.getError());
    assertNull(selectAccountResponse.getBody());
  }

  @Test
  public void test_insertCard_verifyPin_selectAccount_invalid_accountNumber_shouldExpectError_02() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNull(controller.verifyPin(login.getPin()).getError());
    IResponse<Boolean, IError> selectAccountResponse = controller.selectAccount(randomString());
    assertNotNull(selectAccountResponse.getError());
    assertNull(selectAccountResponse.getBody());
  }

  @Test
  public void
      test_insertCard_verifyPin_selectAccount_invalid_then_valid_accountNumber_shouldExpectError() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    IResponse<Boolean, IError> selectAccountResponse1 = controller.selectAccount(randomString());
    assertNotNull(selectAccountResponse1.getError());
    assertNull(selectAccountResponse1.getBody());
    IResponse<Boolean, IError> selectAccountResponse =
        controller.selectAccount(getRandomAccountNumberForSelection(accounts));
    assertNull(selectAccountResponse.getError());
    assertNotNull(selectAccountResponse.getBody());
  }

  @Test
  public void
      test_insertCard_verifyPin_selectAccount_valid_then_invalid_accountNumber_shouldExpectError() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    IResponse<Boolean, IError> selectAccountResponse =
        controller.selectAccount(getRandomAccountNumberForSelection(accounts));
    assertNull(selectAccountResponse.getError());
    assertNotNull(selectAccountResponse.getBody());
    IResponse<Boolean, IError> selectAccountResponse1 = controller.selectAccount(randomString());
    assertNotNull(selectAccountResponse1.getError());
    assertNull(selectAccountResponse1.getBody());
  }

  @Test
  public void test_insertCard_verifyPin_repeat_selectAccount_shouldNotExpectError() {
    IATMController controller =
        __Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      IResponse<Boolean, IError> selectAccountResponse =
          controller.selectAccount(getRandomAccountNumberForSelection(accounts));
      assertNull(selectAccountResponse.getError());
      assertNotNull(selectAccountResponse.getBody());
    }
  }
}
