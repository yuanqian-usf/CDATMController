package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.constant.DownstreamAPIs;

public final class StateMapper {
  private StateMapper() {}

  public static DownstreamAPIs map(ATMStates state) {
    if (state == null) {
      return DownstreamAPIs.UNSPECIFIED;
    }
    switch (state) {
      case ACTIVE:
        return DownstreamAPIs.EJECT_CARD;
      case INSERT_CARD:
        return DownstreamAPIs.INSERT_CARD;
      case VERIFY_PIN:
        return DownstreamAPIs.VERIFY_PIN;
      case SELECT_ACCOUNT:
        return DownstreamAPIs.SELECT_ACCOUNT;
      case CHECK_BALANCE:
        return DownstreamAPIs.CHECK_BALANCE;
      case DEPOSIT:
        return DownstreamAPIs.DEPOSIT;
      case WITHDRAW:
        return DownstreamAPIs.WITHDRAW;
    }
    return DownstreamAPIs.UNSPECIFIED;
  }
}
