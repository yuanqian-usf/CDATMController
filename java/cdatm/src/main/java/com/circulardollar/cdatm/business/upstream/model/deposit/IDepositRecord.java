package com.circulardollar.cdatm.business.upstream.model.deposit;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;

public interface IDepositRecord {
  Integer getAmount();

  IAccountRecord getAccount();

  Long getTimeStamp();

  interface IBuilder<T extends IAccountRecord> {
    IBuilder setAmount(Integer amount);
    IBuilder setAccount(T accountRecord);
    IBuilder setTimeStamp(Long timeStamp);
    IDepositRecord build();
  }
}
