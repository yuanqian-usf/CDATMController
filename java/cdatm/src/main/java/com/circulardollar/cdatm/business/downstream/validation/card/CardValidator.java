package com.circulardollar.cdatm.business.downstream.validation.card;

import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.utils.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CardValidator implements IValidator<ICard> {

    private static final String GET_CARD = "GET_CARD";
    private static final String GET_CARD_NUMBER = "GET_CARD_NUMBER";

    private final IATMConfigurations atmConfigurations;

    public CardValidator(IATMConfigurations atmConfigurations) {
        this.atmConfigurations = atmConfigurations;
    }

    @Override public Optional<IError> validate(ICard card) {
        List<String> errorMessages = new ArrayList<>();
        if (card == null) {
            errorMessages.add(Messages.getIllegalArgumentExceptionForClass(ICard.class));
            return Optional.of(Error.of(ICard.class, errorMessages));
        }
        String cardNumber = card.getCardNumber();
        if (cardNumber == null || cardNumber.length() == 0
            || cardNumber.trim().length() == 0) {
            errorMessages.add(Messages.getIllegalArgumentExceptionForMethod(GET_CARD,
                Arrays.asList(GET_CARD, GET_CARD_NUMBER)));
        } else {
            if (atmConfigurations.getMinCardNumberLength() > cardNumber.length()) {
                errorMessages.add(Messages.getIllegalArgumentExceptionForMethod(GET_CARD,
                    Arrays.asList(GET_CARD, GET_CARD_NUMBER, Errors.BELOW_MIN_LENGTH.getValue())));
            }
            if (atmConfigurations.getMaxCardNumberLength() < cardNumber.length()) {
                errorMessages.add(Messages.getIllegalArgumentExceptionForMethod(GET_CARD, Arrays
                    .asList(GET_CARD, GET_CARD_NUMBER, Errors.EXCEEDS_MAX_LENGTH.getValue())));
            }
        }
        if (!errorMessages.isEmpty()) {
            return Optional.of(Error.of(ICard.class, errorMessages));
        }
        return Optional.empty();
    }
}
