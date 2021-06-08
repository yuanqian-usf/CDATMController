package com.circulardollar.cdatm.business.upstream.validation.token;

import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class RequestWithTokenRemoteValidator implements IRemoteValidator<IRequestWithToken> {

  private static final String POST_TOKEN = "POST_TOKEN";

  @Override
  public Optional<IErrorRecord> validate(IRequestWithToken requestWithToken) {
    List<String> errorMessages = new ArrayList<>();
    if (requestWithToken == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IRequestWithToken.class));
    } else {
      if (StringUtils.isBlank(requestWithToken.getTokenId())) {
        errorMessages.add(Messages.getNullPointerExceptionForMethod(POST_TOKEN));
      }
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(ErrorRecord.of(IRequestWithToken.class, errorMessages));
    }
    return Optional.empty();
  }
}
