package com.circulardollar.cdatm.business.upstream.validation.auth;

import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogoutRecordRemoteValidator implements IRemoteValidator<ILogoutRecord> {
  private static final String POST_LOGOUT = "POST_LOGOUT";

  @Override
  public Optional<IErrorRecord> validate(ILogoutRecord logout) {
    List<String> errorMessages = new ArrayList<>();
    if (logout == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(ILogoutRecord.class));
    } else {
      if (logout.getTimeStamp() == null) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(POST_LOGOUT));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(ILogoutRecord.class, errorMessages));
    }
    return Optional.empty();
  }
}
