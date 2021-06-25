package com.circulardollar.cdatm.business.upstream.response;

import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;

public interface IRemoteResponse<T, E extends IErrorRecord> {
  T getBody();

  E getError();
}
