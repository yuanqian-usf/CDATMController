package com.circulardollar.cdatm.business.downstream.model.auth;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;

import java.util.List;
import java.util.Objects;

public class Auth implements IAuth {
  private final String tokenId;
  private final List<IAccount> accounts;
  private final Long timeStamp;

  Auth(String tokenId, List<IAccount> accounts, Long timeStamp) {
    Objects.requireNonNull(tokenId);
    Objects.requireNonNull(accounts);
    Objects.requireNonNull(timeStamp);
    this.tokenId = tokenId;
    this.accounts = accounts;
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
  public List<IAccount> getAccounts() {
    return accounts;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  @Override public String toString() {
    return "Auth{" + "tokenId='" + tokenId + '\'' + ", accounts=" + accounts + ", timeStamp="
        + timeStamp + '}';
  }

  public static class Builder {

    private String tokenId;
    private List<IAccount> accounts;
    private Long timeStamp;

    private Builder() {}

    public Builder setTokenId(String tokenId) {
      Objects.requireNonNull(tokenId);
      this.tokenId = tokenId;
      return this;
    }

    public Builder setAccounts(List<IAccount> accounts) {
      Objects.requireNonNull(accounts);
      this.accounts = accounts;
      return this;
    }

    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public Auth build() {
      return new Auth(tokenId, accounts, timeStamp);
    }
  }
}
