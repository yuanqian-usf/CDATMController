package com.circulardollar.cdatm.business.downstream.validation.state;

import com.circulardollar.cdatm.business.downstream.model.auth.Auth;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.state.ATMStateController;
import com.circulardollar.cdatm.state.IATMStateController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StateControllerValidatorTest {

    @Test
    public void null_whenExceptionThrown_thenExpectationSatisfied() {
        Optional<IError> optionalIError = new StateControllerValidator().validate(null);
        assertTrue(optionalIError.isPresent());
    }

    @Test
    public void notNull_whenExceptionThrown_thenExpectationSatisfied() {
        Optional<IError> optionalIError = new StateControllerValidator().validate(new ATMStateController());
        assertFalse(optionalIError.isPresent());
    }
}
