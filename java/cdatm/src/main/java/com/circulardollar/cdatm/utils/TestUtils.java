package com.circulardollar.cdatm.utils;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.LoginRecord;
import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.PinRecord;
import com.circulardollar.cdatm.config.IATMConfigurations;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {
  public static final int TEST_ITERATION = 3;
  public static final String TESTING = "testing";

  public static ICardRecord validRandomCardRecord() {
    return randomCard(
        IATMConfigurations.MIN_CARD_NUMBER_LENGTH, IATMConfigurations.MAX_CARD_NUMBER_LENGTH);
  }

  public static String anyString() {
    return RandomStringUtils.randomAlphabetic(new Random().nextInt(30) + 2);
  }

  public static ICardRecord randomCard(Integer minLength, Integer maxLength) {
    return CardRecord.newBuilder()
        .setHolderName(anyString())
        .setCardNumber(randomLengthRandomNumber(minLength, maxLength))
        .setCvc(anyString())
        .setExpirationDate(anyString())
        .build();
  }

  public static String randomLengthRandomNumber(int min, int max) {
    return IntStream.range(0, randomNumber(max - min) + min)
        .mapToObj(index -> randomDigit().toString())
        .collect(Collectors.joining());
  }

  public static Integer randomNumber(int end) {
    return new Random().nextInt(end);
  }

  public static Integer randomDigit() {
    return new Random().nextInt(10);
  }

  public static IPinRecord validRandomPinRecord() {
    return PinRecord.newBuilder()
        .setPinNumber(
            randomLengthRandomNumber(
                IATMConfigurations.MIN_CARD_NUMBER_LENGTH, IATMConfigurations.MAX_PIN_LENGTH))
        .build();
  }

  public static Integer randomBalance() {
    long num = (long) (new Random().nextInt(Integer.MAX_VALUE) * 1.3f);
    boolean negFlag = new Random().nextBoolean();
    num *= negFlag ? -1 : 1;
    if (num > Integer.MAX_VALUE) return Integer.MAX_VALUE;
    if (num < Integer.MIN_VALUE) return Integer.MIN_VALUE;
    return (int) num;
  }

  public static Map<ILoginRecord, IAccountsRecord> dummyDB() {
    Map<ILoginRecord, IAccountsRecord> dummyAccountsTable = new HashMap<>();
    TestUtils.generateBankDB(dummyAccountsTable, new HashMap<>(), new HashMap<>());
    return dummyAccountsTable;
  }

  public static void generateBankDB(Map<ILoginRecord, IAccountsRecord> accountsRecordTable,
      Map<String, IPinRecord> cardNumberLoginRecordsTable,
      Map<String, Map<String, IAccountRecord>> cardNumberAccountNumberAccountRecordTable) {
    accountsRecordTable.clear();
    cardNumberLoginRecordsTable.clear();
    cardNumberAccountNumberAccountRecordTable.clear();
    double loginRecordsCount = Math.pow(TEST_ITERATION, 2);
    double totalAccountsForAssociating = Math.pow(TEST_ITERATION, 3);
    int logOfTotalAccountsPerAccount = (int) Math.log(totalAccountsForAssociating);
    for (int i = 0; i < loginRecordsCount; i++) {
      ILoginRecord loginRecord =
          LoginRecord.newBuilder().setCard(validRandomCardRecord()).setPin(validRandomPinRecord())
              .build();
      cardNumberLoginRecordsTable.put(loginRecord.getCard().getCardNumber(), loginRecord.getPin());
      double x = Math.log(totalAccountsForAssociating);

      int accountAmountForThisCard = randomNumber(logOfTotalAccountsPerAccount);
      List<IAccountRecord> accountRecordList = new ArrayList<>();

      for (int j = 0; j < accountAmountForThisCard; j++) {
        accountRecordList.add(
            AccountRecord.newBuilder().setAccountNumber(anyString()).setBalance(randomBalance())
                .build());
      }
      cardNumberAccountNumberAccountRecordTable.put(loginRecord.getCard().getCardNumber(),
          accountRecordList.stream()
              .collect(Collectors.toMap(IAccountRecord::getAccountNumber, account -> account)));
      IAccountsRecord accountRecord = AccountsRecord.newBuilder().setAccounts(
          accountRecordList.stream().map(iAccountRecord -> AccountRecord.newBuilder()
              .setAccountNumber(iAccountRecord.getAccountNumber())
              .setBalance(iAccountRecord.getBalance()).build()).collect(Collectors.toList()))
          .setTimeStamp(System.currentTimeMillis()).build();
      accountsRecordTable.put(loginRecord, accountRecord);
    }
  }
}
