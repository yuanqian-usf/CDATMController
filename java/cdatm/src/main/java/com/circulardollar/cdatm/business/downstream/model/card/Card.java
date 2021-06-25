package com.circulardollar.cdatm.business.downstream.model.card;

import java.util.Objects;

public class Card implements ICard {

  private final String holderName;
  private final String cardNumber;
  private final String cvc;
  private final String expirationDate;

  Card(String holderName, String cardNumber, String cvc, String expirationDate) {
    Objects.requireNonNull(holderName);
    Objects.requireNonNull(cardNumber);
    Objects.requireNonNull(cvc);
    Objects.requireNonNull(expirationDate);
    this.holderName = holderName;
    this.cardNumber = cardNumber;
    this.cvc = cvc;
    this.expirationDate = expirationDate;
  }

  @Override
  public String getHolderName() {
    return holderName;
  }

  @Override
  public String getCardNumber() {
    return cardNumber;
  }

  @Override
  public String getCVC() {
    return cvc;
  }

  @Override
  public String getExpirationDate() {
    return expirationDate;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "Card{\n" + "holderName='" + holderName + '\'' + ", cardNumber='" + cardNumber + '\''
        + ", cvc='" + cvc + '\'' + ", expirationDate='" + expirationDate + '\'' + "}\n";
  }

  public static class Builder {

    private String holderName;
    private String cardNumber;
    private String cvc;
    private String expirationDate;

    private Builder() {}

    public Builder setHolderName(String holderName) {
      Objects.requireNonNull(holderName);
      this.holderName = holderName;
      return this;
    }

    public Builder setCardNumber(String cardNumber) {
      Objects.requireNonNull(cardNumber);
      this.cardNumber = cardNumber;
      return this;
    }

    public Builder setCvc(String cvc) {
      Objects.requireNonNull(cvc);
      this.cvc = cvc;
      return this;
    }

    public Builder setExpirationDate(String expirationDate) {
      Objects.requireNonNull(expirationDate);
      this.expirationDate = expirationDate;
      return this;
    }

    public Card build() {
      return new Card(holderName, cardNumber, cvc, expirationDate);
    }
  }
}
