package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.session.ATMSessionController;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.circulardollar.cdatm.constant.ATMStates.INSERT_CARD;
import static com.circulardollar.cdatm.constant.ATMStates.VERIFY_PIN;
import static org.junit.Assert.*;

public class ATMStateControllerTest {

    @Test public void getStateId() {
        assertEquals(new ATMStateController().getStateId().intValue(), 1);
    }

    @Test public void canGoToNextState() {
        assertTrue(new ATMStateController().canGoToNextState(2));
        assertFalse(new ATMStateController().canGoToNextState(0));
    }

    @Test public void nextState() {
        ATMStateController atmStateController = new ATMStateController();
        atmStateController.nextState(2);
        assertEquals(atmStateController.getStateId().intValue(), 2);
    }

    @Test public void availableStates() {
        assertEquals(new ATMStateController().availableStates(), new HashSet<>(Collections.singletonList(INSERT_CARD)));
    }
}
