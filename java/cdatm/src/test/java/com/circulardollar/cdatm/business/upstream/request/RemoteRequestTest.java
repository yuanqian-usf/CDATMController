package com.circulardollar.cdatm.business.upstream.request;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import org.junit.Test;

public class RemoteRequestTest {

  @Test(expected = NullPointerException.class)
  public void RemoteRequest_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    RemoteRequest.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setBody_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    RemoteRequest.newBuilder().setBody(null).build();
  }

  @Test
  public void getBody() {
    String body = randomString();
    assertEquals(body, RemoteRequest.newBuilder().setBody(body).build().getBody());
  }

  @Test
  public void withToken_not_having_token() {
    assertFalse(RemoteRequest.newBuilder().setBody(randomString()).build().withToken().isPresent());
  }

  @Test
  public void withToken_having_token() {
    assertTrue(
        RemoteRequest.newBuilder()
            .setBody(
                new IRequestWithToken() {
                  @Override
                  public String getTokenId() {
                    return null;
                  }
                })
            .build()
            .withToken()
            .isPresent());
  }
}
