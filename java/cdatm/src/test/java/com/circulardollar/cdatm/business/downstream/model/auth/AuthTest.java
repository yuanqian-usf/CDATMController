package com.circulardollar.cdatm.business.downstream.model.auth;

import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

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
                .setTokenId(randomString())
                .setTimeStamp(randomLong())
                .setAccounts(randomList())
                .build()
        );
    }

    @Test public void getTokenId() {
        assertNotNull(new Auth(randomString(), randomList(), randomLong()).getTokenId());
    }

    @Test public void getAccounts() {
        assertNotNull(new Auth(randomString(), randomList(), randomLong()).getAccounts());
    }

    @Test public void getTimeStamp() {
        assertNotNull(new Auth(randomString(), randomList(), randomLong()).getTimeStamp());
    }

    @Test public void testToString() {
        assertNotNull(new Auth(randomString(), randomList(), randomLong()).toString());
    }
}
