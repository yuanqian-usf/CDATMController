package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class DepositRecord implements IDepositRecord {

  @SerializedName("account")
  @Expose
  final IAccountRecord account;

  @SerializedName("amount")
  @Expose
  final Integer amount;

  @SerializedName("timeStamp")
  @Expose
  final Long timeStamp;

  DepositRecord(IAccountRecord account, Integer amount, Long timeStamp) {
    Objects.requireNonNull(account);
    Objects.requireNonNull(amount);
    Objects.requireNonNull(timeStamp);
    this.account = account;
    this.amount = amount;
    this.timeStamp = timeStamp;
  }

  public static Builder newBuilder() {
    return new Builder();
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

  @Override
  public String toString() {
    return "DepositRecord{\n"
        + "account="
        + account
        + ", amount="
        + amount
        + ", timeStamp="
        + timeStamp
        + "}\n";
  }

  public static class Builder {

    private IAccountRecord account;
    private Integer amount;
    private Long timeStamp;

    protected Builder() {}

    protected IAccountRecord getAccount() {
      return account;
    }

    public Builder setAccount(IAccountRecord account) {
      Objects.requireNonNull(account);
      this.account = account;
      return this;
    }

    protected Integer getAmount() {
      return amount;
    }

    public Builder setAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.amount = amount;
      return this;
    }

    protected Long getTimeStamp() {
      return timeStamp;
    }

    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public IDepositRecord build() {
      return new DepositRecord(account, amount, timeStamp);
    }
  }
}
