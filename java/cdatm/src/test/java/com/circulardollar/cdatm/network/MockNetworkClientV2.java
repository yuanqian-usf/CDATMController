package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.*;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.business.upstream.request.IRemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.utils.MockError;
import com.circulardollar.cdatm.utils.MockUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static com.circulardollar.cdatm.utils.MockUtils.MOCK_NETWORK_DELAY;

public class MockNetworkClientV2 implements INetworkClientV2 {

  private static final List<String> ACCOUNT_NUMBERS = Arrays.asList("a", "b", "c");
  private static final List<Integer> BALANCES = Arrays.asList(-100, 0, 100);

  @Override
  public CompletableFuture<RemoteResponse<IAuthRecord>> verifyPin(IRemoteRequest<ILoginRecord> request) {
    CompletableFuture<RemoteResponse<IAuthRecord>> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            new ICallable<IRemoteRequest<ILoginRecord>, RemoteResponse<IAuthRecord>>(request) {
              @Override
              public RemoteResponse<IAuthRecord> call() throws Exception {

                Thread.sleep(MOCK_NETWORK_DELAY);

                String tokenId = UUID.randomUUID().toString();

                List<AccountRecord> accounts = new ArrayList<>();

                IntStream.range(0, ACCOUNT_NUMBERS.size())
                    .forEach(
                        index ->
                            accounts.add(
                                AccountRecord.newBuilder()
                                    .setAccountNumber(ACCOUNT_NUMBERS.get(index))
                                    .setBalance(BALANCES.get(index))
                                    .build()));
                RemoteResponse<IAuthRecord> response =
                    RemoteResponse.<IAuthRecord>newBuilder()
                        .setBody(
                            AuthRecord.newBuilder()
                                .setTokenId(tokenId)
                                .setAccounts(accounts)
                                .setTimeStamp(System.currentTimeMillis())
                                .build())
                        .build();
                completableFuture.complete(response);
                return null;
              }
            });
    return completableFuture;
  }

  @Override
  public CompletableFuture<RemoteResponse<IAuthRecordV2>> verifyPinV2(IRemoteRequest<ILoginRecord> request) {
    CompletableFuture<RemoteResponse<IAuthRecordV2>> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            new ICallable<IRemoteRequest<ILoginRecord>, RemoteResponse<IAuthRecordV2>>(request) {
              @Override
              public RemoteResponse<IAuthRecordV2> call() throws Exception {

                Thread.sleep(MOCK_NETWORK_DELAY);

                String tokenId = UUID.randomUUID().toString();

                List<IAccountRecord> accounts = new ArrayList<>();

                IntStream.range(0, ACCOUNT_NUMBERS.size())
                    .forEach(
                        index ->
                            accounts.add(
                                AccountRecord.newBuilder()
                                    .setAccountNumber(ACCOUNT_NUMBERS.get(index))
                                    .setBalance(BALANCES.get(index))
                                    .build()));
                RemoteResponse<IAuthRecordV2> response =
                    RemoteResponse.<IAuthRecordV2>newBuilder()
                        .setBody(
                            AuthRecordV2.newBuilder()
                                .setTokenId(tokenId)
                                .setTimeStamp(System.currentTimeMillis())
                                .build())
                        .build();
                completableFuture.complete(response);
                return null;
              }
            });
    return completableFuture;
  }

  @Override
  public CompletableFuture<RemoteResponse<IAccountsRecord>> getAccounts(IRemoteRequest<String> request) {
    CompletableFuture<RemoteResponse<IAccountsRecord>> completableFuture =
        new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            new ICallable<IRemoteRequest<String>, RemoteResponse<IAccountsRecord>>(request) {
              @Override
              public RemoteResponse<IAccountsRecord> call() throws Exception {

                Thread.sleep(MOCK_NETWORK_DELAY);

                List<AccountRecord> accounts = new ArrayList<>();

                IntStream.range(0, ACCOUNT_NUMBERS.size())
                    .forEach(
                        index ->
                            accounts.add(
                                AccountRecord.newBuilder()
                                    .setAccountNumber(ACCOUNT_NUMBERS.get(index))
                                    .setBalance(BALANCES.get(index))
                                    .build()));
                RemoteResponse<IAccountsRecord> response =
                    RemoteResponse.<IAccountsRecord>newBuilder()
                        .setBody(
                            AccountsRecord.newBuilder()
                                .setAccounts(accounts)
                                .setTimeStamp(System.currentTimeMillis())
                                .build())
                        .build();
                completableFuture.complete(response);
                return null;
              }
            });
    return completableFuture;
  }

  @Override
  public CompletableFuture<RemoteResponse<IDepositRecord>> deposit(
      IRemoteRequest<IDepositRecordRequest> request) {
    CompletableFuture<RemoteResponse<IDepositRecord>> completableFuture =
        new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            new ICallable<IRemoteRequest<IDepositRecordRequest>, RemoteResponse<IDepositRecord>>(
                request) {
              @Override
              public RemoteResponse<IDepositRecord> call() throws Exception {
                Thread.sleep(MOCK_NETWORK_DELAY);

                RemoteResponse<IDepositRecord> response;
                Integer index =
                    ACCOUNT_NUMBERS.indexOf(getInput().getBody().getAccount().getAccountNumber());
                Optional<Integer> latestBalance =
                    MockUtils.getModifiedBalance(
                        1, BALANCES.get(index), getInput().getBody().getAmount());
                if (latestBalance.isPresent()) {
                  response =
                      RemoteResponse.<IDepositRecord>newBuilder()
                          .setBody(
                              DepositRecord.newBuilder()
                                  .setAccount(
                                      AccountRecord.newBuilder()
                                          .setAccountNumber(ACCOUNT_NUMBERS.get(index))
                                          .setBalance(latestBalance.get())
                                          .build())
                                  .setAmount(getInput().getBody().getAmount())
                                  .setTimeStamp(System.currentTimeMillis())
                                  .build())
                          .build();
                } else {
                  response =
                      RemoteResponse.<IDepositRecord>newBuilder()
                          .setError(MockUtils.error(MockError.SERVER_001))
                          .build();
                }
                completableFuture.complete(response);
                return super.call();
              }
            });
    return completableFuture;
  }

  @Override
  public CompletableFuture<RemoteResponse<IWithdrawRecord>> withdraw(
      IRemoteRequest<IWithdrawRecordRequest> request) {
    CompletableFuture<RemoteResponse<IWithdrawRecord>> completableFuture =
        new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            new ICallable<IRemoteRequest<IWithdrawRecordRequest>, RemoteResponse<IWithdrawRecord>>(
                request) {
              @Override
              public RemoteResponse<IWithdrawRecord> call() throws Exception {

                RemoteResponse<IWithdrawRecord> response;
                Integer index =
                    ACCOUNT_NUMBERS.indexOf(getInput().getBody().getAccount().getAccountNumber());
                Optional<Integer> latestBalance =
                    MockUtils.getModifiedBalance(
                        -1, BALANCES.get(index), getInput().getBody().getAmount());
                if (latestBalance.isPresent()) {
                  response =
                      RemoteResponse.<IWithdrawRecord>newBuilder()
                          .setBody(
                              WithdrawRecord.newBuilder()
                                  .setAccount(
                                      AccountRecord.newBuilder()
                                          .setAccountNumber(ACCOUNT_NUMBERS.get(index))
                                          .setBalance(latestBalance.get())
                                          .build())
                                  .setAmount(getInput().getBody().getAmount())
                                  .setTimeStamp(System.currentTimeMillis())
                                  .build())
                          .build();
                } else {
                  response =
                      RemoteResponse.<IWithdrawRecord>newBuilder()
                          .setError(MockUtils.error(MockError.SERVER_002))
                          .build();
                }
                completableFuture.complete(response);
                return super.call();
              }
            });
    return completableFuture;
  }

  @Override
  public CompletableFuture<RemoteResponse<ILogoutRecord>> logout(IRemoteRequest<ILogoutRecordRequest> logout) {
    return null;
  }
}
