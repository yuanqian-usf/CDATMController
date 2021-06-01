package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.request.IRemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface INetworkClientV2 extends INetworkClient {
  CompletableFuture<RemoteResponse<IAuthRecordV2>> verifyPinV2(IRemoteRequest<ILoginRecord> login);

  CompletableFuture<RemoteResponse<IAccountsRecord>> getAccounts(IRemoteRequest<String> token);

  abstract class IBuilder {
    public IBuilder setAccountsRecordTable(Map<ILoginRecord, IAccountsRecord> accountsRecordTable) {return null;}

      public IBuilder setUrl(String url) {return null; }

    public abstract INetworkClientV2 build();
  }
}
