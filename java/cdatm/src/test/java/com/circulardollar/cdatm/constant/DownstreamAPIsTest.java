package com.circulardollar.cdatm.constant;

import org.junit.Test;

import static org.junit.Assert.*;

public class DownstreamAPIsTest {


    @Test public void getCommand() {
        assertEquals("", DownstreamAPIs.UNSPECIFIED.getCommand());
        assertEquals("insertCard", DownstreamAPIs.INSERT_CARD.getCommand());
        assertEquals("verifyPin", DownstreamAPIs.VERIFY_PIN.getCommand());
        assertEquals("selectAccount", DownstreamAPIs.SELECT_ACCOUNT.getCommand());
        assertEquals("checkBalance", DownstreamAPIs.CHECK_BALANCE.getCommand());
        assertEquals("deposit", DownstreamAPIs.DEPOSIT.getCommand());
        assertEquals("withdraw", DownstreamAPIs.WITHDRAW.getCommand());
        assertEquals("ejectCard", DownstreamAPIs.EJECT_CARD.getCommand());
    }

    @Test public void getArgSize() {
        assertEquals(0, DownstreamAPIs.UNSPECIFIED.getArgSize());
        assertEquals(2, DownstreamAPIs.INSERT_CARD.getArgSize());
        assertEquals(2, DownstreamAPIs.VERIFY_PIN.getArgSize());
        assertEquals(2, DownstreamAPIs.SELECT_ACCOUNT.getArgSize());
        assertEquals(1, DownstreamAPIs.CHECK_BALANCE.getArgSize());
        assertEquals(2, DownstreamAPIs.DEPOSIT.getArgSize());
        assertEquals(2, DownstreamAPIs.WITHDRAW.getArgSize());
        assertEquals(1, DownstreamAPIs.EJECT_CARD.getArgSize());
    }

    @Test public void ofCommand() {
        assertEquals(DownstreamAPIs.ofCommand("hello"), DownstreamAPIs.UNSPECIFIED);
        assertEquals(DownstreamAPIs.ofCommand("insertCard"), DownstreamAPIs.INSERT_CARD);
    }
}
