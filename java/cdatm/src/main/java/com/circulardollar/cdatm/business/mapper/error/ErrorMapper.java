package com.circulardollar.cdatm.business.mapper.error;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;

public class ErrorMapper {
  public static IError down(com.circulardollar.cdatm.business.upstream.model.error.IError error) {
    if(error == null) {
      return null;
    }
    return new Error(error.getErrorCode(), error.getErrorMessages());
  }

}
