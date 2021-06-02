package com.circulardollar.cdatm.business.downstream.model.pin;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Test;

public class PinTest {

  @Test(expected = NullPointerException.class)
  public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Pin.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setPinNumber_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Pin.newBuilder().setPinNumber(null);
  }

  @Test
  public void getPinNumber() {
    assertNotNull(new Pin(anyString()).getPinNumber());
  }

  @Test
  public void newBuilder() {
    assertNotNull(Pin.newBuilder().setPinNumber(anyString()).build());
  }
}
