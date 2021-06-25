package com.circulardollar.cdatm.business.downstream.model.auth;

import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;

public interface ILogin {
  ICard getCard();

  IPin getPin();
}
