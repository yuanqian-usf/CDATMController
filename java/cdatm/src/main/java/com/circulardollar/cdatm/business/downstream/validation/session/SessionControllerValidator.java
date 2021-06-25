package com.circulardollar.cdatm.business.downstream.validation.session;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.session.IATMSessionController;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionControllerValidator implements IValidator<IATMSessionController> {

  @Override
  public Optional<IError> validate(IATMSessionController sessionController) {
    List<String> errorMessages = new ArrayList<>();
    if (sessionController == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IATMSessionController.class));
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IATMSessionController.class, errorMessages));
    }
    return Optional.empty();
  }
}
