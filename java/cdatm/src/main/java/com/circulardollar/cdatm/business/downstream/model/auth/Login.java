package com.circulardollar.cdatm.business.downstream.model.auth;

import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import java.util.Objects;

public class Login implements ILogin {

  private final ICard card;
  private final IPin pin;

  Login(ICard card, IPin pin) {
    Objects.requireNonNull(card);
    Objects.requireNonNull(pin);
    this.card = card;
    this.pin = pin;
  }

  @Override
  public ICard getCard() {
    return card;
  }

  @Override
  public IPin getPin() {
    return pin;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "Login{\n" + "card=" + card + ", pin=" + pin + "}\n";
  }

  public static class Builder {

    private ICard card;
    private IPin pin;

    private Builder() {}

    public Builder setCard(ICard card) {
      Objects.requireNonNull(card);
      this.card = card;
      return this;
    }

    public Builder setPin(IPin pin) {
      Objects.requireNonNull(pin);
      this.pin = pin;
      return this;
    }

    public Login build() {
      return new Login(card, pin);
    }
  }
}
