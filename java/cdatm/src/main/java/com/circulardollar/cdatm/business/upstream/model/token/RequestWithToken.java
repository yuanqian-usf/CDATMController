package com.circulardollar.cdatm.business.upstream.model.token;

import java.util.Objects;

public class RequestWithToken implements IRequestWithToken {

  private String tokenId;

  RequestWithToken(String tokenId) {
    Objects.requireNonNull(tokenId);
    this.tokenId = tokenId;
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "RequestWithToken{" +
        "tokenId='" + tokenId + '\'' +
        '}';
  }

  public static class Builder {
    private Builder() {}

    private String tokenId;
    public Builder setTokenId(String tokenId) {
      Objects.requireNonNull(tokenId);
      this.tokenId = tokenId;
      return this;
    }

    public RequestWithToken build() {
      return new RequestWithToken(tokenId);
    }
  }

}
