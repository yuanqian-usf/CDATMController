package com.circulardollar.cdatm.business.upstream.response;

import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import org.junit.Test;

import java.util.Collections;

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


}
