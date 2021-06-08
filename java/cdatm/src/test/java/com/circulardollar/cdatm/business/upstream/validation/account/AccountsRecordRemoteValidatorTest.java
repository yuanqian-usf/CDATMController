package com.circulardollar.cdatm.business.upstream.validation.account;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class AccountsRecordRemoteValidatorTest {
  @Test
  public void validate_01() {
    assertTrue(new AccountsRecordRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_null_accounts() {
    assertTrue(
        new AccountsRecordRemoteValidator()
            .validate(
                new IAccountsRecord() {
                  @Override
                  public List<IAccountRecord> getAccounts() {
                    return null;
                  }

                  @Override
                  public Long getTimeStamp() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_02() {
    assertFalse(
        new AccountsRecordRemoteValidator()
            .validate(
                AccountsRecord.newBuilder()
                    .setAccounts(
                        Collections.singletonList(
                            AccountRecord.newBuilder()
                                .setAccountNumber(randomString())
                                .setBalance(randomInt())
                                .build()))
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertTrue(
        new AccountsRecordRemoteValidator()
            .validate(
                AccountsRecord.newBuilder()
                    .setAccounts(
                        Collections.singletonList(
                            AccountRecord.newBuilder()
                                .setAccountNumber("")
                                .setBalance(randomInt())
                                .build()))
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
