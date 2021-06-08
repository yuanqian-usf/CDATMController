package com.circulardollar.cdatm.business.upstream.model.error;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.google.gson.Gson;
import java.util.List;
import org.junit.Test;

import java.util.Arrays;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static org.junit.Assert.*;

public class ErrorRecordTest {
//
//    @Test public void implementation() {
//        IErrorRecord error = new ErrorRecord(randomInt(), randomList());
//        assertNotNull(error.getErrorCode());
//        assertNotNull(error.getErrorMessages());
//    }

    @Test public void of() {
        String errorMessage = "0";
        String errorMessage1 = "1";
        Class<Object> errorCode = Object.class;
        List<String> expectedMessages = Arrays.asList(errorMessage, errorMessage1);
        IErrorRecord actual = ErrorRecord.of(errorCode, expectedMessages);
        assertNotNull(actual.getErrorCode());
        assertNotNull(actual.getErrorMessages());
        int hash = errorCode.hashCode();
        hash = errorMessage.hashCode() + hash * 31;
        hash = errorMessage1.hashCode() + hash * 31;
        assertEquals(hash, actual.getErrorCode().intValue());
    }


    @Test public void of_null() {
        String errorMessage = "0";
        String errorMessage1 = null;
        Class<Object> errorCode = Object.class;
        List<String> expectedMessages = Arrays.asList(errorMessage, errorMessage1);
        IErrorRecord actual = ErrorRecord.of(errorCode, expectedMessages);
        assertNotNull(actual.getErrorCode());
        assertNotNull(actual.getErrorMessages());
        int hash = errorCode.hashCode();
        hash = errorMessage.hashCode() + hash * 31;
        hash = 31 + hash * 31;
        assertEquals(hash, actual.getErrorCode().intValue());
    }

    @Test public void newBuilder() {
        assertNotNull(ErrorRecord.newBuilder().setErrorCode(randomInt()).setErrorMessages(randomList()).build());
    }

    @Test public void getErrorCode() {
        assertNotNull(new ErrorRecord(randomInt(), randomList()).getErrorCode());
    }

    @Test public void getErrorMessages() {
        assertNotNull(new ErrorRecord(randomInt(), randomList()).getErrorMessages());
    }


    @Test public void testGson_on_success() {
        Gson gson = new Gson();
        String json = "{\"errorCode\":404,\"errorMessages\":[\"invalid card or pin\"]}";
        IErrorRecord response = null;
        try {
            response = gson.fromJson(json, ErrorRecord.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        System.out.println(response.getErrorCode());
        assertNotNull(response.getErrorMessages());
        System.out.println(response.getErrorMessages());

    }

    @Test public void testToString() {
        assertNotNull(new ErrorRecord(randomInt(), randomList()).toString());
    }

}
