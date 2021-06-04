package com.circulardollar.cdatm.business.downstream.validation.deposit;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.validation.account.AccountValidator;
import com.circulardollar.cdatm.business.downstream.validation.card.CardValidator;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.config.IATMConfigurations;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class DepositValidatorTest {

    @Test public void validate_01() {
        assertTrue(
            new DepositValidator(ATMConfigurations.newBuilder().build()).validate(null).isPresent());
    }

    @Test public void validate_02() {
        assertFalse(new DepositValidator(ATMConfigurations.newBuilder().build()).validate(
            Deposit.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
                .setAmount(randomInt()).setTimeStamp(randomLong()).build())
            .isPresent());
    }

    @Test public void validate_03() {
        assertTrue(new DepositValidator(ATMConfigurations.newBuilder().build()).validate(
            Deposit.newBuilder().setAccount(Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
                .setAmount(-1).setTimeStamp(randomLong()).build())
            .isPresent());
    }

    @Test public void validate_04() {
        assertTrue(
            new DepositValidator(ATMConfigurations.newBuilder().setMinDepositAmount(Integer.MAX_VALUE).build())
                .validate(Deposit.newBuilder().setAccount(Account.newBuilder()
                    .setAccountNumber(randomString()).setBalance(randomInt()).build())
                    .setAmount(randomInt() - 1)
                    .setTimeStamp(randomLong())
                    .build()).isPresent());
    }

    @Test public void validate_05() {
        assertTrue(
            new DepositValidator(ATMConfigurations.newBuilder().setMaxDepositAmount(Integer.MIN_VALUE).build())
                .validate(Deposit.newBuilder().setAccount(Account.newBuilder()
                    .setAccountNumber(randomString()).setBalance(randomInt()).build())
                    .setAmount(randomInt())
                    .setTimeStamp(randomLong())
                    .build()).isPresent());
    }
}
