package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class WithdrawRecordTest {

    @Test public void testToString() {
        assertNotNull(WithdrawRecord.newBuilder()
            .setAccount(AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt())
            .setTimeStamp(randomLong())
            .build()
            .toString());
    }
}
