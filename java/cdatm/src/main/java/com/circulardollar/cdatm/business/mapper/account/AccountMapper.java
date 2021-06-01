package com.circulardollar.cdatm.business.mapper.account;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountMapper {

  public static IAccount down(IAccountRecord account) {
    if (account == null) return null;
    return Account.newBuilder()
        .setAccountNumber(account.getAccountNumber())
        .setBalance(account.getBalance())
        .build();
  }

  public static AccountRecord up(IAccount account) {
    if (account == null) return null;
    return AccountRecord.newBuilder()
        .setAccountNumber(account.getAccountNumber())
        .setBalance(account.getBalance())
        .build();
  }

  public static Map<String, IAccount> mapAccounts(List<IAccount> accounts) {
    if (accounts == null) return null;
    return accounts.stream()
        .collect(Collectors.toMap(IAccount::getAccountNumber, account -> account));
  }
}
