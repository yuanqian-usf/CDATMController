package com.circulardollar.cdatm.business.downstream.response;

import com.circulardollar.cdatm.business.downstream.model.error.IError;

public interface IResponse<T, E extends IError> {
  T getBody();

  E getError();
}
