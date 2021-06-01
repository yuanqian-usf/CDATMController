package com.circulardollar.cdatm;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class __TestMain {



  @Test
  public void test_insertCard_null_response_not_null() {
    assertNotNull(__Main.__ATMController().insertCard(null));
  }

  @Test
  public void testDummy() {
    assertNotNull(__Main.__ATMController());
  }
}
