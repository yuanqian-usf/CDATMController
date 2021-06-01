package com.circulardollar.cdatm.business.downstream.model.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;

public interface IWithdraw {
  IAccount getAccount();

  Integer getAmount();

  Long getTimeStamp();
}
