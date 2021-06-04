package com.circulardollar.cdatm.business.upstream.model.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class ErrorRecord implements IErrorRecord {
  @SerializedName("errorCode")
  @Expose
  private final Integer errorCode;
  @SerializedName("errorMessages")
  @Expose
  private final List<String> errorMessages;

  ErrorRecord(Integer errorCode, List<String> errorMessages) {
    Objects.requireNonNull(errorCode);
    Objects.requireNonNull(errorMessages);
    this.errorCode = errorCode;
    this.errorMessages = errorMessages;
  }

  public static IErrorRecord of(Class<?> className, List<String> errorMessages) {
    Objects.requireNonNull(className);
    Objects.requireNonNull(errorMessages);
    int errorHash = className.hashCode();
    for (String s : errorMessages) {
      errorHash = s.hashCode() + errorHash * 31;
    }
    return new ErrorRecord(errorHash, errorMessages);
  }

  @Override
  public Integer getErrorCode() {
    return errorCode;
  }

  @Override
  public List<String> getErrorMessages() {
    return errorMessages;
  }

  public static Builder newBuilder() {
   return new Builder();
  }

  @Override public String toString() {
    return "Error{\n" + "errorCode=" + errorCode + ", errorMessages=" + errorMessages + "}\n";
  }


  public static class Builder {
    private Integer errorCode;
    private List<String> errorMessages;

    private Builder() {
    }

    public Builder setErrorCode(Integer errorCode) {
      Objects.requireNonNull(errorCode);
      this.errorCode = errorCode;
      return this;
    }


    public Builder setErrorMessages(List<String> errorMessages) {
      Objects.requireNonNull(errorMessages);
      this.errorMessages = errorMessages;
      return this;
    }

    public ErrorRecord build() {
      return new ErrorRecord(errorCode, errorMessages);
    }
  }
}
