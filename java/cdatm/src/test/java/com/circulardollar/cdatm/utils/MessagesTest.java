package com.circulardollar.cdatm.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertEquals;

public class MessagesTest {

  @Test
  public void getIllegalArgumentExceptionForClass() {
    assertEquals(
        "Can't instantiate class: " + null, Messages.getIllegalArgumentExceptionForClass(null));
    assertEquals(
        "Can't instantiate class: " + Object.class.getName(),
        Messages.getIllegalArgumentExceptionForClass(Object.class));
  }

  @Test
  public void getNullPointerExceptionForMethod() {
    String methodName = randomString();
    assertEquals(
        "Can't invoke function: " + methodName,
        Messages.getNullPointerExceptionForMethod(methodName));
  }

  @Test
  public void getIllegalArgumentExceptionForMethod() {
    String methodName = randomString();
    List<String> errorMessages = Arrays.asList("a", "b", "c");
    assertEquals(
        "Can't invoke function: " + methodName + " for " + "a, b, c",
        Messages.getIllegalArgumentExceptionForMethod(methodName, errorMessages));
  }
}
