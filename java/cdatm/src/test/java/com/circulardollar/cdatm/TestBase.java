package com.circulardollar.cdatm;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.config.IATMConfigurations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestBase {

  public static final int REPEAT_TEST_ITERATION = 2;
  public static List<String> testAccountNumber = Arrays.asList("", "0", "a");
  public static List<Integer> testResponseBody = Arrays.asList(0, 1, 2);
  public static List<IError> testError =
      Arrays.asList(
          Error.of(Error.class, Collections.singletonList("0")),
          Error.of(Error.class, Collections.singletonList("1")),
          Error.of(Error.class, Collections.singletonList("2")));
  public static List<Integer> testBalance = Arrays.asList(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
  public static List<String> testCardHolderName = Arrays.asList("", "Circular", "Dollar");
  public static List<String> testCardNumber = Arrays.asList("", "0", "2");
  public static List<String> testCardCVC = Arrays.asList("", "1", "222");
  public static List<String> testCardExpirationDate = Arrays.asList("", "04/21", "06/22");
  public static List<String> testPinNumber = Arrays.asList("", "Circular", "Dollar");

  public static String randomString() {
    return UUID.randomUUID().toString();
  }

  public static boolean randomBoolean() {
    return new Random().nextBoolean();
  }

  public static int randomInt() {
    return new Random().nextInt(100);
  }

  public static int randomPostiveInt() {
    return new Random().nextInt(99) + 1;
  }

  public static long randomLong() {
    return new Random().nextLong();
  }

  public static <T> List<T> randomList() {
    return new ArrayList<>();
  }

  public static <T> Set<T> randomSet() {
    return new HashSet<>();
  }

  public static <K, V> Map<K, V> randomMap() {
    return new HashMap<>();
  }

  public static Integer randomNumber(int end) {
    return new Random().nextInt(end);
  }

  public static Integer randomDigit() {
    return new Random().nextInt(10);
  }

  public static String randomCardNumber(int minLen, int maxLen) {
    return randomLengthRandomNumber(minLen, maxLen);
  }

  public static String validRandomCardNumber() {
    return randomLengthRandomNumber(
        IATMConfigurations.MIN_CARD_NUMBER_LENGTH, IATMConfigurations.MAX_CARD_NUMBER_LENGTH);
  }

  public static String validRandomPinNumber() {
    return randomLengthRandomNumber(
        IATMConfigurations.MIN_PIN_LENGTH, IATMConfigurations.MAX_PIN_LENGTH);
  }

  public static String randomLengthRandomNumber(int min, int max) {
    return IntStream.range(0, randomNumber(max - min) + min)
        .mapToObj(index -> randomDigit().toString())
        .collect(Collectors.joining());
  }

  public static String validEmptyJsonString() {
    return "{}";
  }
}
