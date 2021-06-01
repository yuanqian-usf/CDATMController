package com.circulardollar.cdatm.validator;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.session.IATMSessionController;
import com.circulardollar.cdatm.state.IATMStateController;

import java.util.Optional;

public interface IATMValidator {

  Optional<IError> validateConfigurations(IATMConfigurations atmConfigurations);

  Optional<IError> validateStateController(IATMStateController atmStateController);

  Optional<IError> validateSessionController(IATMSessionController atmSessionController);

  Optional<IError> validateCard(ICard card);

  Optional<IError> validatePin(IPin pin);

  Optional<IError> validateAccount(IAccount account);

  Optional<IError> validateDeposit(IDeposit deposit);

  Optional<IError> validateWithdraw(IWithdraw withdraw);

  IATMConfigurations getATMConfigurations();
}
