package com.circulardollar.cdatm.business.upstream.model.auth;

import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

public class AuthRecordV2Test {

    @Test public void testToString() {
        assertNotNull(AuthRecordV2.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .build().toString());
    }

    @Test public void newBuilder() {
        assertNotNull(AuthRecordV2.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .build());
    }

    @Test public void getTokenId() {
        assertNotNull(AuthRecordV2.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .build().getTokenId());
    }

    @Test public void getTimeStamp() {
        assertNotNull(AuthRecordV2.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .build().getTimeStamp());
    }
}
