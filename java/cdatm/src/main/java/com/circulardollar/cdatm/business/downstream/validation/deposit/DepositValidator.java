package com.circulardollar.cdatm.business.downstream.validation.deposit;

import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.utils.Messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DepositValidator implements IValidator<IDeposit> {

  private static final String POST_DEPOSIT = "POST_DEPOSIT";
  private static final String GET_ACCOUNT = "GET_ACCOUNT";

  private final IATMConfigurations atmConfigurations;

  public DepositValidator(IATMConfigurations atmConfigurations) {
    this.atmConfigurations = atmConfigurations;
  }

  public Optional<IError> validate(IDeposit deposit) {
    List<String> errorMessages = new ArrayList<>();
    if (deposit == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IDeposit.class));
    } else {
      if (deposit.getAccount() == null || deposit.getAmount() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT));
      } else {
        if (deposit.getAmount() <= 0) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(
                  POST_DEPOSIT, Collections.singletonList(Errors.INVALID_AMOUNT.getValue())));
        }
        if ((long) deposit.getAmount() + deposit.getAccount().getBalance() > Integer.MAX_VALUE) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(
                  POST_DEPOSIT,
                  Collections.singletonList((Errors.EXCEEDS_BALANCE_AMOUNT.getValue()))));
        }
        if (deposit.getAmount() > atmConfigurations.getMaxDepositAmount()) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(
                  POST_DEPOSIT, Collections.singletonList(Errors.EXCEEDS_MAX_AMOUNT.getValue())));
        }
        if (deposit.getAmount() < atmConfigurations.getMinDepositAmount()) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(
                  POST_DEPOSIT, Collections.singletonList(Errors.BELOW_MIN_AMOUNT.getValue())));
        }
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IDeposit.class, errorMessages));
    }
    return Optional.empty();
  }
}
