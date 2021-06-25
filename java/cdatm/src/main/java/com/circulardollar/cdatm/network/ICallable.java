package com.circulardollar.cdatm.network;

import java.util.concurrent.Callable;

public abstract class ICallable<T, V> implements Callable<V> {

  private final T input;

  public ICallable(T input) {
    this.input = input;
  }

  @Override
  public V call() throws Exception {
    return null;
  }

  public T getInput() {
    return input;
  }
}
