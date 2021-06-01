package com.circulardollar.cdatm.business.upstream.response;

import com.circulardollar.cdatm.business.upstream.model.error.IError;

public interface IResponse<T, E extends IError> {
  T getBody();

  E getError();
}
