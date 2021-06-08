package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class WithdrawRecordRequest extends WithdrawRecord implements IWithdrawRecordRequest {

  @SerializedName("tokenId")
  @Expose
  private final String tokenId;

  WithdrawRecordRequest(IAccountRecord account, Integer amount, Long timeStamp, String tokenId) {
    super(account, amount, timeStamp);
    Objects.requireNonNull(tokenId);
    this.tokenId = tokenId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

  @Override
  public String toString() {
    return "WithdrawRecordRequest{\n"
        + "tokenId='"
        + tokenId
        + '\''
        + ", account="
        + account
        + ", amount="
        + amount
        + ", timeStamp="
        + timeStamp
        + "}\n";
  }

  public static class Builder extends WithdrawRecord.Builder {

    private String tokenId;

    private Builder() {}

    @Override
    public Builder setAccount(IAccountRecord account) {
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
