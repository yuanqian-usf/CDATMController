package com.circulardollar.cdatm.business.downstream.validation.account;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountValidatorTest {

    @Test public void validate_01() {
        assertTrue(new AccountValidator().validate(
            null)
            .isPresent());
    }

    @Test public void validate_02() {
        assertFalse(new AccountValidator().validate(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .isPresent());
    }

    @Test public void validate_03() {
        assertTrue(new AccountValidator().validate(
            Account.newBuilder().setAccountNumber("").setBalance(randomInt()).build())
            .isPresent());
    }
}
