package com.circulardollar.cdatm.business.mapper.error;

import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static org.junit.Assert.*;

public class ErrorMapperTest {

    @Test public void down() {
        ErrorMapper.down(ErrorRecord.newBuilder().setErrorCode(randomInt()).setErrorMessages(randomList()).build());
    }
}
