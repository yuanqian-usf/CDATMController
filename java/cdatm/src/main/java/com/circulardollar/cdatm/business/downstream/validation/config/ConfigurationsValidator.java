package com.circulardollar.cdatm.business.downstream.validation.config;

import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.constant.APIVersions;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.utils.Messages;
import com.circulardollar.cdatm.constant.Errors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConfigurationsValidator implements IValidator<IATMConfigurations> {

  private static final String GET_CONFIGURATIONS = "GET_CONFIGURATIONS";

  @Override
  public Optional<IError> validate(IATMConfigurations config) {
    List<String> errorMessages = new ArrayList<>();
    if (config == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IATMConfigurations.class));
    } else {
      if (config.getMinCardNumberLength() < IATMConfigurations.MIN_CARD_NUMBER_LENGTH) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(ICard.class.getName(), Errors.BELOW_MIN_LENGTH.getValue())));
      }
      if (config.getMaxCardNumberLength() > IATMConfigurations.MAX_CARD_NUMBER_LENGTH) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(ICard.class.getName(), Errors.EXCEEDS_MAX_LENGTH.getValue())));
      }
      if (config.getMinCardNumberLength() > config.getMaxCardNumberLength()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(ICard.class.getName(), Errors.INVALID_LENGTH.getValue())));
      }
      if (config.getMinPinLength() < IATMConfigurations.MIN_PIN_LENGTH) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IPin.class.getName(), Errors.BELOW_MIN_LENGTH.getValue())));
      }
      if (config.getMaxPinLength() > IATMConfigurations.MAX_PIN_LENGTH) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IPin.class.getName(), Errors.EXCEEDS_MAX_LENGTH.getValue())));
      }
      if (config.getMinPinLength() > config.getMaxPinLength()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IPin.class.getName(), Errors.INVALID_LENGTH.getValue())));
      }
      if (config.getMinDepositAmount() < IATMConfigurations.MIN_DEPOSIT_AMOUNT) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IDeposit.class.getName(), Errors.BELOW_MIN_AMOUNT.getValue())));
      }
      if (config.getMaxDepositAmount() > IATMConfigurations.MAX_DEPOSIT_AMOUNT) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IDeposit.class.getName(), Errors.EXCEEDS_MAX_AMOUNT.getValue())));
      }
      if (config.getMinDepositAmount() > config.getMaxDepositAmount()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IDeposit.class.getName(), Errors.INVALID_AMOUNT.getValue())));
      }
      if (config.getMinWithdrawAmount() < IATMConfigurations.MIN_WITHDRAW_AMOUNT) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IWithdraw.class.getName(), Errors.BELOW_MIN_AMOUNT.getValue())));
      }
      if (config.getMaxWithdrawAmount() > IATMConfigurations.MAX_WITHDRAW_AMOUNT) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IWithdraw.class.getName(), Errors.EXCEEDS_MAX_AMOUNT.getValue())));
      }
      if (config.getMinWithdrawAmount() > config.getMaxWithdrawAmount()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(IWithdraw.class.getName(), Errors.INVALID_AMOUNT.getValue())));
      }
      if (APIVersions.UNSPECIFIED == config.getAPIVersion()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_CONFIGURATIONS,
                Arrays.asList(APIVersions.class.getName(), Errors.UNSUPPORTED_API_VERSION.getValue())));
      }
    }

    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IATMConfigurations.class, errorMessages));
    }
    return Optional.empty();
  }
}
