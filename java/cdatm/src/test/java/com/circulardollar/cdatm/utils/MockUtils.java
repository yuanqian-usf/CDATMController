package com.circulardollar.cdatm.utils;

import com.circulardollar.cdatm.business.upstream.model.error.Error;
import com.circulardollar.cdatm.business.upstream.model.error.IError;

import java.util.Collections;
import java.util.Optional;

public class MockUtils {
    public static final long MOCK_NETWORK_DELAY = 2000;

    public static Optional<Integer> getModifiedBalance(Integer op, Integer currentBalance,
        Integer diff) {
        long modifiedBalance = (long) currentBalance + (long) op * diff;
        if (modifiedBalance > Integer.MAX_VALUE) {
            return Optional.empty();
        }
        if (modifiedBalance < 0) {
            return Optional.empty();
        }
        return Optional.of((int) modifiedBalance);
    }

    public static IError error(MockError error) {
        return Error.newBuilder().setErrorCode(error.getErrorCode())
            .setErrorMessages(Collections.singletonList(error.getErrorMessage())).build();
    }
}
