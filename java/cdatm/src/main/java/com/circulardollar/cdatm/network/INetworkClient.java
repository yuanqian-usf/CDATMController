package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import java.util.concurrent.CompletableFuture;

public interface INetworkClient {
  CompletableFuture<RemoteResponse<IAuthRecord>> verifyPin(RemoteRequest<ILoginRecord> login);

  CompletableFuture<RemoteResponse<IDepositRecord>> deposit(
      RemoteRequest<IDepositRecordRequest> deposit);

  CompletableFuture<RemoteResponse<IWithdrawRecord>> withdraw(
      RemoteRequest<IWithdrawRecordRequest> withdraw);

  CompletableFuture<RemoteResponse<ILogoutRecord>> logout(
      RemoteRequest<ILogoutRecordRequest> logout);
}
