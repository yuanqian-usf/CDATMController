package com.circulardollar.cdatm.business.upstream.request;

import com.circulardollar.cdatm.business.downstream.response.Response;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

public class IRemoteRequestTest {

    @Test(expected = NullPointerException.class)
    public void IRemoteRequest_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        IRemoteRequest.newBuilder().build();
    }

    @Test(expected = NullPointerException.class)
    public void setBody_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        IRemoteRequest.newBuilder().setBody(null).build();
    }

    @Test public void getBody() {
        String body = randomString();
        assertEquals(body, IRemoteRequest.newBuilder().setBody(body).build().getBody());
    }

}
