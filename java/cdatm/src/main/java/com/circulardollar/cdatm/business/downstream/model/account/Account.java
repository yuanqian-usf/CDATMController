package com.circulardollar.cdatm.business.downstream.model.account;

import java.util.Objects;

public class Account implements IAccount {

  private final String accountNumber;
  private final Integer balance;

  Account(String accountNumber, Integer balance) {
    Objects.requireNonNull(accountNumber);
    Objects.requireNonNull(balance);
    this.accountNumber = accountNumber;
    this.balance = balance;
  }

  @Override
  public String getAccountNumber() {
    return accountNumber;
  }

  @Override
  public Integer getBalance() {
    return balance;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "Account{" + "accountNumber='" + accountNumber + '\'' + ", balance=" + balance + '}';
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

    public Account build() {
      return new Account(accountNumber, balance);
    }
  }
}
