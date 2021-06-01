package com.circulardollar.cdatm.business.upstream.model.auth;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import java.util.List;

public interface IAuthRecord {

  String getTokenId();

  List<? extends IAccountRecord> getAccounts();

  Long getTimeStamp();
}
