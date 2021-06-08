package com.circulardollar.cdatm.business.upstream.model.accounts;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertNotNull;

public class AccountsRecordTest {

  @Test
  public void getAccounts() {
    assertNotNull(
        AccountsRecord.newBuilder()
            .setAccounts(
                new ArrayList<>(
                    Collections.singleton(
                        AccountRecord.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomInt())
                            .build())))
            .setTimeStamp(randomLong())
            .build()
            .getAccounts());
  }

  @Test
  public void testToString() {
    assertNotNull(
        AccountsRecord.newBuilder()
            .setAccounts(
                new ArrayList<>(
                    Collections.singleton(
                        AccountRecord.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomInt())
                            .build())))
            .setTimeStamp(randomLong())
            .build()
            .toString());
  }

  @Test
  public void getTimeStamp() {
    assertNotNull(
        AccountsRecord.newBuilder()
            .setAccounts(
                new ArrayList<>(
                    Collections.singleton(
                        AccountRecord.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomInt())
                            .build())))
            .setTimeStamp(randomLong())
            .build()
            .getTimeStamp());
  }
}
