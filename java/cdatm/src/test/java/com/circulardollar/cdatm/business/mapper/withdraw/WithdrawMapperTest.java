package com.circulardollar.cdatm.business.mapper.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.withdraw.Withdraw;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class WithdrawMapperTest {

    @Test public void WithdrawMapper_down_01() {
        assertNull(WithdrawMapper.down(null));
    }

    @Test public void WithdrawMapper_down_02() {
        assertNotNull(WithdrawMapper.down(WithdrawRecord.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build()));
    }

    @Test public void WithdrawMapper_up_01() {
        assertNull(WithdrawMapper.up(null));
    }

    @Test public void WithdrawMapper_up_02() {
        assertNotNull(WithdrawMapper.up(Withdraw.newBuilder().setAccount(
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build()));
    }

    @Test public void WithdrawMapper_parse_01() {
        assertNull(WithdrawMapper.parse(null, randomString()));
    }

    @Test public void WithdrawMapper_parse_02() {
        assertNull(WithdrawMapper.parse(WithdrawRecord.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build(), null));
    }


    @Test public void WithdrawMapper_parse_03() {
        assertNotNull(WithdrawMapper.parse(WithdrawRecord.newBuilder().setAccount(
            AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
            .setAmount(randomInt()).setTimeStamp(randomLong()).build(), randomString()));
    }
}
