package com.circulardollar.cdatm.business.downstream.validation.card;

import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.utils.Messages;
import com.circulardollar.cdatm.constant.Errors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class CardValidator implements IValidator<ICard> {

  public static final String GET_CARD = "GET_CARD";
  public static final String GET_CARD_NUMBER = "GET_CARD_NUMBER";

  private final IATMConfigurations atmConfigurations;

  public CardValidator(IATMConfigurations atmConfigurations) {
    this.atmConfigurations = atmConfigurations;
  }

  @Override
  public Optional<IError> validate(ICard card) {
    List<String> errorMessages = new ArrayList<>();
    if (card == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(ICard.class));

    } else if (StringUtils.isBlank(card.getCardNumber())) {
      errorMessages.add(
          Messages.getIllegalArgumentExceptionForMethod(
              GET_CARD, Arrays.asList(GET_CARD, GET_CARD_NUMBER)));
    } else {
      if (atmConfigurations.getMinCardNumberLength() > card.getCardNumber().length()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CARD,
                Arrays.asList(GET_CARD, GET_CARD_NUMBER, Errors.BELOW_MIN_LENGTH.getValue())));
      }
      if (atmConfigurations.getMaxCardNumberLength() < card.getCardNumber().length()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CARD,
                Arrays.asList(GET_CARD, GET_CARD_NUMBER, Errors.EXCEEDS_MAX_LENGTH.getValue())));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(ICard.class, errorMessages));
    }
    return Optional.empty();
  }
}