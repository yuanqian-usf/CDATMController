package com.circulardollar.cdatm.business.downstream.model.auth;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class AuthTest {
    @Test(expected = NullPointerException.class)
    public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Auth.newBuilder().build();
    }

    @Test(expected = NullPointerException.class)
    public void setTimeStamp_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Auth.newBuilder().setTimeStamp(null);
    }

    @Test(expected = NullPointerException.class)
    public void setAccounts_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Auth.newBuilder().setAccounts(null);
    }

    @Test(expected = NullPointerException.class)
    public void setTokenId_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Auth.newBuilder().setTokenId(null);
    }


    @Test public void newBuilder() {
        assertNotNull(
            Auth.newBuilder()
                .setTokenId(anyString())
                .setTimeStamp(anyLong())
                .setAccounts(anyList())
                .build()
        );
    }

    @Test public void getTokenId() {
        assertNotNull(new Auth(anyString(), anyList(), anyLong()).getTokenId());
    }

    @Test public void getAccounts() {
        assertNotNull(new Auth(anyString(), anyList(), anyLong()).getAccounts());
    }

    @Test public void getTimeStamp() {
        assertNotNull(new Auth(anyString(), anyList(), anyLong()).getTimeStamp());
    }

    @Test public void testToString() {
        assertNotNull(new Auth(anyString(), anyList(), anyLong()).toString());
    }
}
