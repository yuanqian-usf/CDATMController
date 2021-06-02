package com.circulardollar.cdatm.business.downstream.model.deposit;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

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
    assertNotNull(new Deposit(Account.newBuilder().setAccountNumber(randomString()).setBalance(anyInt()).build(), anyInt(), anyLong()).getAccount());
  }

  @Test
  public void getAmount() {
    assertNotNull(new Deposit(
        Account.newBuilder().setAccountNumber(randomString()).setBalance(anyInt()).build(), anyInt(), anyLong()).getAmount());
  }

  @Test
  public void getTimeStamp() {
    assertNotNull(new Deposit(Account.newBuilder().setAccountNumber(randomString()).setBalance(anyInt()).build(), anyInt(), anyLong()).getTimeStamp());
  }

  @Test
  public void newBuilder() {
    assertNotNull(
        Deposit.newBuilder()
            .setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(anyInt()).build())
            .setAmount(anyInt())
            .setTimeStamp(anyLong())
            .build());
  }
}
