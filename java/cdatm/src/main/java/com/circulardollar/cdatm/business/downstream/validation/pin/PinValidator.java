package com.circulardollar.cdatm.business.downstream.validation.pin;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.utils.Messages;
import com.circulardollar.cdatm.constant.Errors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class PinValidator implements IValidator<IPin> {

  private static final String GET_PIN = "GET_PIN";
  private static final String GET_PIN_NUMBER = "GET_PIN_NUMBER";

  private final IATMConfigurations atmConfigurations;

  public PinValidator(IATMConfigurations atmConfigurations) {
    this.atmConfigurations = atmConfigurations;
  }

  @Override
  public Optional<IError> validate(IPin pin) {
    List<String> errorMessages = new ArrayList<>();
    if (pin == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IPin.class));
    } else {
      if (StringUtils.isBlank(pin.getPinNumber())) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_PIN));
      }
      if (atmConfigurations.getMinPinLength() > pin.getPinNumber().length()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_PIN, Arrays.asList(GET_PIN_NUMBER, Errors.BELOW_MIN_LENGTH.getValue())));
      }
      if (atmConfigurations.getMaxPinLength() < pin.getPinNumber().length()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_PIN, Arrays.asList(GET_PIN_NUMBER, Errors.EXCEEDS_MAX_LENGTH.getValue())));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IPin.class, errorMessages));
    }
    return Optional.empty();
  }
}
