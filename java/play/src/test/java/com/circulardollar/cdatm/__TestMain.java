package com.circulardollar.cdatm;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class __TestMain {



  @Test
  public void test_insertCard_null_response_not_null() {
    assertNotNull(__Main.nonCSATMController().insertCard(null));
  }

  @Test
  public void testDummy() {
    assertNotNull(__Main.nonCSATMController());
  }
}
