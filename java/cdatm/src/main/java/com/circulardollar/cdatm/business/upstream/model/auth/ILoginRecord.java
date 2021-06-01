package com.circulardollar.cdatm.business.upstream.model.auth;

import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;

public interface ILoginRecord {
  ICardRecord getCard();

  IPinRecord getPin();
}
