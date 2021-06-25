package com.circulardollar.cdatm.business.mapper.error;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;

public final class ErrorMapper {
  private ErrorMapper() {}

  public static IError down(IErrorRecord error) {
    if (error == null) {
      return null;
    }
    return new Error(error.getErrorCode(), error.getErrorMessages());
  }
}
