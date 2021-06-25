package com.circulardollar.cdatm.business.downstream.model.auth;

import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertNotNull;

public class LoginTest {

    @Test(expected = NullPointerException.class)
    public void not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Login.newBuilder().build();
    }

    @Test(expected = NullPointerException.class)
    public void setCard_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Login.newBuilder().setCard(null);
    }

    @Test(expected = NullPointerException.class)
    public void setPin_not_null_whenExceptionThrown_thenExpectationSatisfied() {
        Login.newBuilder().setPin(null);
    }

    @Test public void getCard() {
        assertNotNull(new Login(
            Card.newBuilder().setHolderName(randomString()).setCardNumber(randomString())
                .setCvc(randomString()).setExpirationDate(randomString()).build(),
            Pin.newBuilder().setPinNumber(randomString()).build()).getCard());
    }

    @Test public void getPin() {
        assertNotNull(new Login(
            Card.newBuilder().setHolderName(randomString()).setCardNumber(randomString())
                .setCvc(randomString()).setExpirationDate(randomString()).build(),
            Pin.newBuilder().setPinNumber(randomString()).build()).getPin());
    }

    @Test public void newBuilder() {
        assertNotNull(Login.newBuilder().setCard(
            Card.newBuilder().setHolderName(randomString()).setCardNumber(randomString())
                .setCvc(randomString()).setExpirationDate(randomString()).build())
            .setPin(Pin.newBuilder().setPinNumber(randomString()).build()).build());
    }

  @Test public void testToString() {
    assertNotNull(new Login(
        Card.newBuilder().setHolderName(randomString()).setCardNumber(randomString())
            .setCvc(randomString()).setExpirationDate(randomString()).build(),
        Pin.newBuilder().setPinNumber(randomString()).build()).toString());
  }
}
