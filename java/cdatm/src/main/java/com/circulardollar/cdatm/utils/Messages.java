package com.circulardollar.cdatm.utils;

import java.util.List;

public final class Messages {

  private Messages() {}

  public static String getIllegalArgumentExceptionForClass(Class<?> className) {
    return "Can't instantiate class: " + (className == null ? null : className.getName());
  }

  public static String getNullPointerExceptionForMethod(String methodName) {
    return "Can't invoke function: " + methodName;
  }

  public static String getIllegalArgumentExceptionForMethod(
      String methodName, List<String> errorMessage) {
    StringBuilder sb = new StringBuilder("Can't invoke function: ");
    sb.append(methodName);
    sb.append(" for ");
    sb.append(String.join(", ", errorMessage));
    return sb.toString();
  }
}
