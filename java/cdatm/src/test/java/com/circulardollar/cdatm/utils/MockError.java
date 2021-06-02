package com.circulardollar.cdatm.utils;

public enum MockError {
  SERVER_001(0x001, "EXCEEDS_AMOUNT"),
  SERVER_002(0x002, "NOT_ENOUGH_BALANCE");
  private final Integer errorCode;
  private final String errorMessage;

  MockError(Integer errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
