package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface INetworkClientV2 extends INetworkClient {
  CompletableFuture<RemoteResponse<IAuthRecordV2>> verifyPinV2(RemoteRequest<ILoginRecord> login);

  CompletableFuture<RemoteResponse<IAccountsRecord>> getAccounts(RemoteRequest<IRequestWithToken> token);

  abstract class IBuilder {
    public IBuilder setAccountsRecordTable(Map<ILoginRecord, IAccountsRecord> accountsRecordTable) {
      return null;
    }
    public IBuilder setIATMRemoteValidator(IATMRemoteValidator validator) {
      return null;
    }

    public IBuilder setUrl(String url) {
      return null;
    }

    public abstract INetworkClientV2 build();
  }
}
