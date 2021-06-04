package com.circulardollar.cdatm.business.mapper.deposit;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DepositMapperTest {
    @Test public void DepositMapper_down_01() {
        assertNull(DepositMapper.down(null));
    }

    @Test public void DepositMapper_down_02() {
        assertNotNull(DepositMapper.down(DepositRecord.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build()));
    }

    @Test public void DepositMapper_up_01() {
        assertNull(DepositMapper.up(null));
    }

    @Test public void DepositMapper_up_02() {
        assertNotNull(DepositMapper.up(Deposit.newBuilder().setAccount(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build()));
    }

    @Test public void DepositMapper_parse_01() {
        assertNull(DepositMapper.parse(null, randomString()));
    }

    @Test public void DepositMapper_parse_02() {
        assertNull(DepositMapper.parse(DepositRecord.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build(), null));
    }


    @Test public void DepositMapper_parse_03() {
        assertNotNull(DepositMapper.parse(DepositRecord.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build(), randomString()));
    }
}
