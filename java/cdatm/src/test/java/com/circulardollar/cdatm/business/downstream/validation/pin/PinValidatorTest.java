package com.circulardollar.cdatm.business.downstream.validation.pin;

import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.config.IATMConfigurations;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class PinValidatorTest {

    @Test public void validate_01() {
        assertTrue(
            new PinValidator(
                ATMConfigurations.newBuilder().build())
                .validate(null).isPresent());
    }

    @Test public void validate_02() {
        assertFalse(new PinValidator(ATMConfigurations.newBuilder().build()).validate(
            Pin.newBuilder().setPinNumber(
                validRandomPinNumber()).build())
            .isPresent());
    }

    @Test public void validate_03() {
        assertTrue(new PinValidator(ATMConfigurations.newBuilder().build()).validate(
            Pin.newBuilder().setPinNumber(randomLengthRandomNumber(0, IATMConfigurations.MIN_PIN_LENGTH)
                ).build())
            .isPresent());
    }

    @Test public void validate_04() {
        assertTrue(new PinValidator(ATMConfigurations.newBuilder().build()).validate(
            Pin.newBuilder().setPinNumber(randomLengthRandomNumber(IATMConfigurations.MAX_PIN_LENGTH + 1, IATMConfigurations.MAX_PIN_LENGTH + 2 )
            ).build())
            .isPresent());
    }

    @Test public void validate_05() {
        assertTrue(new PinValidator(ATMConfigurations.newBuilder().build()).validate(
            Pin.newBuilder().setPinNumber(
                "").build())
            .isPresent());
    }
}
