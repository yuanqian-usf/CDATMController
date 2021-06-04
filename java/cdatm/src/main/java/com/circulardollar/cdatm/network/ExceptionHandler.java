package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.Errors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExceptionHandler {
  public static <T> RemoteResponse<T> handleFuture(CompletableFuture<RemoteResponse<T>> future) {
    List<String> errorMessages = new ArrayList<>();
    RemoteResponse<T> response = null;
    try {
      response = future.get();
    } catch (InterruptedException e) {
      errorMessages.add(Errors.INTERRUPTED_EXCEPTION.getValue());
    } catch (ExecutionException e) {
      errorMessages.add(Errors.EXECUTION_EXCEPTION.getValue());
    }
    if (!errorMessages.isEmpty()) {
      return RemoteResponse.<T>newBuilder()
          .setError(ErrorRecord.of(future.getClass(), errorMessages))
          .build();
    }
    return response;
  }
}
