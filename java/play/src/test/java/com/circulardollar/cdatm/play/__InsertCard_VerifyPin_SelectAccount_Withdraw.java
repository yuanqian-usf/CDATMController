package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.generateBankDB;
import static com.circulardollar.cdatm.__TestBase.getCardByCardNumber;
import static com.circulardollar.cdatm.__TestBase.getRandomExistingLogin;
import static com.circulardollar.cdatm.__TestBase.randomCardNumberWithValidWithdrawBalance;
import static com.circulardollar.cdatm.__TestBase.randomNumber;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.Main;
import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.downstream.model.auth.Login;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.mapper.card.CardMapper;
import com.circulardollar.cdatm.business.mapper.pin.PinMapper;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.network.NonCSNetworkClientV2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

public class __InsertCard_VerifyPin_SelectAccount_Withdraw {
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
  public void test_withdraw_only_shouldExpectError() {
    IATMController controller =
        Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    assertNotNull(controller.withdraw(1).getError());
  }

  @Test
  public void test_insertCard_withdraw_shouldExpectError() {
    IATMController controller =
        Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNotNull(controller.withdraw(1).getError());
  }

  @Test
  public void test_insertCard_verifyPin_withdraw_shouldExpectError() {
    IATMController controller =
        Main.createATMController(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
    ILogin login = getRandomExistingLogin(accountsRecordTable.keySet());
    assertNull(controller.insertCard(login.getCard()).getError());
    assertNotNull(controller.verifyPin(login.getPin()).getBody());
    assertNotNull(controller.withdraw(1).getError());
  }

  // Assume we know the DB's latest balance
  @Test
  public void test_insertCard_verifyPin_selectAccount_withdraw_shouldNotExpectError() {
    Pair<String, String> cardNumberAccountNumberPair;
    IATMController controller;
    do {
      controller =
          Main.createATMController(
              NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
      cardNumberAccountNumberPair =
          randomCardNumberWithValidWithdrawBalance(
              cardNumberAccountNumberAccountRecordTable, false);
    } while (cardNumberAccountNumberPair == null);
    String cardNumber = cardNumberAccountNumberPair.getLeft();
    String accountNumber = cardNumberAccountNumberPair.getRight();
    Integer existingBalance =
        cardNumberAccountNumberAccountRecordTable.get(cardNumber).get(accountNumber).getBalance();
    Integer availableMaxWithdrawAmount = existingBalance;
    availableMaxWithdrawAmount =
        availableMaxWithdrawAmount > IATMConfigurations.MAX_WITHDRAW_AMOUNT
            ? IATMConfigurations.MAX_WITHDRAW_AMOUNT
            : availableMaxWithdrawAmount;
    Integer withdrawAmount;
    do {
      withdrawAmount = randomNumber(availableMaxWithdrawAmount);
    } while (withdrawAmount < IATMConfigurations.MIN_WITHDRAW_AMOUNT);
    ILoginRecord loginRecord = getCardByCardNumber(accountsRecordTable, cardNumber);
    Objects.requireNonNull(loginRecord);
    ILogin login =
        Login.newBuilder()
            .setCard(CardMapper.down(loginRecord.getCard()))
            .setPin(PinMapper.down(loginRecord.getPin()))
            .build();
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    assertTrue(accounts.contains(accountNumber));
    assertTrue(controller.selectAccount(accountNumber).getBody());

    IResponse<IWithdraw, IError> response = controller.withdraw(withdrawAmount);
    assertNull(response.getError());
    assertNotNull(response.getBody());
    assertEquals(
        response.getBody().getAccount().getBalance(),
        java.util.Optional.of(existingBalance - withdrawAmount).get());
  }

  @Test
  public void test_insertCard_verifyPin_selectAccount_repeat_withdraw_shouldNotExpectError() {
    Pair<String, String> cardNumberAccountNumberPair;
    IATMController controller;
    do {
      controller =
          Main.createATMController(
              NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
      cardNumberAccountNumberPair =
          randomCardNumberWithValidWithdrawBalance(
              cardNumberAccountNumberAccountRecordTable, false);
    } while (cardNumberAccountNumberPair == null);
    String cardNumber = cardNumberAccountNumberPair.getLeft();
    String accountNumber = cardNumberAccountNumberPair.getRight();
    Integer existingBalance =
        cardNumberAccountNumberAccountRecordTable.get(cardNumber).get(accountNumber).getBalance();

    ILoginRecord loginRecord = getCardByCardNumber(accountsRecordTable, cardNumber);
    Objects.requireNonNull(loginRecord);
    ILogin login =
        Login.newBuilder()
            .setCard(CardMapper.down(loginRecord.getCard()))
            .setPin(PinMapper.down(loginRecord.getPin()))
            .build();
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    assertTrue(accounts.contains(accountNumber));
    assertTrue(controller.selectAccount(accountNumber).getBody());

    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      IResponse<IWithdraw, IError> response = controller.withdraw(1);
      assertNull(response.getError());
      assertNotNull(response.getBody());
      assertEquals(
          response.getBody().getAccount().getBalance(),
          java.util.Optional.of(existingBalance - i - 1).get());
    }
  }

  @Test
  public void test_insertCard_verifyPin_repeat_selectAccount_withdraw_shouldNotExpectError() {
    Pair<String, String> cardNumberAccountNumberPair;
    IATMController controller;
    do {
      controller =
          Main.createATMController(
              NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(accountsRecordTable));
      cardNumberAccountNumberPair =
          randomCardNumberWithValidWithdrawBalance(
              cardNumberAccountNumberAccountRecordTable, false);
    } while (cardNumberAccountNumberPair == null);
    String cardNumber = cardNumberAccountNumberPair.getLeft();
    String accountNumber = cardNumberAccountNumberPair.getRight();
    Integer existingBalance =
        cardNumberAccountNumberAccountRecordTable.get(cardNumber).get(accountNumber).getBalance();

    ILoginRecord loginRecord = getCardByCardNumber(accountsRecordTable, cardNumber);
    Objects.requireNonNull(loginRecord);
    ILogin login =
        Login.newBuilder()
            .setCard(CardMapper.down(loginRecord.getCard()))
            .setPin(PinMapper.down(loginRecord.getPin()))
            .build();
    assertNull(controller.insertCard(login.getCard()).getError());
    List<String> accounts = controller.verifyPin(login.getPin()).getBody();
    assertTrue(accounts.contains(accountNumber));

    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      assertTrue(controller.selectAccount(accountNumber).getBody());
      IResponse<IWithdraw, IError> response = controller.withdraw(1);
      assertNull(response.getError());
      assertNotNull(response.getBody());
      assertEquals(
          response.getBody().getAccount().getBalance(),
          java.util.Optional.of(existingBalance - i - 1).get());
    }
  }
}
