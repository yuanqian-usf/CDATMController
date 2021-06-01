package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;

public interface IDepositRecord {
  Integer getAmount();

  IAccountRecord getAccount();

  Long getTimeStamp();
}
