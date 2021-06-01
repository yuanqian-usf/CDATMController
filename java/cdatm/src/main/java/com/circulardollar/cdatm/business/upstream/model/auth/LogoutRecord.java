package com.circulardollar.cdatm.business.upstream.model.auth;

import java.util.Objects;

public class LogoutRecord implements ILogoutRecord {
  private final Long timeStamp;

  @Override public String toString() {
    return "LogoutRecord{" + "timeStamp=" + timeStamp + '}';
  }

  LogoutRecord(Long timeStamp) {
    Objects.requireNonNull(timeStamp);
    this.timeStamp = timeStamp;
  }

  @Override
  public Long getTimeStamp() {
    return timeStamp;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private Long timeStamp;


    public Builder setTimeStamp(Long timeStamp) {
      Objects.requireNonNull(timeStamp);
      this.timeStamp = timeStamp;
      return this;
    }

    public LogoutRecord build() {
      return new LogoutRecord(timeStamp);
    }

    protected Long getTimeStamp() {
      return timeStamp;
    }
  }
}
