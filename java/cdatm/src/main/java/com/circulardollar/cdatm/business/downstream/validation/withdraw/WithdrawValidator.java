package com.circulardollar.cdatm.business.downstream.validation.withdraw;

import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
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

public class WithdrawValidator implements IValidator<IWithdraw> {

  private static final String POST_WITHDRAW = "POST_WITHDRAW";
  private static final String GET_ACCOUNT = "GET_ACCOUNT";

  private final IATMConfigurations atmConfigurations;

  public WithdrawValidator(IATMConfigurations atmConfigurations) {
    this.atmConfigurations = atmConfigurations;
  }

  @Override
  public Optional<IError> validate(IWithdraw withdraw) {
    List<String> errorMessages = new ArrayList<>();
    if (withdraw == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IWithdraw.class));
    } else {
      if (withdraw.getAccount() == null || withdraw.getAmount() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT));
      } else {
        if (withdraw.getAmount() <= 0) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(POST_WITHDRAW, Collections.singletonList((Errors.INVALID_AMOUNT.getValue()))));
        }
        if ((long) withdraw.getAmount() > withdraw.getAccount().getBalance()) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(POST_WITHDRAW, Collections.singletonList((Errors.EXCEEDS_BALANCE_AMOUNT.getValue()))));
        }
        if (withdraw.getAmount() > atmConfigurations.getMaxWithdrawAmount()) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(POST_WITHDRAW, Collections.singletonList((Errors.EXCEEDS_MAX_AMOUNT.getValue()))));
        }
        if (withdraw.getAmount() < atmConfigurations.getMinWithdrawAmount()) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(POST_WITHDRAW, Collections.singletonList((Errors.BELOW_MIN_AMOUNT.getValue()))));
        }
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IWithdraw.class, errorMessages));
    }
    return Optional.empty();
  }
}
