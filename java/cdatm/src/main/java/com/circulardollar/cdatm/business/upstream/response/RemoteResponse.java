package com.circulardollar.cdatm.business.upstream.response;

import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.constant.Errors;

public class RemoteResponse<T> implements IRemoteResponse<T, IErrorRecord> {
  private final T body;
  private final IErrorRecord error;

  RemoteResponse(T body, IErrorRecord error) {
    this.body = body;
    this.error = error;
    if (!validate()) {
      throw new IllegalArgumentException(Errors.XOR_ILLEGAL_EXCEPTION.getValue());
    }
  }

  private boolean validate() {
    return body != null ^ error != null;
  }

  @Override
  public T getBody() {
    return body;
  }

  @Override
  public IErrorRecord getError() {
    return error;
  }

  public static <T> RemoteResponse.Builder<T> newBuilder() {
    return new RemoteResponse.Builder<>();
  }

  public static class Builder<T> {
    private T body;
    private IErrorRecord error;

    private Builder() {}

    public RemoteResponse.Builder<T> setError(IErrorRecord error) {
      this.body = null;
      this.error = error;
      return this;
    }

    public RemoteResponse.Builder<T> setBody(T body) {
      this.body = body;
      this.error = null;
      return this;
    }

    public T getBody() {
      return body;
    }

    public RemoteResponse<T> build() {
      return new RemoteResponse<>(body, error);
    }
  }
}
