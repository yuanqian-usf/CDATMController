package com.circulardollar.cdatm.business.downstream.validation.card;

import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.config.IATMConfigurations;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomString;
import static com.circulardollar.cdatm.TestBase.validRandomCardNumber;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CardValidatorTest {
  @Test
  public void validate_01() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().build()).validate(null).isPresent());
  }

  @Test
  public void validate_02_01() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().build())
            .validate(
                new ICard() {
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
                })
            .isPresent());
  }

  @Test
  public void validate_02_02() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Card.newBuilder()
                    .setCardNumber("")
                    .setCvc(randomString())
                    .setExpirationDate(randomString())
                    .setHolderName(randomString())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_02_03() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Card.newBuilder()
                    .setCardNumber(" ")
                    .setCvc(randomString())
                    .setExpirationDate(randomString())
                    .setHolderName(randomString())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertFalse(
        new CardValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Card.newBuilder()
                    .setCardNumber(validRandomCardNumber())
                    .setCvc(randomString())
                    .setExpirationDate(randomString())
                    .setHolderName(randomString())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_04() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().setMinCardNumberLength(12).build())
            .validate(
                Card.newBuilder()
                    .setCardNumber(randomString())
                    .setCvc(randomString())
                    .setExpirationDate(randomString())
                    .setHolderName(randomString())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_04_02() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().setMinCardNumberLength(12).build())
            .validate(
                Card.newBuilder()
                    .setCardNumber("1")
                    .setCvc(randomString())
                    .setExpirationDate(randomString())
                    .setHolderName(randomString())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_05() {
    assertTrue(
        new CardValidator(ATMConfigurations.newBuilder().setMaxCardNumberLength(1).build())
            .validate(
                Card.newBuilder()
                    .setCardNumber(randomString())
                    .setCvc(randomString())
                    .setExpirationDate(randomString())
                    .setHolderName(randomString())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_06() {
    IATMConfigurations configurations = mock(ATMConfigurations.class);
    ICard card = mock(Card.class);
    String randomCardNumber = randomString();
    when(card.getCardNumber()).thenReturn(randomCardNumber);
    when(configurations.getMinCardNumberLength()).thenReturn(1);
    when(configurations.getMaxCardNumberLength()).thenReturn(100);
    CardValidator cardValidator = new CardValidator(configurations);
    cardValidator.validate(card);
    verify(card, times(1)).getCardNumber();
    verify(configurations, times(1)).getMinCardNumberLength();
    verify(configurations, times(1)).getMaxCardNumberLength();
  }
}
