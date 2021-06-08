package com.circulardollar.cdatm.business.upstream.model.auth;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static com.circulardollar.cdatm.utils.network.InterfaceSerializer.interfaceSerializer;
import static org.junit.Assert.*;

public class AuthRecordTest {

    @Test(expected = NullPointerException.class)
    public void AuthRecord_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        AuthRecord.newBuilder().build();
    }

    @Test(expected = NullPointerException.class)
    public void setAccounts_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        AuthRecord.newBuilder().setAccounts(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void setTimeStamp_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        AuthRecord.newBuilder().setTimeStamp(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void setTokenId_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        AuthRecord.newBuilder().setTokenId(null).build();
    }


    @Test public void newBuilder() {
        assertNotNull(
            AuthRecord.newBuilder()
                .setTokenId(randomString())
                .setTimeStamp(randomLong())
                .setAccounts(randomList())
                .build()
        );
    }

    @Test public void getTokenId() {
        assertNotNull(AuthRecord.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .setAccounts(randomList())
            .build().getTokenId());
    }

    @Test public void getAccounts() {
        assertNotNull(AuthRecord.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .setAccounts(randomList())
            .build().getAccounts());
    }

    @Test public void getTimeStamp() {
        assertNotNull(AuthRecord.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .setAccounts(randomList())
            .build().getTimeStamp());
    }


    @Test public void testGson_on_success() {
        Gson gson = new GsonBuilder().registerTypeAdapter(IAccountRecord.class, interfaceSerializer(
            AccountRecord.class)).create();
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

    @Test
    public void testToString() {
        assertNotNull(AuthRecord.newBuilder()
            .setTokenId(randomString())
            .setTimeStamp(randomLong())
            .setAccounts(randomList())
            .build().toString());
    }

}
