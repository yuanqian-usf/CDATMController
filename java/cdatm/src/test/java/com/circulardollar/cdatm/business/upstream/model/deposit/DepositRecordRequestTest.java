package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class DepositRecordRequestTest {

    @Test public void getTokenId() {
        assertNotNull(DepositRecordRequest.newBuilder()
            .setAccount(AccountRecord.newBuilder()
                .setAccountNumber(randomString())
                .setBalance(randomInt())
                .build())
            .setAmount(randomInt())
            .setTimeStamp(randomLong())
            .setTokenId(randomString())
            .build()
            .getTokenId());
    }

    @Test public void newBuilder() {
        assertNotNull(DepositRecordRequest.newBuilder()
            .setAccount(AccountRecord.newBuilder()
                .setAccountNumber(randomString())
                .setBalance(randomInt())
                .build())
            .setAmount(randomInt())
            .setTimeStamp(randomLong())
            .setTokenId(randomString())
            .build());
    }

    @Test public void testToString() {
        assertNotNull(DepositRecordRequest.newBuilder()
            .setAccount(AccountRecord.newBuilder()
                .setAccountNumber(randomString())
                .setBalance(randomInt())
                .build())
            .setAmount(randomInt())
            .setTimeStamp(randomLong())
            .setTokenId(randomString())
            .build()
            .toString());
    }
}
