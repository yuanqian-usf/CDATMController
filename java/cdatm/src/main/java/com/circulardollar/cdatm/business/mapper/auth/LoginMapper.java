package com.circulardollar.cdatm.business.mapper.auth;

import com.circulardollar.cdatm.business.downstream.model.auth.ILogin;
import com.circulardollar.cdatm.business.downstream.model.auth.Login;
import com.circulardollar.cdatm.business.mapper.card.CardMapper;
import com.circulardollar.cdatm.business.mapper.pin.PinMapper;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;

public final class LoginMapper {
  private LoginMapper() {}
  public static ILogin down(ILoginRecord record) {
    if(record == null) {
      return null;
    }
    return Login.newBuilder()
        .setCard(CardMapper.down(record.getCard()))
        .setPin(PinMapper.down(record.getPin()))
        .build();
  }
}
