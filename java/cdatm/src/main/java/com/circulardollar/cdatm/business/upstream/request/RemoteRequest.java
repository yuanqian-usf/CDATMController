package com.circulardollar.cdatm.business.upstream.request;

import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import java.util.Objects;
import java.util.Optional;

public class RemoteRequest<T> implements IRequest<T> {
  private final T body;

  private RemoteRequest(T body) {
    Objects.requireNonNull(body);
    this.body = body;
  }

  @Override
  public Optional<IRequestWithToken> withToken() {
    if (body instanceof IRequestWithToken) {
     return Optional.of(((IRequestWithToken) body));
    }
    return Optional.empty();
  }

  @Override
  public T getBody() {
    return body;
  }

  public static <T> RemoteRequest.Builder<T> newBuilder() {
    return new RemoteRequest.Builder<>();
  }

  public static class Builder<T> {
    private T body;

    private Builder() {}

    public RemoteRequest.Builder<T> setBody(T body) {
      Objects.requireNonNull(body);
      this.body = body;
      return this;
    }

    public RemoteRequest<T> build() {
      return new RemoteRequest<>(body);
    }
  }
}
