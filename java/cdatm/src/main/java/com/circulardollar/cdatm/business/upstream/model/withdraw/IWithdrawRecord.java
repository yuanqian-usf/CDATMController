package com.circulardollar.cdatm.business.upstream.model.withdraw;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;

public interface IWithdrawRecord {

  IAccountRecord getAccount();

  Integer getAmount();

  Long getTimeStamp();
}
