package com.circulardollar.cdatm.business.upstream.model.token;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class RequestWithTokenTest {

  @Test
  public void getTokenId() {
    assertNotNull(RequestWithToken.newBuilder().setTokenId(randomString()).build().getTokenId());
  }

  @Test
  public void newBuilder() {
    assertNotNull(RequestWithToken.newBuilder().setTokenId(randomString()).build());
  }

  @Test public void testToString() {
    assertNotNull(RequestWithToken.newBuilder().setTokenId(randomString()).build().toString());
  }
}
