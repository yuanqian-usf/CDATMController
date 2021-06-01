package com.circulardollar.cdatm.business.upstream.request;

import java.util.Objects;

public class IRemoteRequest<T> implements IRequest<T> {
  private final T body;

  private IRemoteRequest(T body) {
    Objects.requireNonNull(body);
    this.body = body;
  }

  @Override
  public T getBody() {
    return body;
  }

  public static <T> IRemoteRequest.Builder<T> newBuilder() {
    return new IRemoteRequest.Builder<>();
  }

  public static class Builder<T> {
    private T body;

    private Builder() {}

    public IRemoteRequest.Builder<T> setBody(T body) {
      Objects.requireNonNull(body);
      this.body = body;
      return this;
    }

    public IRemoteRequest<T> build() {
      return new IRemoteRequest<>(body);
    }
  }
}
