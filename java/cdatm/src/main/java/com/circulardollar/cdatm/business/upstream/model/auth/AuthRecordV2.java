package com.circulardollar.cdatm.business.upstream.model.auth;

import java.util.Objects;

public class AuthRecordV2 implements IAuthRecordV2 {
  private final String tokenId;
  private final Long timeStamp;

  @Override public String toString() {
    return "AuthRecordV2{\n" + "tokenId='" + tokenId + '\'' + ", timeStamp=" + timeStamp + "}\n";
  }

  AuthRecordV2(String tokenId, Long timeStamp) {
    Objects.requireNonNull(tokenId);
    Objects.requireNonNull(timeStamp);
    this.tokenId = tokenId;
    this.timeStamp = timeStamp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  public static class Builder {

    private String tokenId;
    private Long timeStamp;

    private Builder() {}

    public Builder setTokenId(String tokenId) {
      Objects.requireNonNull(tokenId);
      this.tokenId = tokenId;
      return this;
    }

    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public AuthRecordV2 build() {
      return new AuthRecordV2(tokenId, timeStamp);
    }
  }
}
