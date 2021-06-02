package com.circulardollar.cdatm.business.upstream.validation.auth;

import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import org.junit.Test;

public class AuthRecordV2RemoteValidatorTest {

  @Test
  public void validate_01() {
    assertTrue(new AuthRecordV2RemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertTrue(
        new AuthRecordV2RemoteValidator()
            .validate(
                new IAuthRecordV2() {
                  @Override
                  public String getTokenId() {
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
  public void validate_03() {
    assertTrue(
        new AuthRecordV2RemoteValidator()
            .validate(AuthRecordV2.newBuilder().setTokenId("").setTimeStamp(randomLong()).build())
            .isPresent());
  }

  @Test
  public void validate_04() {
    assertTrue(
        new AuthRecordV2RemoteValidator()
            .validate(AuthRecordV2.newBuilder().setTokenId(" ").setTimeStamp(randomLong()).build())
            .isPresent());
  }

  @Test
  public void validate_05() {
    assertFalse(
        new AuthRecordV2RemoteValidator()
            .validate(
                AuthRecordV2.newBuilder()
                    .setTokenId(randomString())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
