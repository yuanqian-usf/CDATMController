package com.circulardollar.cdatm.business.upstream.model.auth;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class AuthRecord implements IAuthRecord {
  @SerializedName("tokenId")
  @Expose
  private final String tokenId;
  @SerializedName("accounts")
  @Expose
  private final List<AccountRecord> accounts;
  @SerializedName("timeStamp")
  @Expose
  private final Long timeStamp;

  AuthRecord(String tokenId, List<AccountRecord> accounts, Long timeStamp) {
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
  public List<? extends IAccountRecord> getAccounts() {
    return accounts;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  @Override public String toString() {
    return "AuthRecord{\n" + "tokenId='" + tokenId + '\'' + ", accounts=" + accounts + ", timeStamp="
        + timeStamp + "}\n";
  }

  public static class Builder {

    private String tokenId;
    private List<AccountRecord> accounts;
    private Long timeStamp;

    private Builder() {}

    public Builder setTokenId(String tokenId) {
      Objects.requireNonNull(tokenId);
      this.tokenId = tokenId;
      return this;
    }

    public Builder setAccounts(List<AccountRecord> accounts) {
      Objects.requireNonNull(accounts);
      this.accounts = accounts;
      return this;
    }

    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public AuthRecord build() {
      return new AuthRecord(tokenId, accounts, timeStamp);
    }
  }
}
