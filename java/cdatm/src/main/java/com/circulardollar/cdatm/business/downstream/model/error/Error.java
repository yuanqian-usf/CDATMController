package com.circulardollar.cdatm.business.downstream.model.error;

import java.util.List;
import java.util.Objects;

public class Error implements IError {
  private final Integer errorCode;
  private final List<String> errorMessages;

  public Error(Integer errorCode, List<String> errorMessages) {
    Objects.requireNonNull(errorCode);
    Objects.requireNonNull(errorMessages);
    this.errorCode = errorCode;
    this.errorMessages = errorMessages;
  }

  public static IError of(Class<?> className, List<String> errorMessages) {
    Objects.requireNonNull(className);
    Objects.requireNonNull(errorMessages);
    int errorHash = className.hashCode();
    for (String s : errorMessages) {
      errorHash = s.hashCode() + errorHash * 31;
    }
    return new Error(errorHash, errorMessages);
  }

  @Override
  public Integer getErrorCode() {
    return errorCode;
  }

  @Override
  public List<String> getErrorMessages() {
    return errorMessages;
  }

  @Override public String toString() {
    return "Error{" + "errorCode=" + errorCode + ", errorMessages=" + errorMessages + '}';
  }
}
