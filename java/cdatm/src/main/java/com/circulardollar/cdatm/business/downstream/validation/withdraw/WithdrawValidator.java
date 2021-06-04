package com.circulardollar.cdatm.business.downstream.validation.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//TODO: Mock Dependencies
public class WithdrawValidator implements IValidator<IWithdraw> {

  private static final String GET_WITHDRAW_AMOUNT = "GET_WITHDRAW_AMOUNT";
  private static final String INVALID_WITHDRAW_AMOUNT = "INVALID_WITHDRAW_AMOUNT";
  private static final String EXCEEDS_MAX_AMOUNT = "EXCEEDS_MAX_AMOUNT";
  private static final String BELOW_MIN_AMOUNT = "BELOW_MIN_AMOUNT";
  private static final String EXCEEDS_BALANCE_AMOUNT = "EXCEEDS_BALANCE_AMOUNT";
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
      if (withdraw.getAccount() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT));
      }
      if (withdraw.getAmount() <= 0) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_WITHDRAW_AMOUNT, Collections.singletonList((INVALID_WITHDRAW_AMOUNT))));
      }
      if (withdraw.getAmount() > withdraw.getAccount().getBalance()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_WITHDRAW_AMOUNT, Collections.singletonList((EXCEEDS_BALANCE_AMOUNT))));
      }
      if (withdraw.getAmount() > atmConfigurations.getMaxWithdrawAmount()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_WITHDRAW_AMOUNT, Collections.singletonList((EXCEEDS_MAX_AMOUNT))));
      }
      if (withdraw.getAmount() < atmConfigurations.getMinWithdrawAmount()) {
        errorMessages.add(
            Messages.getIllegalArgumentExceptionForMethod(
                GET_WITHDRAW_AMOUNT, Collections.singletonList((BELOW_MIN_AMOUNT))));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IWithdraw.class, errorMessages));
    }
    return Optional.empty();
  }
}
