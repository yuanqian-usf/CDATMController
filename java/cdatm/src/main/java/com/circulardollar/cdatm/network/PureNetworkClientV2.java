package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.*;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.error.Error;
import com.circulardollar.cdatm.business.upstream.model.error.IError;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.business.upstream.request.IRemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.Errors;
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class PureNetworkClientV2 implements INetworkClientV2 {


    private final static String VERSION_1 = "/v1";
    private final static String VERSION_2 = "/v2";
    private final static String URL_VERIFY_PIN = "/verify_pin";
    private final static String URL_ACCOUNTS = "/accounts";
    private final static String URL_DEPOSIT = "/deposit";
    private final static String URL_WITHDRAW = "/withdraw";
    private static final String URL_LOGOUT = "/logout";

    private final String url;

    PureNetworkClientV2(String url) {
        this.url = url;

    }

    public static IBuilder newBuilder() {
        return new Builder();
    }

    @Override public CompletableFuture<RemoteResponse<IAuthRecord>> verifyPin(
        IRemoteRequest<ILoginRecord> request) {
        RemoteResponse.Builder<IAuthRecord> builder = RemoteResponse.newBuilder();
        if (request == null || request.getBody() == null || request.getBody().getCard() == null
            || request.getBody().getPin() == null)
            return CompletableFuture.completedFuture(builder.setError(Error
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue())))
                .build());
        CompletableFuture<RemoteResponse<IAuthRecord>> completableFuture =
            new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(
            new ICallable<IRemoteRequest<ILoginRecord>, RemoteResponse<IAuthRecord>>(request) {
                @Override public RemoteResponse<IAuthRecord> call() {
                    Gson gson = new Gson();
                    RemoteResponse.Builder<IAuthRecord> responseBuilder =
                        RemoteResponse.newBuilder();
                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpPost httpPost = new HttpPost(url + VERSION_1 + URL_VERIFY_PIN);
                        String jsonRequest = gson.toJson(request.getBody());
                        StringEntity params = new StringEntity(jsonRequest);
                        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                        httpPost.setEntity(params);
                        HttpResponse result = httpClient.execute(httpPost);
                        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                        handleResponse(AuthRecord.class, responseBuilder, json);
                    } catch (IOException e1) {
                        responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                            Arrays.asList(Errors.IO_EXCEPTION.getValue(), e1.getMessage())));
                    }
                    completableFuture.complete(responseBuilder.build());
                    return null;
                }
            });

        return completableFuture;
    }

    @Override public CompletableFuture<RemoteResponse<IAuthRecordV2>> verifyPinV2(
        IRemoteRequest<ILoginRecord> request) {
        RemoteResponse.Builder<IAuthRecordV2> builder = RemoteResponse.newBuilder();
        if (request == null || request.getBody() == null || request.getBody().getCard() == null
            || request.getBody().getPin() == null)
            return CompletableFuture.completedFuture(builder.setError(Error
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue())))
                .build());
        CompletableFuture<RemoteResponse<IAuthRecordV2>> completableFuture =
            new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(
            new ICallable<IRemoteRequest<ILoginRecord>, RemoteResponse<IAuthRecordV2>>(request) {
                @Override public RemoteResponse<IAuthRecordV2> call() {
                    Gson gson = new Gson();
                    RemoteResponse.Builder<IAuthRecordV2> responseBuilder =
                        RemoteResponse.newBuilder();
                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpPost httpPost = new HttpPost(url + VERSION_2 + URL_VERIFY_PIN);
                        String jsonRequest = gson.toJson(request.getBody());
                        StringEntity params = new StringEntity(jsonRequest);
                        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                        httpPost.setEntity(params);
                        HttpResponse result = httpClient.execute(httpPost);
                        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                        handleResponse(AuthRecordV2.class, responseBuilder, json);
                    } catch (IOException e1) {
                        responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                            Arrays.asList(Errors.IO_EXCEPTION.getValue(), e1.getMessage())));
                    }
                    completableFuture.complete(responseBuilder.build());
                    return null;
                }
            });

        return completableFuture;
    }

    @Override public CompletableFuture<RemoteResponse<IAccountsRecord>> getAccounts(
        IRemoteRequest<String> request) {
        RemoteResponse.Builder<IAccountsRecord> builder = RemoteResponse.newBuilder();
        if (request == null || request.getBody() == null || request.getBody().isEmpty()) {
            return CompletableFuture.completedFuture(builder.setError(Error
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue())))
                .build());
        }
        CompletableFuture<RemoteResponse<IAccountsRecord>> completableFuture =
            new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(
            new ICallable<IRemoteRequest<String>, RemoteResponse<IAccountsRecord>>(request) {
                @Override public RemoteResponse<IAccountsRecord> call() {
                    RemoteResponse.Builder<IAccountsRecord> responseBuilder =
                        RemoteResponse.newBuilder();

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpGet httpGet = new HttpGet(url + VERSION_2 + URL_ACCOUNTS);
                        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + request.getBody());
                        HttpResponse result = httpClient.execute(httpGet);
                        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                        handleResponse(AccountsRecord.class, responseBuilder, json);
                    } catch (IOException e1) {
                        responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                            Arrays.asList(Errors.IO_EXCEPTION.getValue(), e1.getMessage())));
                    }
                    completableFuture.complete(responseBuilder.build());
                    return null;
                }
            });

        return completableFuture;
    }

    @Override public CompletableFuture<RemoteResponse<IDepositRecord>> deposit(
        IRemoteRequest<IDepositRecordRequest> request) {
        RemoteResponse.Builder<IDepositRecord> builder = RemoteResponse.newBuilder();
        if (request == null || request.getBody() == null || request.getBody().getTokenId() == null
            || request.getBody().getTokenId().isEmpty() || request.getBody().getAccount() == null
            || request.getBody().getAccount().getAccountNumber() == null || request.getBody()
            .getAccount().getAccountNumber().isEmpty()) {
            return CompletableFuture.completedFuture(builder.setError(Error
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue())))
                .build());
        }
        CompletableFuture<RemoteResponse<IDepositRecord>> completableFuture =
            new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(
            new ICallable<IRemoteRequest<IDepositRecordRequest>, RemoteResponse<IDepositRecord>>(
                request) {
                @Override public RemoteResponse<IDepositRecord> call() {
                    Gson gson = new Gson();
                    RemoteResponse.Builder<IDepositRecord> responseBuilder =
                        RemoteResponse.newBuilder();

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpPost httpPost = new HttpPost(url + VERSION_1 + URL_DEPOSIT);
                        String jsonRequest = gson.toJson(request.getBody());
                        StringEntity params = new StringEntity(jsonRequest);
                        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                        httpPost.setHeader(HttpHeaders.AUTHORIZATION,
                            "Bearer " + request.getBody().getTokenId());
                        httpPost.setEntity(params);
                        HttpResponse result = httpClient.execute(httpPost);
                        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                        handleResponse(DepositRecord.class, responseBuilder, json);
                    } catch (IOException e1) {
                        responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                            Arrays.asList(Errors.IO_EXCEPTION.getValue(), e1.getMessage())));
                    }
                    completableFuture.complete(responseBuilder.build());
                    return null;
                }
            });

        return completableFuture;
    }


    @Override public CompletableFuture<RemoteResponse<IWithdrawRecord>> withdraw(
        IRemoteRequest<IWithdrawRecordRequest> request) {
        RemoteResponse.Builder<IWithdrawRecord> builder = RemoteResponse.newBuilder();
        if (request == null || request.getBody() == null || request.getBody().getTokenId() == null
            || request.getBody().getTokenId().isEmpty() || request.getBody().getAccount() == null
            || request.getBody().getAccount().getAccountNumber() == null || request.getBody()
            .getAccount().getAccountNumber().isEmpty()) {
            return CompletableFuture.completedFuture(builder.setError(Error
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue())))
                .build());
        }

        CompletableFuture<RemoteResponse<IWithdrawRecord>> completableFuture =
            new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(
            new ICallable<IRemoteRequest<IWithdrawRecordRequest>, RemoteResponse<IWithdrawRecord>>(
                request) {
                @Override public RemoteResponse<IWithdrawRecord> call() {
                    Gson gson = new Gson();
                    RemoteResponse.Builder<IWithdrawRecord> responseBuilder =
                        RemoteResponse.newBuilder();

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpPost httpPost = new HttpPost(url + VERSION_1 + URL_WITHDRAW);
                        String jsonRequest = gson.toJson(request.getBody());
                        StringEntity params = new StringEntity(jsonRequest);
                        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                        httpPost.setHeader(HttpHeaders.AUTHORIZATION,
                            "Bearer " + request.getBody().getTokenId());
                        httpPost.setEntity(params);
                        HttpResponse result = httpClient.execute(httpPost);
                        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                        handleResponse(WithdrawRecord.class, responseBuilder, json);
                    } catch (IOException e1) {
                        responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                            Arrays.asList(Errors.IO_EXCEPTION.getValue(), e1.getMessage())));
                    }
                    completableFuture.complete(responseBuilder.build());
                    return null;
                }
            });

        return completableFuture;
    }

    @Override public CompletableFuture<RemoteResponse<ILogoutRecord>> logout(
        IRemoteRequest<ILogoutRecordRequest> request) {
        RemoteResponse.Builder<ILogoutRecord> builder = RemoteResponse.newBuilder();
        if (request.getBody().getTokenId() == null) {
            return CompletableFuture.completedFuture(builder.setError(Error
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.INVALID_TOKEN.getValue()))).build());
        }

        CompletableFuture<RemoteResponse<ILogoutRecord>> completableFuture =
            new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(
            new ICallable<IRemoteRequest<ILogoutRecordRequest>, RemoteResponse<ILogoutRecord>>(
                request) {
                @Override public RemoteResponse<ILogoutRecord> call() {
                    Gson gson = new Gson();
                    RemoteResponse.Builder<ILogoutRecord> responseBuilder =
                        RemoteResponse.newBuilder();

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpPost httpPost = new HttpPost(url + VERSION_1 + URL_LOGOUT);
                        String jsonRequest = gson.toJson(request.getBody());
                        StringEntity params = new StringEntity(jsonRequest);
                        httpPost.addHeader("content-type", ContentType.APPLICATION_JSON.getMimeType());
                        httpPost.setHeader(HttpHeaders.AUTHORIZATION,
                            "Bearer " + request.getBody().getTokenId());
                        httpPost.setEntity(params);
                        HttpResponse result = httpClient.execute(httpPost);
                        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                        handleResponse(LogoutRecord.class, responseBuilder, json);
                    } catch (IOException e1) {
                        responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                            Arrays.asList(Errors.IO_EXCEPTION.getValue(), e1.getMessage())));
                    }
                    completableFuture.complete(responseBuilder.build());
                    return null;
                }
            });

        return completableFuture;
    }

    public <T extends S, S> void handleResponse(Class<T> responseClass,
        RemoteResponse.Builder<S> responseBuilder, String json) {
        Gson gson = new Gson();
        try {
            IError error = gson.fromJson(json, Error.class);
            if (error.getErrorCode() != null) {
                responseBuilder.setError(error);
                return;
            }
            S response = gson.fromJson(json, responseClass);
            responseBuilder.setBody(response);
        } catch (Exception e) {
            responseBuilder.setError(Error.of(PureNetworkClientV2.class,
                Arrays.asList(Errors.JSON_PARSE_EXCEPTION.getValue(), e.getMessage())));
        }
    }

    public static class Builder extends IBuilder {
        String url;

        public IBuilder setUrl(String url) {
            Objects.requireNonNull(url);
            this.url = url;
            return this;
        }

        public INetworkClientV2 build() {
            return new PureNetworkClientV2(url);
        }
    }
}
