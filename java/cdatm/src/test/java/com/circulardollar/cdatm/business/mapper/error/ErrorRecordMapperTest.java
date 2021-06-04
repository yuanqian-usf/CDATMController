package com.circulardollar.cdatm.business.mapper.error;

import com.circulardollar.cdatm.business.downstream.model.error.IError;
import org.junit.Test;

import java.util.List;

import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;

public class ErrorRecordMapperTest {

    @Test public void ErrorMapper_down_01() {
        IError error = ErrorMapper.down(null);
        assertNull(error);
    }

    @Test public void ErrorMapper_down_02() {
        List<String> mockList = anyList();
        IErrorRecord error = ErrorRecord.of(Object.class, mockList);
        assertNotNull(ErrorMapper.down(error));
        assertNotNull(error.getErrorCode());
        assertNotNull(error.getErrorMessages());
    }
}
