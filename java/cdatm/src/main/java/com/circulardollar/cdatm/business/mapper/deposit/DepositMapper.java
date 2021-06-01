package com.circulardollar.cdatm.business.mapper.deposit;

import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.mapper.account.AccountMapper;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;

public class DepositMapper {
  public static IDeposit down(IDepositRecord record) {
    return Deposit.newBuilder()
        .setAccount(AccountMapper.down(record.getAccount()))
        .setAmount(record.getAmount())
        .setTimeStamp(record.getTimeStamp())
        .build();
  }

  public static IDepositRecord up(IDeposit deposit) {
    return DepositRecord.newBuilder()
        .setAccount(AccountMapper.up(deposit.getAccount()))
        .setAmount(deposit.getAmount())
        .setTimeStamp(deposit.getTimeStamp())
        .build();
  }

  public static IDepositRecordRequest parse(IDepositRecord record, String tokenId) {
    return DepositRecordRequest.newBuilder()
        .setAccount(AccountRecord.newBuilder().setAccountNumber(record.getAccount().getAccountNumber()).setBalance(record.getAccount().getBalance()).build())
        .setAmount(record.getAmount())
        .setTimeStamp(record.getTimeStamp())
        .setTokenId(tokenId)
        .build();
  }
}
