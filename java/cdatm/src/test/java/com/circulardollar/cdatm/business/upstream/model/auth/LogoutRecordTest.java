package com.circulardollar.cdatm.business.upstream.model.auth;

import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomLong;
import static org.junit.Assert.*;


public class LogoutRecordTest {

    @Test public void testToString() {
        assertNotNull(LogoutRecord.newBuilder().setTimeStamp(randomLong()).build().toString());
    }

    @Test public void getTimeStamp() {
        Long timeStamp = randomLong();
        assertEquals(timeStamp, LogoutRecord.newBuilder().setTimeStamp(timeStamp).build().getTimeStamp());
    }

    @Test public void newBuilder() {
        assertNotNull(LogoutRecord.newBuilder().setTimeStamp(randomLong()).build());
    }
}
