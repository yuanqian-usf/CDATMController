package com.circulardollar.cdatm.business.upstream.model.accounts;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class AccountsRecord implements IAccountsRecord {
  @SerializedName("accounts")
  @Expose
  private final List<IAccountRecord> accounts;

  @SerializedName("timeStamp")
  @Expose
  private final Long timeStamp;

  AccountsRecord(List<IAccountRecord> accounts, Long timeStamp) {
    Objects.requireNonNull(accounts);
    Objects.requireNonNull(timeStamp);
    this.accounts = accounts;
    this.timeStamp = timeStamp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "AccountsRecord{\n" + "accounts=" + accounts + ", timeStamp=" + timeStamp + "}\n";
  }

  @Override
  public List<IAccountRecord> getAccounts() {
    return accounts;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  public static class Builder {

    private List<IAccountRecord> accounts;
    private Long timeStamp;

    private Builder() {}

    public Builder setAccounts(List<IAccountRecord> accounts) {
      Objects.requireNonNull(accounts);
      this.accounts = accounts;
      return this;
    }

    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public AccountsRecord build() {
      return new AccountsRecord(accounts, timeStamp);
    }
  }
}
