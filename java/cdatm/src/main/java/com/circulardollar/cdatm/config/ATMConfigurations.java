package com.circulardollar.cdatm.config;

import com.circulardollar.cdatm.constant.APIVersions;

import java.util.Objects;

public class ATMConfigurations implements IATMConfigurations {
  private final Integer minCardNumberLength;
  private final Integer maxCardNumberLength;
  private final Integer minPinLength;
  private final Integer maxPinLength;
  private final Integer minDepositAmount;
  private final Integer maxDepositAmount;
  private final Integer minWithdrawAmount;
  private final Integer maxWithdrawAmount;
  private final APIVersions apiVersions;

  ATMConfigurations(
      Integer minCardNumberLength,
      Integer maxCardNumberLength,
      Integer minPinLength,
      Integer maxPinLength,
      Integer minDepositAmount,
      Integer maxDepositAmount,
      Integer minWithdrawAmount,
      Integer maxWithdrawAmount,
      APIVersions apiVersions) {
    Objects.requireNonNull(minCardNumberLength);
    Objects.requireNonNull(maxCardNumberLength);
    Objects.requireNonNull(minPinLength);
    Objects.requireNonNull(maxPinLength);
    Objects.requireNonNull(minDepositAmount);
    Objects.requireNonNull(maxDepositAmount);
    Objects.requireNonNull(minWithdrawAmount);
    Objects.requireNonNull(maxWithdrawAmount);
    Objects.requireNonNull(apiVersions);
    this.minCardNumberLength = minCardNumberLength;
    this.maxCardNumberLength = maxCardNumberLength;
    this.minPinLength = minPinLength;
    this.maxPinLength = maxPinLength;
    this.minDepositAmount = minDepositAmount;
    this.maxDepositAmount = maxDepositAmount;
    this.minWithdrawAmount = minWithdrawAmount;
    this.maxWithdrawAmount = maxWithdrawAmount;
    this.apiVersions = apiVersions;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private Integer minCardNumberLength = MIN_CARD_NUMBER_LENGTH;
    private Integer maxCardNumberLength = MAX_CARD_NUMBER_LENGTH;
    private Integer minPinLength = MIN_PIN_LENGTH;
    private Integer maxPinLength = MAX_PIN_LENGTH;
    private Integer minDepositAmount = MIN_DEPOSIT_AMOUNT;
    private Integer maxDepositAmount = MAX_DEPOSIT_AMOUNT;
    private Integer minWithdrawAmount = MIN_WITHDRAW_AMOUNT;
    private Integer maxWithdrawAmount = MAX_WITHDRAW_AMOUNT;
    private APIVersions apiVersions = APIVersions.V1;

    public Builder setMinCardNumberLength(Integer length) {
      Objects.requireNonNull(length);
      this.minCardNumberLength = length;
      return this;
    }

    public Builder setMaxCardNumberLength(Integer length) {
      Objects.requireNonNull(length);
      this.maxCardNumberLength = length;
      return this;
    }

    public Builder setMinPinLength(Integer length) {
      Objects.requireNonNull(length);
      this.minPinLength = length;
      return this;
    }

    public Builder setMaxPinLength(Integer length) {
      Objects.requireNonNull(length);
      this.maxPinLength = length;
      return this;
    }

    public Builder setMinDepositAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.minDepositAmount = amount;
      return this;
    }

    public Builder setMaxDepositAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.maxDepositAmount = amount;
      return this;
    }

    public Builder setMinWithdrawAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.minWithdrawAmount = amount;
      return this;
    }

    public Builder setMaxWithdrawAmount(Integer amount) {
      Objects.requireNonNull(amount);
      this.maxWithdrawAmount = amount;
      return this;
    }

    public Builder setAPIVersion(APIVersions apiVersions) {
      Objects.requireNonNull(apiVersions);
      this.apiVersions = apiVersions;
      return this;
    }

    public ATMConfigurations build() {
      return new ATMConfigurations(
          minCardNumberLength,
          maxCardNumberLength,
          minPinLength,
          maxPinLength,
          minDepositAmount,
          maxDepositAmount,
          minWithdrawAmount,
          maxWithdrawAmount, apiVersions);
    }
  }

  public Integer getMinCardNumberLength() {
    return minCardNumberLength;
  }

  public Integer getMaxCardNumberLength() {
    return maxCardNumberLength;
  }

  public Integer getMinPinLength() {
    return minPinLength;
  }

  public Integer getMaxPinLength() {
    return maxPinLength;
  }

  public Integer getMinDepositAmount() {
    return minDepositAmount;
  }

  public Integer getMaxDepositAmount() {
    return maxDepositAmount;
  }

  public Integer getMinWithdrawAmount() {
    return minWithdrawAmount;
  }

  public Integer getMaxWithdrawAmount() {
    return maxWithdrawAmount;
  }

  @Override
  public APIVersions getAPIVersion() {
    return apiVersions;
  }
}
