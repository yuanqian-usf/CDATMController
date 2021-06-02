package com.circulardollar.cdatm.business.downstream.response;

import static com.circulardollar.cdatm.TestBase.testError;
import static com.circulardollar.cdatm.TestBase.testResponseBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.circulardollar.cdatm.business.downstream.model.error.IError;
import java.util.stream.IntStream;
import org.junit.Test;

public class ResponseTest {

  @Test(expected = IllegalArgumentException.class)
  public void Response_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Response.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setBody_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Response.newBuilder().setBody(null);
  }

  @Test(expected = NullPointerException.class)
  public void setError_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Response.newBuilder().setError(null);
  }

  @Test
  public void getBody() {
    IntStream.range(0, testResponseBody.size())
        .forEach(
            index -> {
              IResponse<Integer, IError> response =
                  Response.<Integer>newBuilder().setBody(testResponseBody.get(index)).build();
              assertEquals(testResponseBody.get(index), response.getBody());
            });
  }

  @Test
  public void getError() {
    IntStream.range(0, testError.size())
        .forEach(
            index -> {
              IResponse<Integer, IError> response =
                  Response.<Integer>newBuilder().setError(testError.get(index)).build();
              assertEquals(testError.get(index), response.getError());
            });
  }

  @Test
  public void newBuilder() {
    IntStream.range(0, testResponseBody.size())
        .forEach(
            index -> {
              IResponse<Integer, IError> response =
                  Response.<Integer>newBuilder()
                      .setBody(testResponseBody.get(index))
                      .setError(testError.get(index))
                      .build();
              assertEquals(testError.get(index), response.getError());
            });
  }


    @Test(expected = IllegalArgumentException.class)
    public void testValidate_bothNull() {
        new Response<>(null, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_neitherNull() {
        new Response<>(testResponseBody.get(0), testError.get(0));
    }


}
