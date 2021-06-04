package com.circulardollar.cdatm.business.upstream.response;

import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;

public interface IResponse<T, E extends IErrorRecord> {
  T getBody();

  E getError();
}
