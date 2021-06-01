package com.circulardollar.cdatm.utils.network;

import static com.circulardollar.cdatm.utils.network.InterfaceSerializer.interfaceSerializer;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.auth.Auth;
import com.circulardollar.cdatm.business.downstream.model.auth.IAuth;
import com.circulardollar.cdatm.business.downstream.model.error.Error;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.downstream.response.Response;
import com.circulardollar.cdatm.business.mapper.account.AccountMapper;
import com.circulardollar.cdatm.business.mapper.error.ErrorMapper;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.token.RequestWithToken;
import com.circulardollar.cdatm.business.upstream.request.IRequest;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.APIVersions;
import com.circulardollar.cdatm.constant.DownstreamCommands;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.constant.network.HttpMethod;
import com.circulardollar.cdatm.network.ExceptionHandler;
import com.circulardollar.cdatm.network.ICallable;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.network.NetworkClientV2;
import com.circulardollar.cdatm.validator.upstream.ATMRemoteValidator;
import com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public final class NetworkUtils {
  public static final String LOCAL_HOST = "localhost";
  public static final String LOCAL_HOST_URL = "http://127.0.0.1:8080";

  private static final String BEARER = "Bearer";

  private NetworkUtils() {}

  public static String createBearerTokenHeader(String tokenId) {
    return String.join(" ", BEARER, tokenId);
  }

  public static <BODY, REQ extends IRequest<BODY>, RES, ResImpl extends RES>
      CompletableFuture<RemoteResponse<RES>> getRemoteResponseCompletableFuture(
          IATMRemoteValidator validator,
          HttpClientBuilder httpClientBuilder,
          REQ request,
          HttpMethod method,
          String endpoint,
          Class<ResImpl> responseClass) {
    CompletableFuture<RemoteResponse<RES>> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            new ICallable<REQ, RemoteResponse<RES>>(request) {
              @Override
              public RemoteResponse<RES> call() {
                RemoteResponse.Builder<RES> responseBuilder = RemoteResponse.newBuilder();

                try (CloseableHttpClient closeableHttpClient = httpClientBuilder.build()) {
                  HttpResponse result =
                      closeableHttpClient.execute(getHttpUriRequest(method, endpoint, request));
                  String json = EntityUtils.toString(result.getEntity(), StandardCharsets.UTF_8);
                  handleResponse(
                      new GsonBuilder()
                          .registerTypeAdapter(
                              IAccountRecord.class, interfaceSerializer(AccountRecord.class))
                          .create(),
                      responseClass,
                      responseBuilder,
                      json);
                  validator
                      .validate(responseBuilder.getBody())
                      .ifPresent(
                          iErrorRecord ->
                              completableFuture.complete(
                                  responseBuilder.setError(iErrorRecord).build()));
                } catch (IOException e) {
                  responseBuilder.setError(
                      ErrorRecord.of(
                          NetworkClientV2.class,
                          Arrays.asList(Errors.IO_EXCEPTION.getValue(), e.getMessage())));
                }
                completableFuture.complete(responseBuilder.build());
                return null;
              }
            });
    return completableFuture;
  }

  public static <BODY, REQ extends IRequest<BODY>> HttpUriRequest getHttpUriRequest(
      HttpMethod method, String endpoint, REQ request) throws UnsupportedEncodingException {
    HttpUriRequest httpUriRequest;
    switch (method) {
      case POST:
        String jsonRequest = new Gson().toJson(request.getBody());
        StringEntity params = new StringEntity(jsonRequest);
        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setEntity(params);
        httpUriRequest = httpPost;
        break;
      case GET:
        httpUriRequest = new HttpGet(endpoint);
        break;
      default:
        throw new IllegalArgumentException("Can't support such Http Method!");
    }

    httpUriRequest.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    request
        .withToken()
        .ifPresent(
            requestWithToken ->
                httpUriRequest.setHeader(
                    HttpHeaders.AUTHORIZATION,
                    createBearerTokenHeader(requestWithToken.getTokenId())));
    return httpUriRequest;
  }

  public static <T extends S, S> void handleResponse(
      Gson gson, Class<T> responseClass, RemoteResponse.Builder<S> responseBuilder, String json) {
    Objects.requireNonNull(gson);
    Objects.requireNonNull(responseClass);
    Objects.requireNonNull(responseBuilder);
    Objects.requireNonNull(json);
    try {
      IErrorRecord error = gson.fromJson(json, ErrorRecord.class);
      if (error.getErrorCode() != null) {
        responseBuilder.setError(error);
        return;
      } else if (error.getErrorMessages() != null) {
        responseBuilder.setError(
            ErrorRecord.of(
                NetworkClientV2.class,
                Collections.singletonList(Errors.REMOTE_RESPONSE_EXCEPTION.getValue())));
        return;
      }
      S response = gson.fromJson(json, responseClass);
      responseBuilder.setBody(response);
    } catch (Exception e) {
      responseBuilder.setError(
          ErrorRecord.of(
              NetworkClientV2.class,
              Arrays.asList(Errors.JSON_PARSE_EXCEPTION.getValue(), e.getMessage())));
    }
  }

  public static IResponse<IAuth, IError> getVerifyPinResponseByApiVersion(
      INetworkClientV2 networkClientV2,
      APIVersions apiVersions,
      RemoteRequest<ILoginRecord> request) {
    switch (apiVersions) {
      case V1:
        CompletableFuture<RemoteResponse<IAuthRecord>> authRecordV1Future =
            networkClientV2.verifyPin(request);
        RemoteResponse<IAuthRecord> remoteAccountResponse =
            ExceptionHandler.handleFuture(authRecordV1Future);
        if (remoteAccountResponse.getError() != null) {
          return Response.<IAuth>newBuilder()
              .setError(ErrorMapper.down(remoteAccountResponse.getError()))
              .build();
        }
        List<IAccount> accounts =
            remoteAccountResponse.getBody().getAccounts().stream()
                .map(AccountMapper::down)
                .collect(Collectors.toList());
        String tokenId = remoteAccountResponse.getBody().getTokenId();
        return Response.<IAuth>newBuilder()
            .setBody(
                Auth.newBuilder()
                    .setAccounts(accounts)
                    .setTimeStamp(remoteAccountResponse.getBody().getTimeStamp())
                    .setTokenId(tokenId)
                    .build())
            .build();
      case V2:
        CompletableFuture<RemoteResponse<IAuthRecordV2>> authResponseV2 =
            networkClientV2.verifyPinV2(request);
        RemoteResponse<IAuthRecordV2> iAuthRecordV2RemoteResponse =
            ExceptionHandler.handleFuture(authResponseV2);
        if (iAuthRecordV2RemoteResponse.getError() != null) {
          return Response.<IAuth>newBuilder()
              .setError(ErrorMapper.down(iAuthRecordV2RemoteResponse.getError()))
              .build();
        }
        String tokenIdV2 = iAuthRecordV2RemoteResponse.getBody().getTokenId();
        CompletableFuture<RemoteResponse<IAccountsRecord>> accountV2RemoteResponse =
            networkClientV2.getAccounts(
                RemoteRequest.<IRequestWithToken>newBuilder()
                    .setBody(RequestWithToken.newBuilder().setTokenId(tokenIdV2).build())
                    .build());
        RemoteResponse<IAccountsRecord> remoteAccountResponseV2 =
            ExceptionHandler.handleFuture(accountV2RemoteResponse);
        if (remoteAccountResponseV2.getError() != null) {
          return Response.<IAuth>newBuilder()
              .setError(ErrorMapper.down(remoteAccountResponseV2.getError()))
              .build();
        }
        List<IAccount> accountsV2 =
            remoteAccountResponseV2.getBody().getAccounts().stream()
                .map(
                    iAccountRecord ->
                        Account.newBuilder()
                            .setAccountNumber(iAccountRecord.getAccountNumber())
                            .setBalance(iAccountRecord.getBalance())
                            .build())
                .collect(Collectors.toList());
        return Response.<IAuth>newBuilder()
            .setBody(
                Auth.newBuilder()
                    .setAccounts(accountsV2)
                    .setTokenId(tokenIdV2)
                    .setTimeStamp(remoteAccountResponseV2.getBody().getTimeStamp())
                    .build())
            .build();
      default:
        return Response.<IAuth>newBuilder()
            .setError(
                Error.of(
                    NetworkUtils.class,
                    Arrays.asList(
                        APIVersions.class.getName(), Errors.UNSUPPORTED_API_VERSION.getValue())))
            .build();
    }
  }

  public static INetworkClientV2.IBuilder buildNetworkClientBuilder(
      String[] args, Set<DownstreamCommands> supportedCommands) throws IllegalArgumentException {
    INetworkClientV2.IBuilder networkClientBuilder;
    if (args == null) throw new IllegalArgumentException(Errors.INVALID_COMMAND.getValue());
    if (args.length > 1) {
      if (!supportedCommands.contains(DownstreamCommands.ofValue(args[0]))) {
        throw new IllegalArgumentException(Errors.UNSUPPORTED_COMMAND.getValue());
      }
      if (DownstreamCommands.URL.getValue().equalsIgnoreCase(args[0])) {
        if (args[1] == null || args[1].length() == 0 || args[1].trim().length() == 0) {
          throw new IllegalArgumentException(UserInterface.INVALID_COMMAND.getValue());
        } else if (LOCAL_HOST.equalsIgnoreCase(args[1])) {
          System.out.println(UserInterface.TESTING_WITH_LOCALHOST.getValue());
          networkClientBuilder =
              NetworkClientV2.newBuilder()
                  .setUrl(LOCAL_HOST_URL)
                  .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build());
        } else {
          System.out.printf(UserInterface.CONNECT_TO_ENDPOINT.getValue(), args[1]);
          networkClientBuilder =
              NetworkClientV2.newBuilder()
                  .setUrl(args[1])
                  .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build());
        }
      } else {
        throw new IllegalArgumentException(UserInterface.INVALID_COMMAND.getValue());
      }
    } else {
      throw new IllegalArgumentException(Errors.INVALID_COMMAND.getValue());
    }
    return networkClientBuilder;
  }
}
