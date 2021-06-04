package com.circulardollar.cdatm.business.upstream.model.pin;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PinRecord implements IPinRecord {

  @SerializedName("pinNumber")
  private final String pinNumber;

  PinRecord(String pinNumber) {
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
    return "PinRecord{\n" + "pinNumber='" + pinNumber + '\'' + "}\n";
  }

  public static class Builder {
    private String pinNumber;

    private Builder() {}

    public Builder setPinNumber(String pinNumber) {
      Objects.requireNonNull(pinNumber);
      this.pinNumber = pinNumber;
      return this;
    }

    public PinRecord build() {
      return new PinRecord(pinNumber);
    }
  }
}
