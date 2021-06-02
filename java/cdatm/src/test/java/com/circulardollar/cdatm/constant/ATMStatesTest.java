package com.circulardollar.cdatm.constant;

import org.junit.Test;

import static org.junit.Assert.*;

public class ATMStatesTest {

    @Test public void getId() {
        assertEquals(0, ATMStates.UNSPECIFIED.getId().intValue());
        assertEquals(1, ATMStates.ACTIVE.getId().intValue());
        assertEquals(2, ATMStates.INSERT_CARD.getId().intValue());
        assertEquals(3, ATMStates.VERIFY_PIN.getId().intValue());
        assertEquals(4, ATMStates.SELECT_ACCOUNT.getId().intValue());
        assertEquals(5, ATMStates.CHECK_BALANCE.getId().intValue());
        assertEquals(6, ATMStates.DEPOSIT.getId().intValue());
        assertEquals(7, ATMStates.WITHDRAW.getId().intValue());
    }

    @Test public void getWeight() {
        assertEquals(0, ATMStates.UNSPECIFIED.getWeight().intValue());
        assertEquals(1, ATMStates.ACTIVE.getWeight().intValue());
        assertEquals(2, ATMStates.INSERT_CARD.getWeight().intValue());
        assertEquals(3, ATMStates.VERIFY_PIN.getWeight().intValue());
        assertEquals(4, ATMStates.SELECT_ACCOUNT.getWeight().intValue());
        assertEquals(5, ATMStates.CHECK_BALANCE.getWeight().intValue());
        assertEquals(5, ATMStates.DEPOSIT.getWeight().intValue());
        assertEquals(5, ATMStates.WITHDRAW.getWeight().intValue());
    }


    @Test public void ofId() {
        assertEquals(ATMStates.UNSPECIFIED, ATMStates.ofId(10));
    }
}
