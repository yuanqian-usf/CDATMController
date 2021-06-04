package com.circulardollar.cdatm.business.downstream.model.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import java.util.Objects;

public class Withdraw implements IWithdraw {

  private final Integer amount;
  private final IAccount account;
  private final Long timeStamp;

  Withdraw(IAccount account, Integer amount, Long timeStamp) {
    Objects.requireNonNull(account);
    Objects.requireNonNull(amount);
    Objects.requireNonNull(timeStamp);
    this.amount = amount;
    this.account = account;
    this.timeStamp = timeStamp;
  }

  @Override
  public Integer getAmount() {
    return amount;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  @Override
  public IAccount getAccount() {
    return account;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "Withdraw{\n" + "amount=" + amount + ", account=" + account + ", timeStamp=" + timeStamp
        + "}\n";
  }

  public static class Builder {

    private IAccount account;
    private Integer amount;
    private Long timeStamp;

    private Builder() {}

    public Builder setAccount(IAccount account) {
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

    public Withdraw build() {
      return new Withdraw(account, amount, timeStamp);
    }
  }
}
