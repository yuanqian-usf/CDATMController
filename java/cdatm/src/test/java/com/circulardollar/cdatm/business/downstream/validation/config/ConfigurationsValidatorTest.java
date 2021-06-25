package com.circulardollar.cdatm.business.downstream.validation.config;

import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.constant.APIVersions;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationsValidatorTest {

    @Test public void validate_01() {
        assertTrue(new ConfigurationsValidator().validate(
            null)
            .isPresent());
    }

    @Test public void validate_02() {
        Integer max_limit = IATMConfigurations.MAX_CARD_NUMBER_LENGTH;
        Integer max = max_limit + 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMaxCardNumberLength(max).build())
            .isPresent());
    }

    @Test public void validate_03() {
        Integer min_limit = IATMConfigurations.MIN_CARD_NUMBER_LENGTH;
        Integer min = min_limit - 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinCardNumberLength(min).build())
            .isPresent());
    }

    @Test public void validate_02_03() {
        Integer min_limit = IATMConfigurations.MIN_CARD_NUMBER_LENGTH;
        Integer max_limit = IATMConfigurations.MAX_CARD_NUMBER_LENGTH;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinCardNumberLength(max_limit)
                .setMaxCardNumberLength(min_limit).build())
            .isPresent());
    }

    @Test public void validate_04() {
        Integer max_limit = IATMConfigurations.MAX_PIN_LENGTH;
        Integer max = max_limit + 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMaxPinLength(max).build())
            .isPresent());
    }

    @Test public void validate_05() {
        Integer min_limit = IATMConfigurations.MIN_PIN_LENGTH;
        Integer min = min_limit - 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinPinLength(min).build())
            .isPresent());
    }

    @Test public void validate_04_05() {
        Integer min_limit = IATMConfigurations.MIN_PIN_LENGTH;
        Integer max_limit = IATMConfigurations.MAX_PIN_LENGTH;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinPinLength(max_limit)
                .setMaxPinLength(min_limit).build())
            .isPresent());
    }

    @Test public void validate_06() {
        Integer max_limit = IATMConfigurations.MAX_DEPOSIT_AMOUNT;
        Integer max = max_limit + 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMaxDepositAmount(max).build())
            .isPresent());
    }

    @Test public void validate_07() {
        Integer min_limit = IATMConfigurations.MIN_DEPOSIT_AMOUNT;
        Integer min = min_limit - 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinDepositAmount(min).build())
            .isPresent());
    }

    @Test public void validate_06_07() {
        Integer min_limit = IATMConfigurations.MIN_DEPOSIT_AMOUNT;
        Integer max_limit = IATMConfigurations.MAX_DEPOSIT_AMOUNT;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinDepositAmount(max_limit)
                .setMaxDepositAmount(min_limit).build())
            .isPresent());
    }

    @Test public void validate_08() {
        Integer max_limit = IATMConfigurations.MAX_WITHDRAW_AMOUNT;
        Integer max = max_limit + 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMaxWithdrawAmount(max).build())
            .isPresent());
    }

    @Test public void validate_09() {
        Integer min_limit = IATMConfigurations.MIN_WITHDRAW_AMOUNT;
        Integer min = min_limit - 1;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinWithdrawAmount(min).build())
            .isPresent());
    }

    @Test public void validate_08_09() {
        Integer min_limit = IATMConfigurations.MIN_WITHDRAW_AMOUNT;
        Integer max_limit = IATMConfigurations.MAX_WITHDRAW_AMOUNT;
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setMinWithdrawAmount(max_limit)
                .setMaxWithdrawAmount(min_limit).build())
            .isPresent());
    }

    @Test public void validate_10() {
        assertTrue(new ConfigurationsValidator().validate(
            ATMConfigurations.newBuilder().setAPIVersion(APIVersions.UNSPECIFIED).build())
            .isPresent());
    }
}
