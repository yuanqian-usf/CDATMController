package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;

import java.util.Objects;

public class DepositRecordRequest extends DepositRecord implements IDepositRecordRequest {
  private final String tokenId;

  DepositRecordRequest(
      AccountRecord account, Integer amount, Long timeStamp, String tokenId) {
    super(account, amount, timeStamp);
    Objects.requireNonNull(tokenId);
    this.tokenId = tokenId;
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "DepositRecordRequest{" + "tokenId='" + tokenId + '\'' + ", account=" + account
        + ", amount=" + amount + ", timeStamp=" + timeStamp + '}';
  }


  public static class Builder extends DepositRecord.Builder {

    private String tokenId;

    private Builder() {}

    @Override
    public Builder setAccount(AccountRecord account) {
      super.setAccount(account);
      return this;
    }

    @Override
    public Builder setAmount(Integer amount) {
      super.setAmount(amount);
      return this;
    }

    @Override
    public Builder setTimeStamp(Long timeStamp) {
      super.setTimeStamp(timeStamp);
      return this;
    }

    public Builder setTokenId(String tokenId) {
      this.tokenId = tokenId;
      return this;
    }

    public DepositRecordRequest build() {
      return new DepositRecordRequest(
          super.getAccount(), super.getAmount(), super.getTimeStamp(), tokenId);
    }
  }
}
