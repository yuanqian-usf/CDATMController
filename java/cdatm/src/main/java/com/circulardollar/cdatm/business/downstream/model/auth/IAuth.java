package com.circulardollar.cdatm.business.downstream.model.auth;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;

import java.util.List;

public interface IAuth {
    String getTokenId();

    List<IAccount> getAccounts();

    Long getTimeStamp();
}
