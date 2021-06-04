package com.circulardollar.cdatm.business.downstream.validation.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.auth.Auth;
import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.model.withdraw.Withdraw;
import com.circulardollar.cdatm.business.downstream.validation.deposit.DepositValidator;
import com.circulardollar.cdatm.config.ATMConfigurations;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class WithdrawValidatorTest {

    @Test public void validate_01() {
        assertTrue(
            new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(null).isPresent());
    }

    @Test public void validate_02() {
        assertFalse(new DepositValidator(ATMConfigurations.newBuilder().build()).validate(
            Deposit.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
                .setAmount(ATMConfigurations.MIN_WITHDRAW_AMOUNT).setTimeStamp(randomLong()).build())
            .isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void null_get_account_whenExceptionThrown_thenExpectationSatisfied() {
        new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            new IWithdraw() {
                @Override public IAccount getAccount() {
                    return null;
                }

                @Override public Integer getAmount() {
                    return null;
                }

                @Override public Long getTimeStamp() {
                    return null;
                }
            });
    }

    @Test(expected = NullPointerException.class)
    public void null_withdraw_whenExceptionThrown_thenExpectationSatisfied() {
        new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            Withdraw.newBuilder().setAccount(null).setAmount(randomInt()).setTimeStamp(randomLong()).build());
    }

    @Test(expected = NullPointerException.class)
    public void null_withdraw_getAccount_whenExceptionThrown_thenExpectationSatisfied() {
        new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            Withdraw.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build()).setAmount(null).setTimeStamp(randomLong()).build());
    }

    @Test public void validate_03() {
        assertTrue(new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            Withdraw.newBuilder().setAccount(Account.newBuilder().setAccountNumber("").setBalance(randomInt()).build()).setAmount(-1).setTimeStamp(randomLong()).build())
            .isPresent());
    }

    @Test public void validate_04() {
        int balance = randomInt();
        assertTrue(new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            Withdraw.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(balance).build()).setAmount(balance + 1).setTimeStamp(randomLong()).build())
            .isPresent());
    }

    @Test public void validate_05() {
        int withdrawAmount = ATMConfigurations.MAX_WITHDRAW_AMOUNT + 1;
        assertTrue(new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            Withdraw.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build()).setAmount(withdrawAmount).setTimeStamp(randomLong()).build())
            .isPresent());
    }

    @Test public void validate_06() {
        int withdrawAmount = ATMConfigurations.MIN_WITHDRAW_AMOUNT - 1;
        assertTrue(new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(
            Withdraw.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build()).setAmount(withdrawAmount).setTimeStamp(randomLong()).build())
            .isPresent());
    }


}
