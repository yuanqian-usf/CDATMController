package com.circulardollar.cdatm.business.downstream.model.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertNotNull;

public class WithdrawTest {

    @Test(expected = NullPointerException.class)
    public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Withdraw.newBuilder().build();
    }

    @Test(expected = NullPointerException.class)
    public void setAccount_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Withdraw.newBuilder().setAccount(null);
    }

    @Test(expected = NullPointerException.class)
    public void setAmount_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Withdraw.newBuilder().setAmount(null);
    }

    @Test(expected = NullPointerException.class)
    public void setTimeStamp_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Withdraw.newBuilder().setTimeStamp(null);
    }

    @Test public void getAmount() {
        assertNotNull(new Withdraw(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(),
            randomInt(), randomLong()).getAmount());
    }

    @Test public void getTimeStamp() {
        assertNotNull(new Withdraw(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(),
            randomInt(), randomLong()).getTimeStamp());
    }

    @Test public void getAccount() {
        assertNotNull(new Withdraw(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(),
            randomInt(), randomLong()).getAccount());
    }

    @Test public void newBuilder() {
        assertNotNull(Withdraw.newBuilder().setAccount(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build());
    }

    @Test public void testToString() {
        assertNotNull(new Withdraw(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build(),
            randomInt(), randomLong()).toString());
    }
}
