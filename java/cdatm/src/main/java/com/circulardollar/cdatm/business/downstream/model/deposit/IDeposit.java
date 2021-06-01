package com.circulardollar.cdatm.business.downstream.model.deposit;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;

public interface IDeposit {
  Integer getAmount();

  IAccount getAccount();

  Long getTimeStamp();
}
