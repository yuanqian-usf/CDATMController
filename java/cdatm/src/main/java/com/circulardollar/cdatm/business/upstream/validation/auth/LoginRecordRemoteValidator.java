package com.circulardollar.cdatm.business.upstream.validation.auth;

import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginRecordRemoteValidator implements IRemoteValidator<ILoginRecord> {
  private static final String POST_LOGIN = "POST_LOGIN";

  @Override
  public Optional<IErrorRecord> validate(ILoginRecord login) {
    List<String> errorMessages = new ArrayList<>();
    if (login == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(ILoginRecord.class));
    } else {
      if (login.getPin() == null || login.getCard() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(POST_LOGIN));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(ILoginRecord.class, errorMessages));
    }
    return Optional.empty();
  }
}
