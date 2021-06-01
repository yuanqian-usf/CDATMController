package com.circulardollar.cdatm.business.upstream.model.card;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class CardRecord implements ICardRecord {

  private final String holderName;
  @SerializedName("cardNumber")
  private final String cardNumber;
  private final String cvc;
  private final String expirationDate;

  @Override public String toString() {
    return "CardRecord{\n" + "holderName='" + holderName + '\'' + ", cardNumber='" + cardNumber + '\''
        + ", cvc='" + cvc + '\'' + ", expirationDate='" + expirationDate + '\'' + "}\n";
  }

  CardRecord(String holderName, String cardNumber, String cvc, String expirationDate) {
    Objects.requireNonNull(holderName);
    Objects.requireNonNull(cardNumber);
    Objects.requireNonNull(cvc);
    Objects.requireNonNull(expirationDate);
    this.holderName = holderName;
    this.cardNumber = cardNumber;
    this.cvc = cvc;
    this.expirationDate = expirationDate;
  }

  public static CardRecord.Builder newBuilder() {
    return new CardRecord.Builder();
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

  public static class Builder {

    private String holderName;
    private String cardNumber;
    private String cvc;
    private String expirationDate;

    private Builder() {}

    public CardRecord.Builder setHolderName(String holderName) {
      Objects.requireNonNull(holderName);
      this.holderName = holderName;
      return this;
    }

    public CardRecord.Builder setCardNumber(String cardNumber) {
      Objects.requireNonNull(cardNumber);
      this.cardNumber = cardNumber;
      return this;
    }

    public CardRecord.Builder setCvc(String cvc) {
      Objects.requireNonNull(cvc);
      this.cvc = cvc;
      return this;
    }

    public CardRecord.Builder setExpirationDate(String expirationDate) {
      Objects.requireNonNull(expirationDate);
      this.expirationDate = expirationDate;
      return this;
    }

    public CardRecord build() {
      return new CardRecord(holderName, cardNumber, cvc, expirationDate);
    }
  }
}
