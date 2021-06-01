package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import java.util.Objects;

public class WithdrawRecord implements IWithdrawRecord {
  final AccountRecord account;
  final Integer amount;
  final Long timeStamp;

  WithdrawRecord(AccountRecord account, Integer amount, Long timeStamp) {
    Objects.requireNonNull(account);
    Objects.requireNonNull(amount);
    Objects.requireNonNull(timeStamp);
    this.account = account;
    this.amount = amount;
    this.timeStamp = timeStamp;
  }

  @Override
  public IAccountRecord getAccount() {
    return account;
  }

  @Override
  public Integer getAmount() {
    return amount;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "WithdrawRecord{" + "account=" + account + ", amount=" + amount + ", timeStamp="
        + timeStamp + '}';
  }

  public static class Builder {

    private AccountRecord account;
    private Integer amount;
    private Long timeStamp;

    protected Builder() {}

    public Builder setAccount(AccountRecord account) {
      Objects.requireNonNull(account);
      this.account = account;
      return this;
    }

    public Builder setAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.amount = amount;
      return this;
    }

    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    protected AccountRecord getAccount() {
      return account;
    }

    protected Integer getAmount() {
      return amount;
    }

    protected Long getTimeStamp() {
      return timeStamp;
    }

    public WithdrawRecord build() {
      return new WithdrawRecord(account, amount, timeStamp);
    }
  }
}
