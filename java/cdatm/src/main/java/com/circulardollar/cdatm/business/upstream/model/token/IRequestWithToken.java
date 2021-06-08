package com.circulardollar.cdatm.business.upstream.model.token;

@FunctionalInterface
public interface IRequestWithToken {
  String getTokenId();
}
