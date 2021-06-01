package com.circulardollar.cdatm.business.upstream.request;

import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import java.util.Optional;

public interface IRequest<T> {
  Optional<IRequestWithToken> withToken();
  T getBody();
}
