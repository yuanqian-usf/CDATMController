package com.circulardollar.cdatm.business.downstream.validation.session;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.session.ATMSessionController;
import org.junit.Test;

public class SessionControllerValidatorTest {

  @Test
  public void validate_null() {
    assertTrue(new SessionControllerValidator().validate(null).isPresent());
  }

  @Test
  public void validate_not_null() {
    assertFalse(new SessionControllerValidator().validate(new ATMSessionController()).isPresent());
  }
}
