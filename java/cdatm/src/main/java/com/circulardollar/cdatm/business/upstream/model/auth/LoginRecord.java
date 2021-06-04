package com.circulardollar.cdatm.business.upstream.model.auth;

import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LoginRecord implements ILoginRecord {
  @SerializedName("card")
  private final ICardRecord card;
  @SerializedName("pin")
  private final IPinRecord pin;

  @Override public String toString() {
    return "LoginRecord{\n" + "card=" + card + ", pin=" + pin + "}\n";
  }

  LoginRecord(ICardRecord card, IPinRecord pin) {
    Objects.requireNonNull(card);
    Objects.requireNonNull(pin);
    this.card = card;
    this.pin = pin;
  }

  @Override
  public ICardRecord getCard() {
    return card;
  }

  @Override
  public IPinRecord getPin() {
    return pin;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private ICardRecord card;
    private IPinRecord pin;

    private Builder() {}

    public Builder setCard(ICardRecord card) {
      Objects.requireNonNull(card);
      this.card = card;
      return this;
    }

    public Builder setPin(IPinRecord pin) {
      Objects.requireNonNull(pin);
      this.pin = pin;
      return this;
    }

    public LoginRecord build() {
      return new LoginRecord(card, pin);
    }
  }
}
