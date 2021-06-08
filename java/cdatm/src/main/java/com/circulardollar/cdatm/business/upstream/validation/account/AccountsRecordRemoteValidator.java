package com.circulardollar.cdatm.business.upstream.validation.account;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class AccountsRecordRemoteValidator implements IRemoteValidator<IAccountsRecord> {
  private static final String GET_ACCOUNTS = "GET_ACCOUNTS";
  private static final String GET_ACCOUNT_NUMBER = "GET_ACCOUNT_NUMBER";

  @Override
  public Optional<IErrorRecord> validate(IAccountsRecord accountsRecord) {
    List<String> errorMessages = new ArrayList<>();
    if (accountsRecord == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IAccountRecord.class));
    } else {
      if (accountsRecord.getAccounts() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNTS));
      } else {
        for (IAccountRecord accountRecord : accountsRecord.getAccounts())
          if (StringUtils.isBlank(accountRecord.getAccountNumber())) {
            errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT_NUMBER));
            break;
          }
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(IAccountsRecord.class, errorMessages));
    }
    return Optional.empty();
  }
}
