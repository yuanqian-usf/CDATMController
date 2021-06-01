package com.circulardollar.cdatm.business.downstream.response;

import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.constant.Errors;
import java.util.Objects;

public class Response<T> implements IResponse<T, IError> {
  private final T body;
  private final IError error;

  Response(T body, IError error) {
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

  public static <T> Builder<T> newBuilder() {
    return new Builder<>();
  }

  public static class Builder<T> {
    private T body;
    private IError error;

    private Builder() {}

    public Builder<T> setError(IError error) {
      Objects.requireNonNull(error);
      this.body = null;
      this.error = error;
      return this;
    }

    public Builder<T> setBody(T body) {
      Objects.requireNonNull(body);
      this.body = body;
      this.error = null;
      return this;
    }

    public Response<T> build() {
      return new Response<>(body, error);
    }
  }
}
