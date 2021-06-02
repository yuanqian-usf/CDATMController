package com.circulardollar.cdatm.business.upstream.model.auth;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthRecordTest {

    @Test public void newBuilder() {
    }

    @Test public void getTokenId() {
        String a = "";
    }

    @Test public void getAccounts() {
    }

    @Test public void getTimeStamp() {
    }

    @Test public void testGson_on_success() {
        Gson gson = new Gson();
        String json = "{\n" + "    \"accounts\": [\n" + "        {\n"
            + "            \"accountNumber\": \"8babde61-f4ad-4538-9d5c-e8de824c3584\",\n"
            + "            \"balance\": 668994890\n" + "        },\n" + "        {\n"
            + "            \"accountNumber\": \"c964d041-0734-4bb5-a7de-8ff295296a81\",\n"
            + "            \"balance\": -874867810\n" + "        },\n" + "        {\n"
            + "            \"accountNumber\": \"dedb1302-b2b2-4f13-b236-e1ba2092b4dd\",\n"
            + "            \"balance\": 652275718\n" + "        }\n" + "    ],\n"
            + "    \"tokenId\": \"50874b77-8a3c-45bd-80b0-5964849e4b5c\",\n"
            + "    \"timeStamp\": 1622264278263\n" + "}";
        AuthRecord response = null;
        try {
            response = gson.fromJson(json, AuthRecord.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        assertNotNull(response);

    }

}
