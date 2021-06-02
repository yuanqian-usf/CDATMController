package com.circulardollar.cdatm.business.downstream.model.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.auth.Login;
import org.junit.Test;

import static com.circulardollar.cdatm.utils.TestUtils.anyString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

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
            Account.newBuilder()
                .setAccountNumber(anyString())
                .setBalance(anyInt())
                .build(),
            anyInt(), anyLong())
            .getAmount());
    }

    @Test public void getTimeStamp() {
        assertNotNull(new Withdraw(
            Account.newBuilder()
                .setAccountNumber(anyString())
                .setBalance(anyInt())
                .build(),
            anyInt(), anyLong())
            .getTimeStamp());
    }

    @Test public void getAccount() {
        assertNotNull(new Withdraw(
            Account.newBuilder()
                .setAccountNumber(anyString())
                .setBalance(anyInt()).build(),
            anyInt(), anyLong())
            .getAccount());
    }

    @Test public void newBuilder() {
        assertNotNull(Withdraw.newBuilder().setAccount(
            Account.newBuilder().setAccountNumber(anyString()).setBalance(anyInt()).build()).setAmount(anyInt()).setTimeStamp(anyLong()).build());
    }

    @Test public void testToString() {
        assertNotNull(new Withdraw(
            Account.newBuilder()
                .setAccountNumber(anyString())
                .setBalance(anyInt()).build(),
            anyInt(), anyLong())
            .toString());
    }
}
