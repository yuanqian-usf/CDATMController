package com.circulardollar.cdatm.utils.network;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomSet;
import static com.circulardollar.cdatm.TestBase.randomString;
import static com.circulardollar.cdatm.TestBase.validEmptyJsonString;
import static com.circulardollar.cdatm.utils.network.InterfaceSerializer.interfaceSerializer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.request.IRequest;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.APIVersions;
import com.circulardollar.cdatm.constant.DownstreamCommands;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.constant.network.HttpMethod;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator;
import com.google.gson.GsonBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

public class NetworkUtilsTest {

  @Test
  public void handleResponse_error_nonJsonObject() {
    String json = "[]";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNotNull(responseBuilder.build().getError());
  }

  /**
   * handleResponse doesn't validate business related logic for instance passing wrong body instead
   * of expected payload thus reference to {@link
   * com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator} for more business related
   * validations
   */
  @Test
  public void handleResponse_error_missing_both_error_code_messages() {
    String json = "{}";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNull(responseBuilder.build().getError());
    assertNotNull(responseBuilder.build().getBody());
  }

  @Test
  public void handleResponse_error_missing_errorCode() {
    String json =
        "{\n" + "    \"errorMessages\": [\n" + "        \"invalid amount\"\n" + "    ]\n" + "}";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNotNull(responseBuilder.build().getError().getErrorMessages());
    assertTrue(responseBuilder.build().getError().getErrorMessages().size() > 0);
    assertEquals(
        Errors.REMOTE_RESPONSE_EXCEPTION.getValue(),
        responseBuilder.build().getError().getErrorMessages().get(0));
  }

  @Test
  public void handleResponse_error_invalid() {
    String json =
        "{\n"
            + "    \"errorCod\": 404,\n"
            + "    \"errorMessages\": [\n"
            + "        \"invalid amount\"\n"
            + "    ]\n"
            + "}";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNotNull(responseBuilder.build().getError().getErrorMessages());
    assertTrue(responseBuilder.build().getError().getErrorMessages().size() > 0);
    assertEquals(
        Errors.REMOTE_RESPONSE_EXCEPTION.getValue(),
        responseBuilder.build().getError().getErrorMessages().get(0));
  }

  @Test
  public void handleResponse_error() {
    String json =
        "{\n"
            + "    \"errorCode\": 404,\n"
            + "    \"errorMessages\": [\n"
            + "        \"invalid amount\"\n"
            + "    ]\n"
            + "}";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNotNull(responseBuilder.build().getError().getErrorCode());
  }

  /**
   * handleResponse doesn't validate business related logic for instance passing wrong body instead
   * of expected payload thus reference to {@link
   * com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator} for more business related
   * validations
   */
  @Test
  public void handleResponse_wrong_body_incapable() {
    String json =
        "{\n"
            + "    \"accounts\": [\n"
            + "        {\n"
            + "            \"accountNumber\": \"ef8bf73c-5e41-400d-88d2-3d1207edd591\",\n"
            + "            \"balance\": 772927534\n"
            + "        }\n"
            + "    ],\n"
            + "    \"timeStamp\": 1623003574705\n"
            + "}";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNotNull(responseBuilder.build().getBody());
    assertNull(responseBuilder.build().getError());
  }

  @Test
  public void handleResponse_body() {
    String json =
        "{\n"
            + "    \"account\": {\n"
            + "        \"accountNumber\": \"e6b7c318-173b-4b8c-a1e2-efcd90767dfb\",\n"
            + "        \"balance\": -2147483647\n"
            + "    },\n"
            + "    \"amount\": 1,\n"
            + "    \"timeStamp\": 1622352444705\n"
            + "}";
    RemoteResponse.Builder<IDepositRecord> responseBuilder = RemoteResponse.newBuilder();
    NetworkUtils.handleResponse(
        new GsonBuilder()
            .registerTypeAdapter(IAccountRecord.class, interfaceSerializer(AccountRecord.class))
            .create(),
        DepositRecord.class,
        responseBuilder,
        json);
    assertNotNull(responseBuilder.build().getBody().getAccount());
    assertEquals(
        "e6b7c318-173b-4b8c-a1e2-efcd90767dfb",
        responseBuilder.build().getBody().getAccount().getAccountNumber());
  }

  @Test
  public void getVerifyPinResponseByApiVersion_V1_error() {
    RemoteRequest<ILoginRecord> request = mock(RemoteRequest.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    when(networkClientV2.verifyPin(request))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAuthRecord>newBuilder()
                    .setError(
                        ErrorRecord.newBuilder()
                            .setErrorCode(randomInt())
                            .setErrorMessages(randomList())
                            .build())
                    .build()));
    APIVersions apiVersions = APIVersions.V1;
    NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, request);
    verify(networkClientV2, times(1)).verifyPin(request);
  }

  @Test
  public void getVerifyPinResponseByApiVersion_V1_body() {
    RemoteRequest<ILoginRecord> request = mock(RemoteRequest.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    when(networkClientV2.verifyPin(request))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAuthRecord>newBuilder()
                    .setBody(
                        AuthRecord.newBuilder()
                            .setTokenId(randomString())
                            .setAccounts(randomList())
                            .setTimeStamp(randomLong())
                            .build())
                    .build()));
    APIVersions apiVersions = APIVersions.V1;
    NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, request);
    verify(networkClientV2, times(1)).verifyPin(request);
  }

  @Test
  public void getVerifyPinResponseByApiVersion_V2_error() {
    RemoteRequest<ILoginRecord> request = mock(RemoteRequest.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    when(networkClientV2.verifyPinV2(request))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAuthRecordV2>newBuilder()
                    .setError(
                        ErrorRecord.newBuilder()
                            .setErrorCode(randomInt())
                            .setErrorMessages(randomList())
                            .build())
                    .build()));
    APIVersions apiVersions = APIVersions.V2;
    NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, request);
    verify(networkClientV2, times(1)).verifyPinV2(request);
  }

  @Test
  public void getVerifyPinResponseByApiVersion_V2_body_error() {
    String tokenId = randomString();
    RemoteRequest<ILoginRecord> verifyPinV2Request = mock(RemoteRequest.class);
    RemoteRequest<String> getAccountsRequest = mock(RemoteRequest.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    when(networkClientV2.verifyPinV2(verifyPinV2Request))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAuthRecordV2>newBuilder()
                    .setBody(
                        AuthRecordV2.newBuilder()
                            .setTokenId(tokenId)
                            .setTimeStamp(randomLong())
                            .build())
                    .build()));
    when(networkClientV2.getAccounts(any()))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAccountsRecord>newBuilder()
                    .setError(
                        ErrorRecord.newBuilder()
                            .setErrorCode(randomInt())
                            .setErrorMessages(randomList())
                            .build())
                    .build()));
    APIVersions apiVersions = APIVersions.V2;
    NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, verifyPinV2Request);
    verify(networkClientV2, times(1)).verifyPinV2(verifyPinV2Request);
    verify(networkClientV2, times(1)).getAccounts(any());
  }

  @Test
  public void getVerifyPinResponseByApiVersion_V2_body_body() {
    String tokenId = randomString();
    RemoteRequest<ILoginRecord> verifyPinV2Request = mock(RemoteRequest.class);
    RemoteRequest<String> getAccountsRequest = mock(RemoteRequest.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    when(networkClientV2.verifyPinV2(verifyPinV2Request))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAuthRecordV2>newBuilder()
                    .setBody(
                        AuthRecordV2.newBuilder()
                            .setTokenId(tokenId)
                            .setTimeStamp(randomLong())
                            .build())
                    .build()));
    when(networkClientV2.getAccounts(any()))
        .thenReturn(
            CompletableFuture.completedFuture(
                RemoteResponse.<IAccountsRecord>newBuilder()
                    .setBody(
                        AccountsRecord.newBuilder()
                            .setAccounts(
                                Collections.singletonList(
                                    AccountRecord.newBuilder()
                                        .setBalance(randomInt())
                                        .setAccountNumber(randomString())
                                        .build()))
                            .setTimeStamp(randomLong())
                            .build())
                    .build()));
    APIVersions apiVersions = APIVersions.V2;
    NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, verifyPinV2Request);
    verify(networkClientV2, times(1)).verifyPinV2(verifyPinV2Request);
    verify(networkClientV2, times(1)).getAccounts(any());
  }

  @Test
  public void getVerifyPinResponseByApiVersion_UNSPECIFIED() {
    RemoteRequest<ILoginRecord> request = mock(RemoteRequest.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    APIVersions apiVersions = APIVersions.UNSPECIFIED;
    NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, request);
    verify(networkClientV2, never()).verifyPin(request);
    verify(networkClientV2, never()).verifyPinV2(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_01() {
    NetworkUtils.buildNetworkClientBuilder(null, randomSet());
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_02() {
    NetworkUtils.buildNetworkClientBuilder(new String[] {"1"}, randomSet());
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_03() {
    NetworkUtils.buildNetworkClientBuilder(new String[] {"1", "2"}, randomSet());
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_04() {
    NetworkUtils.buildNetworkClientBuilder(
        new String[] {DownstreamCommands.UNSPECIFIED.getValue(), "2"},
        new HashSet<>(Arrays.asList(DownstreamCommands.URL, DownstreamCommands.UNSPECIFIED)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_05() {
    NetworkUtils.buildNetworkClientBuilder(
        new String[] {DownstreamCommands.URL.getValue(), null},
        new HashSet<>(Collections.singletonList(DownstreamCommands.URL)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_06() {
    NetworkUtils.buildNetworkClientBuilder(
        new String[] {DownstreamCommands.URL.getValue(), ""},
        new HashSet<>(Collections.singletonList(DownstreamCommands.URL)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildNetworkClientBuilder_not_null_whenExceptionThrown_thenExpectationSatisfied_07() {
    NetworkUtils.buildNetworkClientBuilder(
        new String[] {DownstreamCommands.URL.getValue(), " "},
        new HashSet<>(Collections.singletonList(DownstreamCommands.URL)));
  }

  @Test
  public void buildNetworkClientBuilder_localhost() {
    assertNotNull(
        NetworkUtils.buildNetworkClientBuilder(
            new String[] {DownstreamCommands.URL.getValue(), NetworkUtils.LOCAL_HOST},
            new HashSet<>(Collections.singletonList(DownstreamCommands.URL))));
  }

  @Test
  public void buildNetworkClientBuilder_realRemoteHost() {
    assertNotNull(
        NetworkUtils.buildNetworkClientBuilder(
            new String[] {DownstreamCommands.URL.getValue(), "realRemoteHost"},
            new HashSet<>(Collections.singletonList(DownstreamCommands.URL))));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getHttpUriRequest_Unspecified() throws UnsupportedEncodingException {
    NetworkUtils.getHttpUriRequest(HttpMethod.UNSPECIFIED, randomString(), new IRequest<Object>() {
      @Override
      public Optional<IRequestWithToken> withToken() {
        return Optional.empty();
      }

      @Override
      public Object getBody() {
        return null;
      }
    });
  }

  @Test
  public void getHttpUriRequest_GET() throws UnsupportedEncodingException {
    assertNotNull(NetworkUtils.getHttpUriRequest(HttpMethod.GET, randomString(), new IRequest<Object>() {
      @Override
      public Optional<IRequestWithToken> withToken() {
        return Optional.empty();
      }

      @Override
      public Object getBody() {
        return null;
      }
    }));
  }

  @Test
  public void getHttpUriRequest_POST() throws UnsupportedEncodingException {
    assertNotNull(NetworkUtils.getHttpUriRequest(HttpMethod.POST, randomString(), new IRequest<Object>() {
      @Override
      public Optional<IRequestWithToken> withToken() {
        return Optional.empty();
      }

      @Override
      public Object getBody() {
        return null;
      }
    }));
  }

  @Test
  public void getRemoteResponseCompletableFuture()
      throws ExecutionException, InterruptedException, IOException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
    CloseableHttpClient closeableHttpClient = mock(CloseableHttpClient.class);
    HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);
    HttpEntity httpEntity = mock(HttpEntity.class);
    when(httpClientBuilder.build()).thenReturn(closeableHttpClient);
    when(closeableHttpClient.execute(any())).thenReturn(httpResponse);
    when(httpResponse.getEntity()).thenReturn(httpEntity);
    when(httpEntity.getContent())
        .thenReturn(new ByteArrayInputStream(validEmptyJsonString().getBytes()));
    when(atmRemoteValidator.validate(any())).thenReturn(Optional.empty());
    RemoteRequest<IDepositRecordRequest> request = RemoteRequest.<IDepositRecordRequest>newBuilder().setBody(DepositRecordRequest.newBuilder().setAccount(AccountRecord.newBuilder().setBalance(randomInt()).setAccountNumber(randomString()).build()).setAmount(randomInt()).setTokenId(randomString()).setTimeStamp(randomLong()).build()).build();
    when(atmRemoteValidator.validate(any())).thenReturn(Optional.of(ErrorRecord.newBuilder()
        .setErrorCode(randomInt())
        .setErrorMessages(randomList())
        .build()));

    CompletableFuture<RemoteResponse<DepositRecordRequest>> remoteResponseCompletableFuture = NetworkUtils
        .getRemoteResponseCompletableFuture(
            atmRemoteValidator,
            httpClientBuilder,
            request,
            HttpMethod.POST,
            randomString(),
            DepositRecordRequest.class);
    assertNotNull(remoteResponseCompletableFuture.get().getError());
  }

  @Test
  public void getRemoteResponseCompletableFuture_throw_exception()
      throws ExecutionException, InterruptedException, IOException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
    HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);
    CloseableHttpClient closeableHttpClient = mock(CloseableHttpClient.class);
    HttpEntity httpEntity = mock(HttpEntity.class);
    when(httpClientBuilder.build()).thenReturn(closeableHttpClient);
    when(closeableHttpClient.execute(any())).thenThrow(new IOException());
    when(httpResponse.getEntity()).thenReturn(httpEntity);
    when(httpEntity.getContent())
        .thenReturn(new ByteArrayInputStream(validEmptyJsonString().getBytes()));
    when(atmRemoteValidator.validate(any())).thenReturn(Optional.empty());
    RemoteRequest<IDepositRecordRequest> request = RemoteRequest.<IDepositRecordRequest>newBuilder().setBody(DepositRecordRequest.newBuilder().setAccount(AccountRecord.newBuilder().setBalance(randomInt()).setAccountNumber(randomString()).build()).setAmount(randomInt()).setTokenId(randomString()).setTimeStamp(randomLong()).build()).build();
    when(atmRemoteValidator.validate(any())).thenReturn(Optional.of(ErrorRecord.newBuilder()
        .setErrorCode(randomInt())
        .setErrorMessages(randomList())
        .build()));

    CompletableFuture<RemoteResponse<DepositRecordRequest>> remoteResponseCompletableFuture = NetworkUtils
        .getRemoteResponseCompletableFuture(
            atmRemoteValidator,
            httpClientBuilder,
            request,
            HttpMethod.POST,
            randomString(),
            DepositRecordRequest.class);
    assertNotNull(remoteResponseCompletableFuture.get().getError());
  }
}
