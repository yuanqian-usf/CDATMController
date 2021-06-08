package com.circulardollar.cdatm.business.upstream.validation.auth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import org.junit.Test;

public class LoginRecordRemoteValidatorTest {

  @Test
  public void validate_01() {
    assertTrue(new LoginRecordRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertTrue(
        new LoginRecordRemoteValidator()
            .validate(
                new ILoginRecord() {

                  @Override
                  public ICardRecord getCard() {
                    return null;
                  }

                  @Override
                  public IPinRecord getPin() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertTrue(
        new LoginRecordRemoteValidator()
            .validate(
                new ILoginRecord() {

                  @Override
                  public ICardRecord getCard() {
                    return new ICardRecord() {
                      @Override
                      public String getHolderName() {
                        return null;
                      }

                      @Override
                      public String getCardNumber() {
                        return null;
                      }

                      @Override
                      public String getCVC() {
                        return null;
                      }

                      @Override
                      public String getExpirationDate() {
                        return null;
                      }
                    };
                  }

                  @Override
                  public IPinRecord getPin() {
                    return null;
                  }
                })
            .isPresent());
  }



  @Test
  public void validate_04() {
    assertTrue(
        new LoginRecordRemoteValidator()
            .validate(
                new ILoginRecord() {

                  @Override
                  public ICardRecord getCard() {
                    return null;
                  }

                  @Override
                  public IPinRecord getPin() {
                    return () -> null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_05() {
    assertFalse(
        new LoginRecordRemoteValidator()
            .validate(
                new ILoginRecord() {

                  @Override
                  public ICardRecord getCard() {
                    return new ICardRecord() {
                      @Override
                      public String getHolderName() {
                        return null;
                      }

                      @Override
                      public String getCardNumber() {
                        return null;
                      }

                      @Override
                      public String getCVC() {
                        return null;
                      }

                      @Override
                      public String getExpirationDate() {
                        return null;
                      }
                    };
                  }

                  @Override
                  public IPinRecord getPin() {
                    return () -> null;
                  }
                })
            .isPresent());
  }
}
