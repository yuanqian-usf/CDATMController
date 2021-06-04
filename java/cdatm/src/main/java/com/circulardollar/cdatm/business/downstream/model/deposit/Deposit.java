package com.circulardollar.cdatm.business.downstream.model.deposit;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import java.util.Objects;

public class Deposit implements IDeposit {
  private final IAccount account;
  private final Integer amount;
  private final Long timeStamp;

  Deposit(IAccount account, Integer amount, Long timeStamp) {
    Objects.requireNonNull(account);
    Objects.requireNonNull(amount);
    Objects.requireNonNull(timeStamp);
    this.account = account;
    this.amount = amount;
    this.timeStamp = timeStamp;
  }

  @Override
  public IAccount getAccount() {
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

  public static Deposit.Builder newBuilder() {
    return new Deposit.Builder();
  }

  @Override public String toString() {
    return "Deposit{\n" + "account=" + account + ", amount=" + amount + ", timeStamp=" + timeStamp
        + "}\n";
  }

  public static class Builder {

    private IAccount account;
    private Integer amount;
    private Long timeStamp;

    private Builder() {}

    public Deposit.Builder setAccount(IAccount account) {
      Objects.requireNonNull(account);
      this.account = account;
      return this;
    }

    public Deposit.Builder setAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.amount = amount;
      return this;
    }

    public Deposit.Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public Deposit build() {
      return new Deposit(account, amount, timeStamp);
    }
  }
}
