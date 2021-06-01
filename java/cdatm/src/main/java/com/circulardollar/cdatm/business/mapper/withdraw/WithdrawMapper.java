package com.circulardollar.cdatm.business.mapper.withdraw;

import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.model.withdraw.Withdraw;
import com.circulardollar.cdatm.business.mapper.account.AccountMapper;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecordRequest;

public class WithdrawMapper {

  public static IWithdraw down(IWithdrawRecord record) {
    if (record == null) return null;
    return Withdraw.newBuilder()
        .setAccount(AccountMapper.down(record.getAccount()))
        .setAmount(record.getAmount())
        .setTimeStamp(record.getTimeStamp())
        .build();
  }

  public static IWithdrawRecord up(IWithdraw withdraw) {
    if (withdraw == null) return null;
    return WithdrawRecord.newBuilder()
        .setAccount(AccountMapper.up(withdraw.getAccount()))
        .setAmount(withdraw.getAmount())
        .setTimeStamp(withdraw.getTimeStamp())
        .build();
  }

  public static IWithdrawRecordRequest parse(IWithdrawRecord record, String tokenId) {
    if (record == null) return null;
    if (tokenId == null) return null;
    return WithdrawRecordRequest.newBuilder()
        .setAccount(
            AccountRecord.newBuilder().setAccountNumber(record.getAccount().getAccountNumber()).setBalance(record.getAccount().getBalance()).build())
        .setAmount(record.getAmount())
        .setTimeStamp(record.getTimeStamp())
        .setTokenId(tokenId)
        .build();
  }
}
