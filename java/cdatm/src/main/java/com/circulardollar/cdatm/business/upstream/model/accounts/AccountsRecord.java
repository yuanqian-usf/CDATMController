package com.circulardollar.cdatm.business.upstream.model.accounts;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import java.util.List;
import java.util.Objects;

public class AccountsRecord implements IAccountsRecord {
  private final List<AccountRecord> accounts;
  private final Long timeStamp;

  @Override public String toString() {
    return "AccountsRecord{\n" + "accounts=" + accounts + ", timeStamp=" + timeStamp + "}\n";
  }

  AccountsRecord(List<AccountRecord> accounts, Long timeStamp) {
    Objects.requireNonNull(accounts);
    Objects.requireNonNull(timeStamp);
    this.accounts = accounts;
    this.timeStamp = timeStamp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public List<? extends IAccountRecord> getAccounts() {
    return accounts;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  public static class Builder {

    private List<AccountRecord> accounts;
    private Long timeStamp;

    private Builder() {}

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

    public AccountsRecord build() {
      return new AccountsRecord(accounts, timeStamp);
    }
  }
}
