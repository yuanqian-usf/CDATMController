package com.circulardollar.cdatm.business.upstream.model.account;

import static com.circulardollar.cdatm.TestBase.testAccountNumber;
import static com.circulardollar.cdatm.TestBase.testBalance;
import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;
import org.junit.Test;

public class AccountRecordTest {

  @Test(expected = NullPointerException.class)
  public void AccountRecord_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    AccountRecord.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setAccountNumber_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    AccountRecord.newBuilder().setAccountNumber(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void setBalance_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    AccountRecord.newBuilder().setBalance(null).build();
  }

  @Test
  public void getAccountNumber() {
    IntStream.range(0, testAccountNumber.size())
        .forEach(
            index -> {
              IAccountRecord account =
                  AccountRecord.newBuilder()
                      .setAccountNumber(testAccountNumber.get(index))
                      .setBalance(testBalance.get(index))
                      .build();
              assertEquals(testAccountNumber.get(index), account.getAccountNumber());
            });
  }

  @Test
  public void getBalance() {
    IntStream.range(0, testBalance.size())
        .forEach(
            index -> {
              IAccountRecord account =
                  AccountRecord.newBuilder()
                      .setAccountNumber(testAccountNumber.get(index))
                      .setBalance(testBalance.get(index))
                      .build();
              assertEquals(testBalance.get(index), account.getBalance());
            });
  }

  @Test
  public void newBuilder() {
    IntStream.range(0, testBalance.size())
        .forEach(
            index -> {
              IAccountRecord account =
                  AccountRecord.newBuilder()
                      .setAccountNumber(testAccountNumber.get(index))
                      .setBalance(testBalance.get(index))
                      .build();
              assertEquals(testBalance.get(index), account.getBalance());
            });
  }
}
