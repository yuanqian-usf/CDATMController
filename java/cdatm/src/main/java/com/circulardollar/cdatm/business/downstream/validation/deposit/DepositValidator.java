package com.circulardollar.cdatm.business.downstream.validation.deposit;

import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.utils.Messages;
import com.circulardollar.cdatm.constant.Errors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DepositValidator implements IValidator<IDeposit> {

  private static final String PUT_DEPOSIT = "PUT_DEPOSIT";

  private final IATMConfigurations atmConfigurations;

  public DepositValidator(IATMConfigurations atmConfigurations) {
    this.atmConfigurations = atmConfigurations;
  }

  public Optional<IError> validate(IDeposit deposit) {
    List<String> errorMessages = new ArrayList<>();
    if (deposit == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IDeposit.class));
    } else {
      if (deposit.getAmount() <= 0) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                PUT_DEPOSIT, Collections.singletonList(Errors.INVALID_AMOUNT.getValue())));
      }
      if (deposit.getAmount() > atmConfigurations.getMaxDepositAmount()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                PUT_DEPOSIT, Collections.singletonList(Errors.EXCEEDS_MAX_AMOUNT.getValue())));
      }
      if (deposit.getAmount() < atmConfigurations.getMinDepositAmount()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                PUT_DEPOSIT, Collections.singletonList(Errors.BELOW_MIN_AMOUNT.getValue())));
      }
    }

    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IDeposit.class, errorMessages));
    }
    return Optional.empty();
  }
}
