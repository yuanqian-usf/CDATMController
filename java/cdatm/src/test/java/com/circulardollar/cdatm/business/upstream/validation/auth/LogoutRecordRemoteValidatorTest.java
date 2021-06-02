package com.circulardollar.cdatm.business.upstream.validation.auth;

import static com.circulardollar.cdatm.TestBase.randomLong;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.LogoutRecord;
import org.junit.Test;

public class LogoutRecordRemoteValidatorTest {

  @Test
  public void validate_01() {
    assertTrue(new LogoutRecordRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertTrue(
        new LogoutRecordRemoteValidator()
            .validate(() -> null)
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertFalse(
        new LogoutRecordRemoteValidator()
            .validate(LogoutRecord.newBuilder().setTimeStamp(randomLong()).build())
            .isPresent());
  }
}
