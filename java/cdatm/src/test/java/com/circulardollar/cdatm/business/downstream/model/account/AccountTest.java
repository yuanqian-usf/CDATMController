package com.circulardollar.cdatm.business.downstream.model.account;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

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
    assertNotNull(new Account(anyString(), anyInt()).getAccountNumber());
  }

  @Test
  public void getBalance() {
    assertNotNull(new Account(anyString(), anyInt()).getBalance());
  }

  @Test
  public void newBuilder() {
    assertNotNull(Account.newBuilder().setAccountNumber(anyString()).setBalance(anyInt()).build());
  }
}
