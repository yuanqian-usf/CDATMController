package com.circulardollar.cdatm.business.downstream.model.pin;

import java.util.Objects;

public class Pin implements IPin {

  private final String pinNumber;

  Pin(String pinNumber) {
    Objects.requireNonNull(pinNumber);
    this.pinNumber = pinNumber;
  }

  @Override
  public String getPinNumber() {
    return pinNumber;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override public String toString() {
    return "Pin{" + "pinNumber='" + pinNumber + '\'' + '}';
  }

  public static class Builder {
    private String pinNumber;

    private Builder() {}

    public Builder setPinNumber(String pinNumber) {
      Objects.requireNonNull(pinNumber);
      this.pinNumber = pinNumber;
      return this;
    }

    public Pin build() {
      return new Pin(pinNumber);
    }
  }
}
