package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertNotNull;

public class WithdrawRecordRequestTest {

    @Test public void getTokenId() {
        assertNotNull(WithdrawRecordRequest.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt())
                .build()).setAmount(randomInt()).setTimeStamp(randomLong()).setTokenId(randomString())
            .build().getTokenId());
    }

    @Test public void newBuilder() {
        assertNotNull(WithdrawRecordRequest.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt())
                .build()).setAmount(randomInt()).setTimeStamp(randomLong()).setTokenId(randomString())
            .build());
    }

    @Test public void testToString() {
        assertNotNull(WithdrawRecordRequest.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt())
                .build()).setAmount(randomInt()).setTimeStamp(randomLong()).setTokenId(randomString())
            .build().toString());
    }
}
