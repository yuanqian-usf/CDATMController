package com.circulardollar.cdatm.business.upstream.validation.auth;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class AuthRecordRemoteValidatorTest {

  @Test
  public void validate_01() {
    assertTrue(new AuthRecordRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertTrue(
        new AuthRecordRemoteValidator()
            .validate(
                new IAuthRecord() {
                  @Override
                  public String getTokenId() {
                    return null;
                  }

                  @Override
                  public List<IAccountRecord> getAccounts() {
                    return randomList();
                  }

                  @Override
                  public Long getTimeStamp() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertTrue(
        new AuthRecordRemoteValidator()
            .validate(
                AuthRecord.newBuilder()
                    .setTokenId("")
                    .setAccounts(randomList())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

@Test
  public void validate_04() {
    assertTrue(
        new AuthRecordRemoteValidator()
            .validate(
                AuthRecord.newBuilder()
                    .setTokenId(" ")
                    .setAccounts(randomList())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_05() {
    assertTrue(
        new AuthRecordRemoteValidator()
            .validate(
                new IAuthRecord() {
                  @Override
                  public String getTokenId() {
                    return randomString();
                  }

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
  public void validate_06() {
    assertFalse(
        new AuthRecordRemoteValidator()
            .validate(
                AuthRecord.newBuilder()
                    .setTokenId(randomString())
                    .setAccounts(randomList())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_07() {
    assertFalse(
        new AuthRecordRemoteValidator()
            .validate(
                AuthRecord.newBuilder()
                    .setTokenId(randomString())
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
  public void validate_08() {
    assertTrue(
        new AuthRecordRemoteValidator()
            .validate(
                AuthRecord.newBuilder()
                    .setTokenId(randomString())
                    .setAccounts(
                        Collections.singletonList(
                            new IAccountRecord() {
                              @Override
                              public String getAccountNumber() {
                                return null;
                              }

                              @Override
                              public Integer getBalance() {
                                return null;
                              }
                            }))
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
