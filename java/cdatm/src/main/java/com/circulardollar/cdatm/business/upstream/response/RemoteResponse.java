package com.circulardollar.cdatm.business.upstream.response;

import com.circulardollar.cdatm.business.upstream.model.error.IError;
import com.circulardollar.cdatm.constant.Errors;

public class RemoteResponse<T> implements IRemoteResponse<T, IError> {
  private final T body;
  private final IError error;

  protected RemoteResponse(T body, IError error) {
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
  public IError getError() {
    return error;
  }

  public static <T> RemoteResponse.Builder<T> newBuilder() {
    return new RemoteResponse.Builder<>();
  }

  public static class Builder<T> {
    private T body;
    private IError error;

    private Builder() {}

    public RemoteResponse.Builder<T> setError(IError error) {
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
