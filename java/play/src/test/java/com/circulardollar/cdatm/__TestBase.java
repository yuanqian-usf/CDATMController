package com.circulardollar.cdatm;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.mapper.auth.LoginMapper;
import com.circulardollar.cdatm.business.mapper.card.CardMapper;
import com.circulardollar.cdatm.business.mapper.pin.PinMapper;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.LoginRecord;
import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.config.IATMConfigurations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class __TestBase {

  public static int RANDOM_TEST_ITERATION = 4;
  public static int REPEAT_TEST_ITERATION = 2;
  public static List<String> testAccountNumber = Arrays.asList("", "0", "a");
  public static List<Integer> testBalance = Arrays.asList(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
  public static List<String> testCardHolderName = Arrays.asList("", "Circular", "Dollar");
  public static List<String> testCardNumber = Arrays.asList("", "0", "2");
  public static List<String> testCardCVC = Arrays.asList("", "1", "222");
  public static List<String> testCardExpirationDate = Arrays.asList("", "04/21", "06/22");
  static Random random = new Random();

  public static Random random() {
    return random;
  }

  public static String anyString() {
    return "";
  }

  public static String emptyString() {
    return "";
  }

  public static String randomString() {
    return UUID.randomUUID().toString();
  }

  public static String randomLengthRandomNumber(int min, int max) {
    return IntStream.range(0, randomNumber(max - min) + min)
        .mapToObj(index -> randomDigit().toString())
        .collect(Collectors.joining());
  }

  public static String fixedLengthRandomNumber(int range) {
    return fixedLengthRandomNumber(0, range);
  }

  public static String fixedLengthRandomNumber(int start, int range) {
    return IntStream.range(start, range)
        .mapToObj(index -> randomDigit().toString())
        .collect(Collectors.joining());
  }

  public static Integer anyInteger() {
    return 0;
  }

  public static Integer randomDigit() {
    return random().nextInt(10);
  }

  public static Integer randomBalance() {
    long num = (long) (random().nextInt(Integer.MAX_VALUE) * 1.3f);
    boolean negFlag = random().nextBoolean();
    num *= negFlag ? -1 : 1;
    if (num > Integer.MAX_VALUE) return Integer.MAX_VALUE;
    if (num < Integer.MIN_VALUE) return Integer.MIN_VALUE;
    return (int) num;
  }

  public static Integer randomNumber(int end) {
    return random().nextInt(end);
  }

  public static ICard validRandomCard() {
    return randomCard(
        IATMConfigurations.MIN_CARD_NUMBER_LENGTH, IATMConfigurations.MAX_CARD_NUMBER_LENGTH);
  }

  public static ICardRecord validRandomCardRecord() {
    return CardMapper.up(validRandomCard());
  }

  public static ICard randomCard(Integer minLength, Integer maxLength) {
    return Card.newBuilder()
        .setHolderName(anyString())
        .setCardNumber(randomLengthRandomNumber(minLength, maxLength))
        .setCvc(anyString())
        .setExpirationDate(anyString())
        .build();
  }

  public static IPin validRandomPin() {
    return Pin.newBuilder()
        .setPinNumber(
            randomLengthRandomNumber(
                IATMConfigurations.MIN_PIN_LENGTH, IATMConfigurations.MAX_PIN_LENGTH))
        .build();
  }

  public static IPinRecord validRandomPinRecord() {
    return PinMapper.up(validRandomPin());
  }

  public static void generateBankDB(
      Map<ILoginRecord, IAccountsRecord> accountsRecordTable,
      Map<String, IPinRecord> cardNumberLoginRecordsTable,
      Map<String, Map<String, IAccountRecord>> cardNumberAccountNumberAccountRecordTable) {
    accountsRecordTable.clear();
    cardNumberLoginRecordsTable.clear();
    cardNumberAccountNumberAccountRecordTable.clear();
    double loginRecordsCount = Math.pow(RANDOM_TEST_ITERATION, 2);
    double totalAccountsForAssociating = Math.pow(RANDOM_TEST_ITERATION, 3);
    int logOfTotalAccountsPerAccount = (int) Math.log(totalAccountsForAssociating);
    for (int i = 0; i < loginRecordsCount; i++) {
      ILoginRecord loginRecord =
          LoginRecord.newBuilder()
              .setCard(validRandomCardRecord())
              .setPin(validRandomPinRecord())
              .build();
      cardNumberLoginRecordsTable.put(loginRecord.getCard().getCardNumber(), loginRecord.getPin());
      // Guarantee at least 1 is available
      int accountAmountForThisCard = randomNumber(logOfTotalAccountsPerAccount) + 1;

      List<AccountRecord> accountRecordList = new ArrayList<>();

      for (int j = 0; j < accountAmountForThisCard; j++) {
        accountRecordList.add(
            AccountRecord.newBuilder()
                .setAccountNumber(randomString())
                .setBalance(randomBalance())
                .build());
      }
      cardNumberAccountNumberAccountRecordTable.put(
          loginRecord.getCard().getCardNumber(),
          accountRecordList.stream()
              .collect(Collectors.toMap(IAccountRecord::getAccountNumber, account -> account)));
      IAccountsRecord accountRecord =
          AccountsRecord.newBuilder()
              .setAccounts(accountRecordList)
              .setTimeStamp(System.currentTimeMillis())
              .build();
      accountsRecordTable.put(loginRecord, accountRecord);
    }
  }

  public static String getRandomExistingAccountNumberFromAccountsRecords(
      Map<String, Map<String, IAccountRecord>> cardNumberAccountsTable) {
    int randomIndex = random().nextInt(cardNumberAccountsTable.size());
    List<String> randomCardNumbers = new ArrayList<>(cardNumberAccountsTable.keySet());
    String randomCardNumber = randomCardNumbers.get(randomIndex);

    Map<String, IAccountRecord> accountRecordMap = cardNumberAccountsTable.get(randomCardNumber);
    List<String> randomAccountNumbers = new ArrayList<>(accountRecordMap.keySet());
    return randomAccountNumbers.get(random().nextInt(randomAccountNumbers.size()));
  }

  public static String getRandomExistingAccountNumberFromAccounts(List<IAccount> accounts) {
    return getRandomExistingAccountFromAccounts(accounts).getAccountNumber();
  }

  public static IAccount getRandomExistingAccountFromAccounts(List<IAccount> accounts) {
    return accounts.get(random().nextInt(accounts.size()));
  }

  public static ILoginRecord getRandomExistingLoginRecord(List<ILoginRecord> loginRecords) {
    return loginRecords.get(random().nextInt(loginRecords.size()));
  }

  public static ILogin getRandomExistingLogin(Set<ILoginRecord> loginRecords) {
    return LoginMapper.down(getRandomExistingLoginRecord(new ArrayList<>(loginRecords)));
  }

  /**
   * Should not check empty list
   *
   * @param accountNumbers
   * @return
   */
  public static String getRandomAccountNumberForSelection(List<String> accountNumbers) {
    if (accountNumbers.size() == 0) return null;
    return accountNumbers.get(randomNumber(accountNumbers.size()));
  }

  public static Pair<String, String> randomCardNumberWithValidDepositBalance(
      Map<String, Map<String, IAccountRecord>> cardNumberAccountNumberAccountRecordTable,
      boolean repeat) {
    // CardNo    //AccountNo
    for (Map.Entry<String, Map<String, IAccountRecord>> entry :
        cardNumberAccountNumberAccountRecordTable.entrySet()) {
      String cardNumber = entry.getKey();
      for (IAccountRecord account : entry.getValue().values()) {
        if (account.getBalance() < (repeat ? 0 : Integer.MAX_VALUE)) {
          return new ImmutablePair<>(cardNumber, account.getAccountNumber());
        }
      }
    }
    return null;
  }

  public static Pair<String, String> randomCardNumberWithValidWithdrawBalance(
      Map<String, Map<String, IAccountRecord>> cardNumberAccountNumberAccountRecordTable,
      boolean repeat) {
    // CardNo    //AccountNo
    for (Map.Entry<String, Map<String, IAccountRecord>> entry :
        cardNumberAccountNumberAccountRecordTable.entrySet()) {
      String cardNumber = entry.getKey();
      for (IAccountRecord account : entry.getValue().values()) {
        if (repeat ? account.getBalance() <= Integer.MAX_VALUE : account.getBalance() > 0) {
          return new ImmutablePair<>(cardNumber, account.getAccountNumber());
        }
      }
    }
    return null;
  }

  public static ILoginRecord getCardByCardNumber(
      Map<ILoginRecord, IAccountsRecord> accountsRecordTable, String cardNumber) {
    for (ILoginRecord loginRecord : accountsRecordTable.keySet()) {
      if (cardNumber.equals(loginRecord.getCard().getCardNumber())) {
        return loginRecord;
      }
    }
    return null;
  }
}
