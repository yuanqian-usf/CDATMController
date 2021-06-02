package com.circulardollar.cdatm.business.downstream.model.deposit;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertNotNull;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import org.junit.Test;

public class DepositTest {

  @Test(expected = NullPointerException.class)
  public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Deposit.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setAccount_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Deposit.newBuilder().setAccount(null);
  }

  @Test(expected = NullPointerException.class)
  public void setAmount_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Deposit.newBuilder().setAmount(null);
  }

  @Test(expected = NullPointerException.class)
  public void setTimeStamp_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Deposit.newBuilder().setTimeStamp(null).build();
  }

  @Test
  public void getAccount() {
    assertNotNull(new Deposit(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(), randomInt(), randomLong()).getAccount());
  }

  @Test
  public void getAmount() {
    assertNotNull(new Deposit(
        Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(), randomInt(), randomLong()).getAmount());
  }

  @Test
  public void getTimeStamp() {
    assertNotNull(new Deposit(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(), randomInt(), randomLong()).getTimeStamp());
  }

  @Test
  public void newBuilder() {
    assertNotNull(
        Deposit.newBuilder()
            .setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt())
            .setTimeStamp(randomLong())
            .build());
  }

  @Test
  public void testToString() {
    assertNotNull(
        Deposit.newBuilder()
            .setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt())
            .setTimeStamp(randomLong())
            .build().toString());
  }
}
