package com.circulardollar.cdatm.business.upstream.validation.withdraw;

import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WithdrawRecordRemoteValidator implements IRemoteValidator<IWithdrawRecord> {

  private static final String POST_WITHDRAW = "POST_WITHDRAW";
  private static final String GET_ACCOUNT = "GET_ACCOUNT";

  @Override
  public Optional<IErrorRecord> validate(IWithdrawRecord withdraw) {
    List<String> errorMessages = new ArrayList<>();
    if (withdraw == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IWithdrawRecord.class));
    } else {
      if (withdraw.getAccount() == null || withdraw.getAmount() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT));
      } else {
        if (withdraw.getAmount() <= 0) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(
                  POST_WITHDRAW, Collections.singletonList((Errors.INVALID_AMOUNT.getValue()))));
        }
        if ((long) withdraw.getAmount() > withdraw.getAccount().getBalance()) {
          errorMessages.add(
              Messages.getIllegalArgumentExceptionForMethod(
                  POST_WITHDRAW,
                  Collections.singletonList((Errors.EXCEEDS_BALANCE_AMOUNT.getValue()))));
        }
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(IWithdrawRecord.class, errorMessages));
    }
    return Optional.empty();
  }
}
