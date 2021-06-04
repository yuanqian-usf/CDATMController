package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;

import java.util.Objects;

public class WithdrawRecordRequest extends WithdrawRecord implements IWithdrawRecordRequest {

  private final String tokenId;

  WithdrawRecordRequest(
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
    return "WithdrawRecordRequest{\n" + "tokenId='" + tokenId + '\'' + ", account=" + account
        + ", amount=" + amount + ", timeStamp=" + timeStamp + "}\n";
  }

  public static class Builder extends WithdrawRecord.Builder {

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
      Objects.requireNonNull(tokenId);
      this.tokenId = tokenId;
      return this;
    }

    public WithdrawRecordRequest build() {
      return new WithdrawRecordRequest(
          super.getAccount(), super.getAmount(), super.getTimeStamp(), tokenId);
    }
  }
}
