package com.circulardollar.cdatm.business.downstream.model.error;

import java.util.List;
import org.junit.Test;

import java.util.Arrays;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static org.junit.Assert.*;

public class ErrorRecordTest {

    @Test public void of() {
        String errorMessage = "0";
        String errorMessage1 = "1";
        Class<Object> errorCode = Object.class;
        List<String> expectedMessages = Arrays.asList(errorMessage, errorMessage1);
        IError actual = Error.of(errorCode, expectedMessages);
        assertNotNull(actual.getErrorCode());
        assertNotNull(actual.getErrorMessages());
        int hash = errorCode.hashCode();
        hash = errorMessage.hashCode() + hash * 31;
        hash = errorMessage1.hashCode() + hash * 31;
        assertEquals(hash, actual.getErrorCode().intValue());
    }

    @Test public void getErrorCode() {
        assertNotNull(new Error(randomInt(), randomList()).getErrorCode());
    }

    @Test public void getErrorMessages() {
        assertNotNull(new Error(randomInt(), randomList()).getErrorMessages());
    }

    @Test public void testToString() {
        assertNotNull(new Error(randomInt(), randomList()).toString());
    }
}
