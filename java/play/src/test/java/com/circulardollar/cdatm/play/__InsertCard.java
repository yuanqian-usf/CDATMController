package com.circulardollar.cdatm.play;

import static com.circulardollar.cdatm.__TestBase.RANDOM_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.__TestBase.anyString;
import static com.circulardollar.cdatm.__TestBase.emptyString;
import static com.circulardollar.cdatm.__TestBase.fixedLengthRandomNumber;
import static com.circulardollar.cdatm.__TestBase.randomLengthRandomNumber;
import static com.circulardollar.cdatm.__TestBase.validRandomCard;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.Main;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.config.IATMConfigurations;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;

public class __InsertCard {

  Card.Builder pendingCardNumberCardBuilder;

  @Before
  public void setup() {
    pendingCardNumberCardBuilder =
        Card.newBuilder()
            .setHolderName(anyString())
            .setCvc(anyString())
            .setExpirationDate(anyString());
  }

  @Test
  public void test_insertCard_response_not_null() {
    assertNotNull(
        Main.nonCSATMController()
            .insertCard(pendingCardNumberCardBuilder.setCardNumber(anyString()).build()));
  }

  @Test
  public void test_insertCard_response_shouldExpectError() {
    assertNotNull(
        Main.nonCSATMController()
            .insertCard(pendingCardNumberCardBuilder.setCardNumber(anyString()).build())
            .getError());
  }

  @Test
  public void test_insertCard_validate_cardNumber_zeroLength_shouldExpectError() {
    assertNotNull(
        Main.nonCSATMController()
            .insertCard(pendingCardNumberCardBuilder.setCardNumber(emptyString()).build())
            .getError());
  }

  @Test
  public void test_insertCard_validate_cardNumber_invalidLength_shouldExpectError_BELOW_MIN() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      IError error =
          Main.nonCSATMController()
              .insertCard(
                  pendingCardNumberCardBuilder
                      .setCardNumber(
                          randomLengthRandomNumber(
                              IATMConfigurations.MIN_CARD_NUMBER_LENGTH - 2,
                              IATMConfigurations.MIN_CARD_NUMBER_LENGTH))
                      .build())
              .getError();
      assertNotNull(error);
    }
  }

  @Test
  public void test_insertCard_validate_cardNumber_invalidLength_shouldExpectError_ABOVE_MAX() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      IError error =
          Main.nonCSATMController()
              .insertCard(
                  pendingCardNumberCardBuilder
                      .setCardNumber(
                          fixedLengthRandomNumber(IATMConfigurations.MAX_CARD_NUMBER_LENGTH + 1))
                      .build())
              .getError();
      assertNotNull(error);
    }
  }

  @Test
  public void test_insertCard_validate_cardNumber_validFixedLength_shouldExpectBody() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      assertNotNull(
          Main.nonCSATMController()
              .insertCard(
                  pendingCardNumberCardBuilder
                      .setCardNumber(
                          fixedLengthRandomNumber(IATMConfigurations.MAX_CARD_NUMBER_LENGTH))
                      .build())
              .getBody());
    }
  }

  @Test
  public void test_insertCard_validate_cardNumber_validFixedLength_shouldNotExpectError() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      assertNull(
          Main.nonCSATMController()
              .insertCard(
                  pendingCardNumberCardBuilder
                      .setCardNumber(
                          fixedLengthRandomNumber(IATMConfigurations.MAX_CARD_NUMBER_LENGTH))
                      .build())
              .getError());
    }
  }

  @Test
  public void test_insertCard_cardNumber_validRandomLength_insertCard_shouldNotExpectError() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      assertNull(
          Main.nonCSATMController()
              .insertCard(
                  pendingCardNumberCardBuilder
                      .setCardNumber(
                          randomLengthRandomNumber(
                              IATMConfigurations.MIN_CARD_NUMBER_LENGTH,
                              IATMConfigurations.MAX_CARD_NUMBER_LENGTH))
                      .build())
              .getError());
    }
  }

  @Test
  public void test_insertCard_cardNumber_validRandomLength_insertCard_shouldExpectTrue() {
    for (int i = 0; i < RANDOM_TEST_ITERATION; i++) {
      Boolean actual =
          Main.nonCSATMController()
              .insertCard(
                  pendingCardNumberCardBuilder
                      .setCardNumber(
                          randomLengthRandomNumber(
                              IATMConfigurations.MIN_CARD_NUMBER_LENGTH,
                              IATMConfigurations.MAX_CARD_NUMBER_LENGTH))
                      .build())
              .getBody();
      assertTrue(actual);
    }
  }

  @Test
  public void test_insertCard_repeat_shouldExpectError() {
    IATMController controller = Main.nonCSATMController();
    assertNull(controller.insertCard(validRandomCard()).getError());
    assertNotNull(controller.insertCard(validRandomCard()).getError());
  }

  @Test
  public void test_insertCard_repeat_async_shouldExpectError()
      throws ExecutionException, InterruptedException {
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      IATMController controller = Main.nonCSATMController();
      CompletableFuture<IResponse<Boolean, IError>> opAsync1 =
          CompletableFuture.supplyAsync(() -> controller.insertCard(validRandomCard()));
      CompletableFuture<IResponse<Boolean, IError>> opAsync2 =
          CompletableFuture.supplyAsync(() -> controller.insertCard(validRandomCard()));
      Boolean isXOR =
          opAsync1
              .thenCombine(
                  opAsync2,
                  ((opAsync1Res, opAsync2Res) ->
                      opAsync1Res.getBody() == null ^ opAsync2Res.getBody() == null))
              .get();
      assertTrue(isXOR);
    }
  }
}
