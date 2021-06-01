package com.circulardollar.cdatm.business.downstream.model.deposit;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;

public interface IDeposit {
  IAccount getAccount();

  Integer getAmount();

  Long getTimeStamp();
}
