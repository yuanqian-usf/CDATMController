package com.circulardollar.cdatm.business.upstream.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AccountRecord implements IAccountRecord {

  @SerializedName("accountNumber")
  @Expose
  final String accountNumber;
  @SerializedName("balance")
  @Expose
  final Integer balance;

  public AccountRecord(String accountNumber, Integer balance) {
    Objects.requireNonNull(accountNumber);
    Objects.requireNonNull(balance);
    this.accountNumber = accountNumber;
    this.balance = balance;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String getAccountNumber() {
    return accountNumber;
  }

  @Override
  public Integer getBalance() {
    return balance;
  }

  @Override public String toString() {
    return "AccountRecord{\n" + "accountNumber='" + accountNumber + '\'' + ", balance=" + balance
        + "}\n";
  }

  public static class Builder {

    private String accountNumber;
    private Integer balance;

    private Builder() {}

    public Builder setAccountNumber(String accountNumber) {
      Objects.requireNonNull(accountNumber);
      this.accountNumber = accountNumber;
      return this;
    }

    public Builder setBalance(Integer balance) {
      Objects.requireNonNull(balance);
      this.balance = balance;
      return this;
    }

    public AccountRecord build() {
      return new AccountRecord(accountNumber, balance);
    }
  }
}
