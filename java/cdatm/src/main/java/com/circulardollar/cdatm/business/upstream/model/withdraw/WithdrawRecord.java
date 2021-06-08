package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class WithdrawRecord implements IWithdrawRecord {

  @SerializedName("account")
  @Expose
  final IAccountRecord account;
  @SerializedName("amount")
  @Expose
  final Integer amount;
  @SerializedName("timeStamp")
  @Expose
  final Long timeStamp;

  WithdrawRecord(IAccountRecord account, Integer amount, Long timeStamp) {
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
    return "WithdrawRecord{\n" + "account=" + account + ", amount=" + amount + ", timeStamp="
        + timeStamp + "}\n";
  }

  public static class Builder {

    private IAccountRecord account;
    private Integer amount;
    private Long timeStamp;

    protected Builder() {}

    protected IAccountRecord getAccount() {
      return account;
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

    public Builder setAccount(IAccountRecord account) {
      Objects.requireNonNull(account);
      this.account = account;
      return this;
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
