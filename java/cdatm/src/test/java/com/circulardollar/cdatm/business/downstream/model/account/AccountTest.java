package com.circulardollar.cdatm.business.downstream.model.account;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AccountTest {

  @Test(expected = NullPointerException.class)
  public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Account.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setAccountNumber_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Account.newBuilder().setAccountNumber(null);
  }

  @Test(expected = NullPointerException.class)
  public void setBalance_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Account.newBuilder().setBalance(null);
  }

  @Test
  public void getAccountNumber() {
    assertNotNull(new Account(randomString(), randomInt()).getAccountNumber());
  }

  @Test
  public void getBalance() {
    assertNotNull(new Account(randomString(), randomInt()).getBalance());
  }

  @Test
  public void newBuilder() {
    assertNotNull(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
  }
}
