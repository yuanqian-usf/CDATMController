package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class PureNetworkClientV2Test {

    @Test public void newBuilder() {
    }

    @Test public void verifyPin() {
    }

    @Test public void verifyPinV2() {
    }

    @Test public void getAccounts() {
    }

    @Test public void deposit() {
    }

    @Test public void withdraw() {
    }

    @Test public void logout() {
    }

    @Test public void handleResponse() {
    }

    @Test public void handleResponse_deposit() {
        String json = "{\n" + "    \"account\": {\n"
            + "        \"accountNumber\": \"e6b7c318-173b-4b8c-a1e2-efcd90767dfb\",\n"
            + "        \"balance\": -2147483647\n" + "    },\n" + "    \"amount\": 1,\n"
            + "    \"timeStamp\": 1622352444705\n" + "}";
        RemoteResponse.Builder<IDepositRecord> responseBuilder =
            RemoteResponse.newBuilder();
        new PureNetworkClientV2("").handleResponse(DepositRecord.class, responseBuilder, json);
        assertEquals(responseBuilder.build().getBody().getAccount().getAccountNumber(), "e6b7c318-173b-4b8c-a1e2-efcd90767dfb");
    }
}
