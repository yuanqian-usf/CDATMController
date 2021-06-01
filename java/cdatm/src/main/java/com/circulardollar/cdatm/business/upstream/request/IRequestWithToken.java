package com.circulardollar.cdatm.business.upstream.request;

@FunctionalInterface
public interface IRequestWithToken {
  String getTokenId();
}
