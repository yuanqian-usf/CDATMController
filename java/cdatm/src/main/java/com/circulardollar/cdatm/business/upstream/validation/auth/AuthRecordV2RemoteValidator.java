package com.circulardollar.cdatm.business.upstream.validation.auth;

import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthRecordV2RemoteValidator implements IRemoteValidator<IAuthRecordV2> {
  private static final String POST_AUTH = "POST_AUTH";

  @Override
  public Optional<IErrorRecord> validate(IAuthRecordV2 authV2) {
    List<String> errorMessages = new ArrayList<>();
    if (authV2 == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IAuthRecordV2.class));
    } else {
      String tokenId = authV2.getTokenId();
      if (tokenId == null || tokenId.length() == 0 || tokenId.trim().length() == 0) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(POST_AUTH));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(IAuthRecordV2.class, errorMessages));
    }
    return Optional.empty();
  }
}
