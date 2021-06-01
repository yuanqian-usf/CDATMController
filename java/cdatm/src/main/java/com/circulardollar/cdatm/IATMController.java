package com.circulardollar.cdatm;

import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.session.IATMSessionController;
import com.circulardollar.cdatm.state.IATMStateController;
import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.validator.IATMValidator;
import java.util.List;
import java.util.Set;

public interface IATMController {

  IResponse<Boolean, IError> insertCard(ICard card);

  IResponse<List<String>, IError> verifyPin(IPin card); // AccountNumbers

  IResponse<Boolean, IError> selectAccount(String accountNumber);

  IResponse<Integer, IError> checkBalance();

  IResponse<IDeposit, IError> deposit(Integer amount);

  IResponse<IWithdraw, IError> withdraw(Integer amount);

  IResponse<Boolean, IError> ejectCard();

  Set<ATMStates> availableStates();

  interface IBuilder {
    IATMController.IBuilder setAtmStateController(IATMStateController atmStateController);

    IATMController.IBuilder setAtmSessionController(IATMSessionController atmSessionController);

    IATMController.IBuilder setNetworkClientV2(INetworkClientV2 networkClientV2);

    IATMController.IBuilder setAtmValidator(IATMValidator atmValidator);

    IATMController build();
  }
}
