package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class DepositRecordTest {

    @Test public void testToString() {assertNotNull(DepositRecord.newBuilder()
        .setAccount(AccountRecord.newBuilder()
            .setAccountNumber(randomString())
            .setBalance(randomInt()).build())
        .setAmount(randomInt())
        .setTimeStamp(randomLong())
        .build().toString());
    }
}
