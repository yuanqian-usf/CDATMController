package com.circulardollar.cdatm.utils.network;

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
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.request.IRemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.APIVersions;
import com.circulardollar.cdatm.constant.DownstreamCommands;
import com.circulardollar.cdatm.network.ExceptionHandler;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.network.NonCSNetworkClientV2;
import com.circulardollar.cdatm.network.PureNetworkClientV2;
import com.circulardollar.cdatm.constant.Errors;
import com.circulardollar.cdatm.constant.UserInterface;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class NetworkUtils {
    public static final String LOCAL_HOST = "localhost";
    public static final String LOCAL_HOST_URL = "http://127.0.0.1:8080";

    public static IResponse<IAuth, IError> getVerifyPinResponseByApiVersion(INetworkClientV2 networkClientV2, APIVersions apiVersions, IRemoteRequest<ILoginRecord> request) {
        switch (apiVersions) {
            case V1:
                CompletableFuture<RemoteResponse<IAuthRecord>> authRecordV1Future = networkClientV2
                    .verifyPin(request);
                RemoteResponse<IAuthRecord> remoteAccountResponse =
                    ExceptionHandler.handleFuture(authRecordV1Future);
                if (remoteAccountResponse.getError() != null) {
                    return Response.<IAuth>newBuilder()
                        .setError(ErrorMapper.down(remoteAccountResponse.getError())).build();
                }
                List<IAccount> accounts =
                    remoteAccountResponse.getBody().getAccounts().stream().map(AccountMapper::down)
                        .collect(Collectors.toList());
                String tokenId = remoteAccountResponse.getBody().getTokenId();
                return Response.<IAuth>newBuilder().setBody(
                    Auth.newBuilder().setAccounts(accounts).setTimeStamp(remoteAccountResponse.getBody().getTimeStamp()).setTokenId(tokenId).build()).build();
            case V2:
                CompletableFuture<RemoteResponse<IAuthRecordV2>> authResponseV2 = networkClientV2
                    .verifyPinV2(request);
                RemoteResponse<IAuthRecordV2> iAuthRecordV2RemoteResponse =
                    ExceptionHandler.handleFuture(authResponseV2);
                if (iAuthRecordV2RemoteResponse.getError() != null) {
                    return Response.<IAuth>newBuilder()
                        .setError(ErrorMapper.down(iAuthRecordV2RemoteResponse.getError())).build();
                }
                String tokenIdV2 = iAuthRecordV2RemoteResponse.getBody().getTokenId();
                CompletableFuture<RemoteResponse<IAccountsRecord>> accountV2RemoteResponse =
                    networkClientV2.getAccounts(
                        IRemoteRequest.<String>newBuilder().setBody(tokenIdV2).build());
                RemoteResponse<IAccountsRecord> iAccountsRecordRemoteResponse =
                    ExceptionHandler.handleFuture(accountV2RemoteResponse);
                if (iAccountsRecordRemoteResponse.getError() != null) {
                    return Response.<IAuth>newBuilder()
                        .setError(ErrorMapper.down(iAccountsRecordRemoteResponse.getError()))
                        .build();
                }
                List<IAccount> accountsV2 =
                    iAccountsRecordRemoteResponse.getBody().getAccounts().stream().map(
                        iAccountRecord -> Account.newBuilder()
                            .setAccountNumber(iAccountRecord.getAccountNumber())
                            .setBalance(iAccountRecord.getBalance()).build())
                        .collect(Collectors.toList());
                return Response.<IAuth>newBuilder().setBody(Auth.newBuilder().setAccounts(accountsV2).setTokenId(tokenIdV2).setTimeStamp(iAccountsRecordRemoteResponse.getBody().getTimeStamp()).build()).build();
            default:
                return Response.<IAuth>newBuilder()
                    .setError(Error.of(NetworkUtils.class,
                        Arrays.asList(APIVersions.class.getName(), Errors.UNSUPPORTED_API_VERSION.getValue())))
                    .build();
        }
    }

    public static INetworkClientV2.IBuilder buildNetworkClientBuilder(String[] args,
        Set<DownstreamCommands> supportedCommands) throws IllegalArgumentException {
        INetworkClientV2.IBuilder networkClientBuilder = null;
        if (args == null) throw new IllegalArgumentException(Errors.INVALID_COMMAND.getValue());
        if (args.length > 1) {
            if (!supportedCommands.contains(DownstreamCommands.ofValue(args[0]))) {
                throw new IllegalArgumentException(Errors.UNSUPPORTED_COMMAND.getValue());
            }
            if (DownstreamCommands.URL.getValue().equalsIgnoreCase(args[0])) {
                if (args[1] == null || args[1].length() == 0) {
                    throw new IllegalArgumentException(UserInterface.INVALID_COMMAND.getValue());
                } else if (LOCAL_HOST.equalsIgnoreCase(args[1])) {
                    System.out.println(UserInterface.TESTING_WITH_LOCALHOST.getValue());
                    networkClientBuilder = PureNetworkClientV2.newBuilder().setUrl(LOCAL_HOST_URL);
                } else {
                    System.out.printf(UserInterface.CONNECT_TO_ENDPOINT.getValue(), args[1]);
                    networkClientBuilder = PureNetworkClientV2.newBuilder().setUrl(args[1]);
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
