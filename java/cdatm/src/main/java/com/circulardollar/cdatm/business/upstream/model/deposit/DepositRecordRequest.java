package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class DepositRecordRequest extends DepositRecord implements IDepositRecordRequest {
  @SerializedName("tokenId")
  @Expose
  private final String tokenId;

  DepositRecordRequest(IAccountRecord account, Integer amount, Long timeStamp, String tokenId) {
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
    return "DepositRecordRequest{\n"
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

  public static class Builder extends DepositRecord.Builder {

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
      this.tokenId = tokenId;
      return this;
    }

    public DepositRecordRequest build() {
      return new DepositRecordRequest(
          super.getAccount(), super.getAmount(), super.getTimeStamp(), tokenId);
    }
  }
}
