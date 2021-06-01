package com.circulardollar.cdatm.business.downstream.validation.account;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class AccountValidator implements IValidator<IAccount> {
  private static final String GET_ACCOUNT_NUMBER = "GET_ACCOUNT_NUMBER";

  @Override
  public Optional<IError> validate(IAccount account) {
    List<String> errorMessages = new ArrayList<>();
    if (account == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IAccount.class));
    } else {
      if (StringUtils.isBlank(account.getAccountNumber())) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT_NUMBER));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IAccount.class, errorMessages));
    }
    return Optional.empty();
  }
}
