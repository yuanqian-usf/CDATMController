package com.circulardollar.cdatm.network;

import static com.circulardollar.cdatm.utils.network.NetworkUtils.getRemoteResponseCompletableFuture;

import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.auth.LogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.network.HttpMethod;
import com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class NetworkClientV2 implements INetworkClientV2 {

  private static final String VERSION_1 = "/v1";
  private static final String VERSION_2 = "/v2";
  private static final String URL_VERIFY_PIN = "/verify_pin";
  private static final String URL_ACCOUNTS = "/accounts";
  private static final String URL_DEPOSIT = "/deposit";
  private static final String URL_WITHDRAW = "/withdraw";
  private static final String URL_LOGOUT = "/logout";

  private final String url;
  private final IATMRemoteValidator atmRemoteValidator;
  private final HttpClientBuilder httpClientBuilder;

  NetworkClientV2(
      String url,
      IATMRemoteValidator iatmRemoteValidator,
      HttpClientBuilder httpClientBuilder) {
    Objects.requireNonNull(url);
    Objects.requireNonNull(iatmRemoteValidator);
    this.url = url;
    this.atmRemoteValidator = iatmRemoteValidator;
    this.httpClientBuilder =
        httpClientBuilder == null ? HttpClientBuilder.create() : httpClientBuilder;
  }

  public static IBuilder newBuilder() {
    return new Builder();
  }

  @Override
  public CompletableFuture<RemoteResponse<IAuthRecord>> verifyPin(
      RemoteRequest<ILoginRecord> request) {
    Objects.requireNonNull(request);
    RemoteResponse.Builder<IAuthRecord> builder = RemoteResponse.newBuilder();
    Optional<IErrorRecord> validationError =
        atmRemoteValidator.validateLoginRecord(request.getBody());
    return validationError
        .map(
            errorRecord -> CompletableFuture.completedFuture(builder.setError(errorRecord).build()))
        .orElseGet(
            () ->
                getRemoteResponseCompletableFuture(
                    atmRemoteValidator,
                    httpClientBuilder,
                    request,
                    HttpMethod.POST,
                    url + VERSION_1 + URL_VERIFY_PIN,
                    AuthRecord.class));
  }

  @Override
  public CompletableFuture<RemoteResponse<IAuthRecordV2>> verifyPinV2(
      RemoteRequest<ILoginRecord> request) {
    Objects.requireNonNull(request);
    RemoteResponse.Builder<IAuthRecordV2> builder = RemoteResponse.newBuilder();
    Optional<IErrorRecord> validationError =
        atmRemoteValidator.validateLoginRecord(request.getBody());
    return validationError
        .map(
            errorRecord -> CompletableFuture.completedFuture(builder.setError(errorRecord).build()))
        .orElseGet(
            () ->
                getRemoteResponseCompletableFuture(
                    atmRemoteValidator,
                    httpClientBuilder,
                    request,
                    HttpMethod.POST,
                    url + VERSION_2 + URL_VERIFY_PIN,
                    AuthRecordV2.class));
  }

  @Override
  public CompletableFuture<RemoteResponse<IAccountsRecord>> getAccounts(
      RemoteRequest<IRequestWithToken> request) {
    Objects.requireNonNull(request);
    RemoteResponse.Builder<IAccountsRecord> builder = RemoteResponse.newBuilder();
    Optional<IErrorRecord> validationError =
        atmRemoteValidator.validateRequestWithToken(request.getBody());
    return validationError
        .map(
            errorRecord -> CompletableFuture.completedFuture(builder.setError(errorRecord).build()))
        .orElseGet(
            () ->
                getRemoteResponseCompletableFuture(
                    atmRemoteValidator,
                    httpClientBuilder,
                    request,
                    HttpMethod.GET,
                    url + VERSION_2 + URL_ACCOUNTS,
                    AccountsRecord.class));
  }

  @Override
  public CompletableFuture<RemoteResponse<IDepositRecord>> deposit(
      RemoteRequest<IDepositRecordRequest> request) {
    Objects.requireNonNull(request);
    RemoteResponse.Builder<IDepositRecord> builder = RemoteResponse.newBuilder();
    Optional<IErrorRecord> validationError1 =
        atmRemoteValidator.validateRequestWithToken(request.getBody());
    if (validationError1.isPresent())
      return CompletableFuture.completedFuture(builder.setError(validationError1.get()).build());
    Optional<IErrorRecord> validationError2 =
        atmRemoteValidator.validateDepositRecord(request.getBody());
    return validationError2
        .map(
            errorRecord -> CompletableFuture.completedFuture(builder.setError(errorRecord).build()))
        .orElseGet(
            () ->
                getRemoteResponseCompletableFuture(
                    atmRemoteValidator,
                    httpClientBuilder,
                    request,
                    HttpMethod.POST,
                    url + VERSION_1 + URL_DEPOSIT,
                    DepositRecord.class));
  }

  @Override
  public CompletableFuture<RemoteResponse<IWithdrawRecord>> withdraw(
      RemoteRequest<IWithdrawRecordRequest> request) {
    Objects.requireNonNull(request);
    RemoteResponse.Builder<IWithdrawRecord> builder = RemoteResponse.newBuilder();
    Optional<IErrorRecord> validationError1 =
        atmRemoteValidator.validateRequestWithToken(request.getBody());
    if (validationError1.isPresent())
      return CompletableFuture.completedFuture(builder.setError(validationError1.get()).build());
    Optional<IErrorRecord> validationError2 =
        atmRemoteValidator.validateWithdrawRecord(request.getBody());
    return validationError2
        .map(
            errorRecord -> CompletableFuture.completedFuture(builder.setError(errorRecord).build()))
        .orElseGet(
            () ->
                getRemoteResponseCompletableFuture(
                    atmRemoteValidator,
                    httpClientBuilder,
                    request,
                    HttpMethod.POST,
                    url + VERSION_1 + URL_WITHDRAW,
                    WithdrawRecord.class));
  }

  @Override
  public CompletableFuture<RemoteResponse<ILogoutRecord>> logout(
      RemoteRequest<ILogoutRecordRequest> request) {
    Objects.requireNonNull(request);
    RemoteResponse.Builder<ILogoutRecord> builder = RemoteResponse.newBuilder();
    Optional<IErrorRecord> validationError1 =
        atmRemoteValidator.validateRequestWithToken(request.getBody());
    if (validationError1.isPresent())
      return CompletableFuture.completedFuture(builder.setError(validationError1.get()).build());
    Optional<IErrorRecord> validationError2 =
        atmRemoteValidator.validateLogoutRecord(request.getBody());
    return validationError2
        .map(
            errorRecord -> CompletableFuture.completedFuture(builder.setError(errorRecord).build()))
        .orElseGet(
            () ->
                getRemoteResponseCompletableFuture(
                    atmRemoteValidator,
                    httpClientBuilder,
                    request,
                    HttpMethod.POST,
                    url + VERSION_1 + URL_LOGOUT,
                    LogoutRecord.class));
  }

  public static class Builder extends IBuilder {
    private String url;
    private IATMRemoteValidator atmRemoteValidator;
    private HttpClientBuilder httpClientBuilder;

    @Override
    public IBuilder setUrl(String url) {
      Objects.requireNonNull(url);
      this.url = url;
      return this;
    }

    @Override
    public IBuilder setIATMRemoteValidator(IATMRemoteValidator validator) {
      Objects.requireNonNull(validator);
      this.atmRemoteValidator = validator;
      return this;
    }

    public INetworkClientV2 build() {
      return new NetworkClientV2(url, atmRemoteValidator, httpClientBuilder);
    }
  }
}
