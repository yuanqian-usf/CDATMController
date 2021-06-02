package com.circulardollar.cdatm.business.upstream.response;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class RemoteResponseTest {

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_bothNull() {
        new RemoteResponse<>(null, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_neitherNull() {
        new RemoteResponse<>(new Object(), ErrorRecord.of(ErrorRecord.class,
            Collections.singletonList("0")));
    }


    @Test
    public void getError() {
        Integer errorCode = randomInt();
        IErrorRecord error = new IErrorRecord() {
            @Override
            public Integer getErrorCode() {
                return errorCode;
            }

            @Override
            public List<String> getErrorMessages() {
                return null;
            }
        };
        RemoteResponse<Object> remoteResponse = RemoteResponse.newBuilder().setError(error).build();
        assertNotNull(remoteResponse.getError());
        assertEquals(errorCode, remoteResponse.getError().getErrorCode());
    }


    @Test
    public void getBody() {
        Object object = new Object();
        RemoteResponse<Object> remoteResponse = RemoteResponse.newBuilder().setBody(object).build();
        assertNotNull(remoteResponse.getBody());
    }

    @Test
    public void builder_getBody() {
        Object object = new Object();
        RemoteResponse.Builder<Object> remoteResponseBuilder = RemoteResponse.newBuilder().setBody(object);
        assertNotNull(remoteResponseBuilder.getBody());
        assertEquals(object, remoteResponseBuilder.getBody());
    }
}
