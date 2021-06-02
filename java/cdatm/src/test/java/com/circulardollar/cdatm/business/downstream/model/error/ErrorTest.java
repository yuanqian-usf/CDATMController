package com.circulardollar.cdatm.business.downstream.model.error;

import java.util.List;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;

public class ErrorTest {

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
        assertNotNull(new Error(anyInt(), anyList()).getErrorCode());
    }

    @Test public void getErrorMessages() {
        assertNotNull(new Error(anyInt(), anyList()).getErrorMessages());
    }

    @Test public void testToString() {
        assertNotNull(new Error(anyInt(), anyList()).toString());
    }
}
