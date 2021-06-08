package com.circulardollar.cdatm.business.upstream.model.accounts;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import java.util.List;

public interface IAccountsRecord {
  List<IAccountRecord> getAccounts();

  Long getTimeStamp();
}
