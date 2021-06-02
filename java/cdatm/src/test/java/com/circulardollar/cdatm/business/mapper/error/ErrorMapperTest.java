package com.circulardollar.cdatm.business.mapper.error;

import org.junit.Test;

import java.util.List;

import com.circulardollar.cdatm.business.upstream.model.error.IError;
import com.circulardollar.cdatm.business.upstream.model.error.Error;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;

public class ErrorMapperTest {

    @Test public void ErrorMapper_down_01() {
        assertNull(ErrorMapper.down(null));
    }

    @Test public void ErrorMapper_down_02() {
        List<String> mockList = anyList();
        IError error = Error.of(Object.class, mockList);
        assertNotNull(ErrorMapper.down(error));
        assertNotNull(error.getErrorCode());
        assertNotNull(error.getErrorMessages());
    }
}
