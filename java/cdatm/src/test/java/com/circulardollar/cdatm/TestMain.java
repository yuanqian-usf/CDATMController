package com.circulardollar.cdatm;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestMain {
  @Test
  public void testDummy() {
    assertNotNull(Main.nonCSATMController());
  }
}
