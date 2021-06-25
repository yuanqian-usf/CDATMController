package com.circulardollar.cdatm.business.upstream.validation.deposit;

import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DepositRecordRemoteValidator implements IRemoteValidator<IDepositRecord> {

  private static final String POST_DEPOSIT = "POST_DEPOSIT";
  private static final String GET_ACCOUNT = "GET_ACCOUNT";

  public Optional<IErrorRecord> validate(IDepositRecord deposit) {
    List<String> errorMessages = new ArrayList<>();
    if (deposit == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IDepositRecord.class));
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
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(IDepositRecord.class, errorMessages));
    }
    return Optional.empty();
  }
}
