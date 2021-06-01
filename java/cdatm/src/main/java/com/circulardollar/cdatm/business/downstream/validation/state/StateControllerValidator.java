package com.circulardollar.cdatm.business.downstream.validation.state;

import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.state.IATMStateController;
import com.circulardollar.cdatm.utils.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StateControllerValidator implements IValidator<IATMStateController> {

  @Override
  public Optional<IError> validate(IATMStateController atmStateController) {
    List<String> errorMessages = new ArrayList<>();
    if (atmStateController == null) {
      errorMessages.add(Messages.getIllegalArgumentExceptionForClass(IATMStateController.class));
    }
    if (!errorMessages.isEmpty()) {
      return Optional.of(Error.of(IATMStateController.class, errorMessages));
    }
    return Optional.empty();
  }
}
