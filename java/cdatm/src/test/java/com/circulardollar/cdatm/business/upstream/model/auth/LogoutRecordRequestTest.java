package com.circulardollar.cdatm.business.upstream.model.auth;

import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

public class LogoutRecordRequestTest {

    @Test public void getTokenId() {
        assertNotNull(LogoutRecordRequest.newBuilder().setTimeStamp(randomLong()).setTokenId(randomString()).build().getTokenId());
    }

    @Test public void newBuilder() {
        assertNotNull(LogoutRecordRequest.newBuilder().setTimeStamp(randomLong()).setTokenId(randomString()).build());
    }
}
