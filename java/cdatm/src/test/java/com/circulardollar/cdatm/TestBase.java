package com.circulardollar.cdatm;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestBase {

  public static final int REPEAT_TEST_ITERATION = 2;

  public static String randomString() {
    return UUID.randomUUID().toString();
  }

  public static List<String> testAccountNumber = Arrays.asList("", "0", "a");
  public static List<Integer> testResponseBody = Arrays.asList(0, 1, 2);
  public static List<IError> testError = Arrays.asList(Error.of(Error.class,
      Collections.singletonList("0")), Error.of(Error.class,
      Collections.singletonList("1")), Error.of(Error.class,
      Collections.singletonList("2")));
  public static List<Integer> testBalance = Arrays.asList(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
  public static List<String> testCardHolderName = Arrays.asList("", "Circular", "Dollar");
  public static List<String> testCardNumber = Arrays.asList("", "0", "2");
  public static List<String> testCardCVC = Arrays.asList("", "1", "222");
  public static List<String> testCardExpirationDate = Arrays.asList("", "04/21", "06/22");
  public static List<String> testPinNumber = Arrays.asList("", "Circular", "Dollar");
}
