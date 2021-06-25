package com.circulardollar.cdatm.business.upstream.validation.auth;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class AuthRecordRemoteValidator implements IRemoteValidator<IAuthRecord> {
  private static final String POST_AUTH = "POST_AUTH";
  private static final String GET_ACCOUNTS = "GET_ACCOUNTS";
  private static final String GET_ACCOUNT_NUMBER = "GET_ACCOUNT_NUMBER";

  @Override
  public Optional<IErrorRecord> validate(IAuthRecord auth) {
    List<String> errorMessages = new ArrayList<>();
    if (auth == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IAuthRecord.class));
    } else {
      String tokenId = auth.getTokenId();
      if (tokenId == null || tokenId.length() == 0 || tokenId.trim().length() == 0) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(POST_AUTH));
      } else if (auth.getAccounts() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNTS));
      } else {
        for (IAccountRecord accountRecord : auth.getAccounts())
          if (StringUtils.isBlank(accountRecord.getAccountNumber())) {
            errorMessages.add(Messages.getNullPointerExceptionForMethod(GET_ACCOUNT_NUMBER));
            break;
          }
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(IAuthRecord.class, errorMessages));
    }
    return Optional.empty();
  }
}
