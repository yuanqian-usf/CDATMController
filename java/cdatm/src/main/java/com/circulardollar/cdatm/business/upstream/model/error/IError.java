package com.circulardollar.cdatm.business.upstream.model.error;

import java.util.List;

public interface IError {
    Integer getErrorCode();

    List<String> getErrorMessages();
}
