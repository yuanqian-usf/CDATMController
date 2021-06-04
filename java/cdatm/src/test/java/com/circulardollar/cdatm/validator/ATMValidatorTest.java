package com.circulardollar.cdatm.validator;

import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.business.downstream.validation.withdraw.WithdrawValidator;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.config.IATMConfigurations;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ATMValidatorTest {

    @Test(expected = IllegalArgumentException.class) public void getATMConfigurations_null_whenExceptionThrown_thenExpectationSatisfied() {
        assertNotNull(new ATMValidator(ATMConfigurations.newBuilder().setMinWithdrawAmount(-1)
            .build(), null, null, null, null, null, null, null, null)
            .getATMConfigurations());
    }

    @Test(expected = NullPointerException.class) public void newBuilder_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.Builder builder = ATMValidator.newBuilder(null);
    }


    @Test(expected = NullPointerException.class) public void initValidator_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build().initValidator(null, null);
    }
    @Test(expected = NullPointerException.class) public void validateConfigurations_null_whenExceptionThrown_thenExpectationSatisfied() {
        assertTrue(new ATMValidator(null, null, null, null, null, null, null, null, null)
            .validateConfigurations(null).isPresent());
    }

    @Test public void validateConfigurations() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateConfigurations(null).isPresent());
    }

    @Test public void validateStateController() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateStateController(null).isPresent());
    }

    @Test public void validateSessionController() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateSessionController(null).isPresent());
    }

    @Test public void validateCard() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateCard(null).isPresent());
    }

    @Test public void validatePin() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validatePin(null).isPresent());
    }

    @Test public void validateAccount() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateAccount(null).isPresent());
    }

    @Test public void validateDeposit() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateDeposit(null).isPresent());
    }

    @Test public void validateWithdraw() {
        assertTrue(new ATMValidator(ATMConfigurations.newBuilder().build(), null, null, null, null, null, null, null, null)
            .validateWithdraw(null).isPresent());
    }

    @Test public void getATMConfigurations() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build().getATMConfigurations());
    }

    @Test public void newBuilder() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build());
    }

    @Test public void initValidator_default() {
        IValidator<Object> actual = target -> Optional.empty();
        assertEquals(actual, ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build().initValidator(null,
            actual));
    }

    @Test public void initValidator() {
        IValidator<Object> actual = new IValidator<Object>() {
            @Override public Optional<IError> validate(Object target) {
                return Optional.empty();
            }
        };

        IValidator<Object> defaultValidator = new IValidator<Object>() {
            @Override public Optional<IError> validate(Object target) {
                return Optional.empty();
            }
        };
        assertEquals(actual, ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build().initValidator(actual,
            defaultValidator));
    }


    @Test(expected = NullPointerException.class)
    public void Builder_validateConfigurations_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setConfigurationsValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validateStateController_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setStateControllerValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validateSessionController_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setSessionControllerValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validateCard_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setCardValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validatePin_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setPinValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validateAccount_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setAccountValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validateDeposit_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setDepositValidator(null)
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void Builder_validateWithdraw_null_whenExceptionThrown_thenExpectationSatisfied() {
        ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setWithdrawValidator(null)
            .build();
    }

    @Test public void Builder_setConfigurationsValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setConfigurationsValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setStateControllerValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setStateControllerValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setSessionControllerValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setSessionControllerValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setCardValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setCardValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setPinValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setPinValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setAccountValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setAccountValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setDepositValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setDepositValidator(target -> Optional.empty())
            .build());
    }
    @Test public void Builder_setWithdrawValidator() {
        assertNotNull(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build())
            .setWithdrawValidator(target -> Optional.empty())
            .build());

    }
}
