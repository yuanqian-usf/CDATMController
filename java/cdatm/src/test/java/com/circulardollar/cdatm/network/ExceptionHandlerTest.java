package com.circulardollar.cdatm.network;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class ExceptionHandlerTest {

  @Test
  public void handleFuture_01() {
    CompletableFuture future = mock(CompletableFuture.class);
    try {
      when(future.get()).thenThrow(new InterruptedException());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    assertNotNull(ExceptionHandler.handleFuture(future).getError());
  }

  @Test
  public void handleFuture_02() {
    CompletableFuture future = mock(CompletableFuture.class);
    try {
      when(future.get()).thenThrow(new ExecutionException(new Throwable()));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    assertNotNull(ExceptionHandler.handleFuture(future).getError());

  }

  @Test
  public void handleFuture_03() {
  }
}
