package com.circulardollar.cdatm.business.downstream.model.card;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Test;

public class CardTest {

  @Test(expected = NullPointerException.class)
  public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Card.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void setHolderName_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Card.newBuilder().setHolderName(null);
  }

  @Test(expected = NullPointerException.class)
  public void setCardNumber_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    Card.newBuilder().setCardNumber(null);
  }

  @Test(expected = NullPointerException.class)
  public void setCVC_not_null_whenExceptionThrown_thenExpectationSatisfied_02() {
    Card.newBuilder().setCvc(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void setExpirationDate_not_null_whenExceptionThrown_thenExpectationSatisfied_02() {
    Card.newBuilder().setExpirationDate(null).build();
  }

  @Test
  public void getHolderName() {
    assertNotNull(new Card(anyString(), anyString(), anyString(), anyString()).getHolderName());
  }

  @Test
  public void getCardNumber() {
    assertNotNull(new Card(anyString(), anyString(), anyString(), anyString()).getCardNumber());
  }

  @Test
  public void getCVC() {
    assertNotNull(new Card(anyString(), anyString(), anyString(), anyString()).getCVC());
  }

  @Test
  public void getExpirationDate() {
    assertNotNull(new Card(anyString(), anyString(), anyString(), anyString()).getExpirationDate());
  }

  @Test
  public void newBuilder() {
    assertNotNull(Card.newBuilder()
        .setHolderName(anyString())
        .setCardNumber(anyString())
        .setCvc(anyString())
        .setExpirationDate(anyString())
        .build());
  }
}
