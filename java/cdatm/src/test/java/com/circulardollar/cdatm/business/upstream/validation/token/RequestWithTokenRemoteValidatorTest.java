package com.circulardollar.cdatm.business.upstream.validation.token;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.token.RequestWithToken;
import org.junit.Test;

public class RequestWithTokenRemoteValidatorTest {

  @Test
  public void validate_01() {
    assertTrue(new RequestWithTokenRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertTrue(new RequestWithTokenRemoteValidator().validate(() -> null).isPresent());
  }

  @Test
  public void validate_03() {
    assertFalse(
        new RequestWithTokenRemoteValidator()
            .validate(RequestWithToken.newBuilder().setTokenId(randomString()).build())
            .isPresent());
  }
}
