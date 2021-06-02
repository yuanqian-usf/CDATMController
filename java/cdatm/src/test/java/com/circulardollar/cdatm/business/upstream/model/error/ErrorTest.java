package com.circulardollar.cdatm.business.upstream.model.error;

import com.google.gson.Gson;
import java.util.List;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ErrorTest {

    @Test public void of() {
    }

    @Test public void getErrorCode() {
    }

    @Test public void getErrorMessages() {
    }


    @Test public void testGson_on_success() {
        Gson gson = new Gson();
        String json = "{\"errorCode\":404,\"errorMessages\":[\"invalid card or pin\"]}";
        IError response = null;
        try {
            response = gson.fromJson(json, Error.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        System.out.println(response.getErrorCode());
        assertNotNull(response.getErrorMessages());
        System.out.println(response.getErrorMessages());

    }

}
