package com.circulardollar.cdatm.network;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static com.circulardollar.cdatm.TestBase.validEmptyJsonString;
import static com.circulardollar.cdatm.TestBase.validRandomCardNumber;
import static com.circulardollar.cdatm.TestBase.validRandomPinNumber;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.auth.LoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.LogoutRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.PinRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.token.RequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.validator.upstream.ATMRemoteValidator;
import com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

public class NetworkClientV2Test {

  @Test(expected = NullPointerException.class)
  public void newBuilder_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder().build();
  }

  @Test(expected = NullPointerException.class)
  public void newBuilder_url_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(null)
        .build();
  }

  @Test(expected = NullPointerException.class)
  public void newBuilder_ATMRemoteValidator_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder().setIATMRemoteValidator(null).setUrl(randomString()).build();
  }

  @Test
  public void newBuilder() {
    assertNotNull(
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
            .setUrl(randomString())
            .build());
  }

  @Test(expected = NullPointerException.class)
  public void verifyPin_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(randomString())
        .build()
        .verifyPin(null);
  }

  @Test(expected = NullPointerException.class)
  public void verifyPinV2_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(randomString())
        .build()
        .verifyPinV2(null);
  }

  @Test(expected = NullPointerException.class)
  public void getAccounts_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(randomString())
        .build()
        .getAccounts(null);
  }

  @Test(expected = NullPointerException.class)
  public void deposit_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(randomString())
        .build()
        .deposit(null);
  }

  @Test(expected = NullPointerException.class)
  public void withdraw_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(randomString())
        .build()
        .withdraw(null);
  }

  @Test(expected = NullPointerException.class)
  public void logout_null_whenExceptionThrown_thenExpectationSatisfied() {
    NetworkClientV2.newBuilder()
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build())
        .setUrl(randomString())
        .build()
        .logout(null);
  }

  @Test
  public void verifyPin_error() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateLoginRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IAuthRecord>> future =
        networkClientV2.verifyPin(
            RemoteRequest.<ILoginRecord>newBuilder().setBody(mock(ILoginRecord.class)).build());
    verify(atmRemoteValidator, times(1)).validateLoginRecord(any());
    assertNotNull(future);
    RemoteResponse<IAuthRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void verifyPin_body() throws IOException, InterruptedException, ExecutionException {
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
    INetworkClientV2 networkClientV2 =
        new NetworkClientV2(randomString(), atmRemoteValidator, httpClientBuilder);
    CompletableFuture<RemoteResponse<IAuthRecord>> future =
        networkClientV2.verifyPin(
            RemoteRequest.<ILoginRecord>newBuilder()
                .setBody(
                    LoginRecord.newBuilder()
                        .setCard(
                            CardRecord.newBuilder()
                                .setHolderName(randomString())
                                .setCardNumber(validRandomCardNumber())
                                .setCvc(randomString())
                                .setExpirationDate(randomString())
                                .build())
                        .setPin(PinRecord.newBuilder().setPinNumber(validRandomPinNumber()).build())
                        .build())
                .build());
    verify(atmRemoteValidator, times(1)).validateLoginRecord(any());
    assertNotNull(future);
    RemoteResponse<IAuthRecord> response = future.get();
    assertNotNull(response);
    assertNull(response.getError());
  }

  @Test
  public void verifyPinV2_error() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateLoginRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IAuthRecordV2>> future =
        networkClientV2.verifyPinV2(
            RemoteRequest.<ILoginRecord>newBuilder().setBody(mock(ILoginRecord.class)).build());
    verify(atmRemoteValidator, times(1)).validateLoginRecord(any());
    assertNotNull(future);
    RemoteResponse<IAuthRecordV2> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void verifyPinV2_body() throws IOException, InterruptedException, ExecutionException {
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
    INetworkClientV2 networkClientV2 =
        new NetworkClientV2(randomString(), atmRemoteValidator, httpClientBuilder);
    CompletableFuture<RemoteResponse<IAuthRecordV2>> future =
        networkClientV2.verifyPinV2(
            RemoteRequest.<ILoginRecord>newBuilder()
                .setBody(
                    LoginRecord.newBuilder()
                        .setCard(
                            CardRecord.newBuilder()
                                .setHolderName(randomString())
                                .setCardNumber(validRandomCardNumber())
                                .setCvc(randomString())
                                .setExpirationDate(randomString())
                                .build())
                        .setPin(PinRecord.newBuilder().setPinNumber(validRandomPinNumber()).build())
                        .build())
                .build());
    verify(atmRemoteValidator, times(1)).validateLoginRecord(any());
    assertNotNull(future);
    RemoteResponse<IAuthRecordV2> response = future.get();
    assertNotNull(response);
    assertNull(response.getError());
  }

  @Test
  public void getAccounts_error() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateRequestWithToken(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IAccountsRecord>> future =
        networkClientV2.getAccounts(
            RemoteRequest.<IRequestWithToken>newBuilder()
                .setBody(mock(IRequestWithToken.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    assertNotNull(future);
    RemoteResponse<IAccountsRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void getAccounts_body() throws IOException, InterruptedException, ExecutionException {
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
    INetworkClientV2 networkClientV2 =
        new NetworkClientV2(randomString(), atmRemoteValidator, httpClientBuilder);
    CompletableFuture<RemoteResponse<IAccountsRecord>> future =
        networkClientV2.getAccounts(
            RemoteRequest.<IRequestWithToken>newBuilder()
                .setBody(RequestWithToken.newBuilder().setTokenId(randomString()).build())
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    assertNotNull(future);
    RemoteResponse<IAccountsRecord> response = future.get();
    assertNotNull(response);
    assertNull(response.getError());
  }

  @Test
  public void deposit_error_01() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateDepositRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    when(atmRemoteValidator.validateRequestWithToken(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IDepositRecord>> future =
        networkClientV2.deposit(
            RemoteRequest.<IDepositRecordRequest>newBuilder()
                .setBody(mock(IDepositRecordRequest.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, never()).validateDepositRecord(any());
    assertNotNull(future);
    RemoteResponse<IDepositRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void deposit_error_02() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateDepositRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IDepositRecord>> future =
        networkClientV2.deposit(
            RemoteRequest.<IDepositRecordRequest>newBuilder()
                .setBody(mock(IDepositRecordRequest.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, times(1)).validateDepositRecord(any());
    assertNotNull(future);
    RemoteResponse<IDepositRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void deposit_body() throws IOException, InterruptedException, ExecutionException {
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
    INetworkClientV2 networkClientV2 =
        new NetworkClientV2(randomString(), atmRemoteValidator, httpClientBuilder);
    CompletableFuture<RemoteResponse<IDepositRecord>> future =
        networkClientV2.deposit(
            RemoteRequest.<IDepositRecordRequest>newBuilder()
                .setBody(
                    DepositRecordRequest.newBuilder()
                        .setTokenId(randomString())
                        .setAccount(
                            AccountRecord.newBuilder()
                                .setAccountNumber(randomString())
                                .setBalance(randomInt())
                                .build())
                        .setAmount(randomInt())
                        .setTimeStamp(randomLong())
                        .build())
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, times(1)).validateDepositRecord(any());
    assertNotNull(future);
    RemoteResponse<IDepositRecord> response = future.get();
    assertNotNull(response);
    assertNull(response.getError());
  }


  @Test
  public void withdraw_error_01() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateDepositRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    when(atmRemoteValidator.validateRequestWithToken(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IWithdrawRecord>> future =
        networkClientV2.withdraw(
            RemoteRequest.<IWithdrawRecordRequest>newBuilder()
                .setBody(mock(IWithdrawRecordRequest.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, never()).validateWithdrawRecord(any());
    assertNotNull(future);
    RemoteResponse<IWithdrawRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void withdraw_error_02() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateWithdrawRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<IWithdrawRecord>> future =
        networkClientV2.withdraw(
            RemoteRequest.<IWithdrawRecordRequest>newBuilder()
                .setBody(mock(IWithdrawRecordRequest.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, times(1)).validateWithdrawRecord(any());
    assertNotNull(future);
    RemoteResponse<IWithdrawRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void withdraw_body() throws IOException, InterruptedException, ExecutionException {
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
    INetworkClientV2 networkClientV2 =
        new NetworkClientV2(randomString(), atmRemoteValidator, httpClientBuilder);
    CompletableFuture<RemoteResponse<IWithdrawRecord>> future =
        networkClientV2.withdraw(
            RemoteRequest.<IWithdrawRecordRequest>newBuilder()
                .setBody(
                    WithdrawRecordRequest.newBuilder()
                        .setTokenId(randomString())
                        .setAccount(
                            AccountRecord.newBuilder()
                                .setAccountNumber(randomString())
                                .setBalance(randomInt())
                                .build())
                        .setAmount(randomInt())
                        .setTimeStamp(randomLong())
                        .build())
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, times(1)).validateWithdrawRecord(any());
    assertNotNull(future);
    RemoteResponse<IWithdrawRecord> response = future.get();
    assertNotNull(response);
    assertNull(response.getError());
  }

  @Test
  public void logout_error_01() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateRequestWithToken(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<ILogoutRecord>> future =
        networkClientV2.logout(
            RemoteRequest.<ILogoutRecordRequest>newBuilder()
                .setBody(mock(ILogoutRecordRequest.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, never()).validateLogoutRecord(any());
    assertNotNull(future);
    RemoteResponse<ILogoutRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void logout_error_02() throws ExecutionException, InterruptedException {
    IATMRemoteValidator atmRemoteValidator = mock(IATMRemoteValidator.class);
    when(atmRemoteValidator.validateLogoutRecord(any()))
        .thenReturn(
            Optional.of(
                ErrorRecord.newBuilder()
                    .setErrorCode(randomInt())
                    .setErrorMessages(randomList())
                    .build()));
    INetworkClientV2 networkClientV2 =
        NetworkClientV2.newBuilder()
            .setIATMRemoteValidator(atmRemoteValidator)
            .setUrl(randomString())
            .build();
    CompletableFuture<RemoteResponse<ILogoutRecord>> future =
        networkClientV2.logout(
            RemoteRequest.<ILogoutRecordRequest>newBuilder()
                .setBody(mock(ILogoutRecordRequest.class))
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, times(1)).validateLogoutRecord(any());
    assertNotNull(future);
    RemoteResponse<ILogoutRecord> response = future.get();
    assertNotNull(response);
    assertNotNull(response.getError());
  }

  @Test
  public void logout_body() throws IOException, InterruptedException, ExecutionException {
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
    INetworkClientV2 networkClientV2 =
        new NetworkClientV2(randomString(), atmRemoteValidator, httpClientBuilder);
    CompletableFuture<RemoteResponse<ILogoutRecord>> future =
        networkClientV2.logout(
            RemoteRequest.<ILogoutRecordRequest>newBuilder()
                .setBody(
                    LogoutRecordRequest.newBuilder()
                        .setTokenId(randomString())
                        .setTimeStamp(randomLong())
                        .build())
                .build());
    verify(atmRemoteValidator, times(1)).validateRequestWithToken(any());
    verify(atmRemoteValidator, times(1)).validateLogoutRecord(any());
    assertNotNull(future);
    RemoteResponse<ILogoutRecord> response = future.get();
    assertNotNull(response);
    assertNull(response.getError());
  }

}
