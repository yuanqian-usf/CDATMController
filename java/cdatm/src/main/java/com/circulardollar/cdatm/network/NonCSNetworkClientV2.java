package com.circulardollar.cdatm.network;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.AccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.*;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.business.upstream.request.IRemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.Errors;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class NonCSNetworkClientV2 implements INetworkClientV2 {
    private final Map<String, IPinRecord> cardNumberLoginRecordTable;
    private final Map<String, Map<String, IAccountRecord>>
        cardNumberAccountNumberAccountRecordTable;
    private final Map<String, String> cardNumberAuthTokenTable;
    private final Map<String, String> authTokenCardNumberTable;
    private final Map<String, Long> authTokenLastLoginTimeStampTable;

    private NonCSNetworkClientV2(Map<ILoginRecord, IAccountsRecord> accountsRecordTable) {
        Objects.requireNonNull(accountsRecordTable);
        cardNumberLoginRecordTable = new HashMap<>();
        cardNumberAccountNumberAccountRecordTable = new HashMap<>();
        cardNumberAuthTokenTable = new HashMap<>();
        authTokenCardNumberTable = new HashMap<>();
        authTokenLastLoginTimeStampTable = new HashMap<>();
        for (Map.Entry<ILoginRecord, IAccountsRecord> entry : accountsRecordTable.entrySet()) {
            cardNumberLoginRecordTable
                .put(entry.getKey().getCard().getCardNumber(), entry.getKey().getPin());
            cardNumberAccountNumberAccountRecordTable.put(entry.getKey().getCard().getCardNumber(),
                entry.getValue().getAccounts().stream().collect(
                    Collectors.toMap(IAccountRecord::getAccountNumber, account -> account)));
        }
    }

    public static IBuilder newBuilder() {
        return new Builder();
    }

    @Override public CompletableFuture<RemoteResponse<IAuthRecord>> verifyPin(
        IRemoteRequest<ILoginRecord> loginRequest) {
        RemoteResponse.Builder<IAuthRecord> builder = RemoteResponse.newBuilder();
        if (loginRequest == null || loginRequest.getBody() == null
            || loginRequest.getBody().getCard() == null || loginRequest.getBody().getPin() == null)
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue()))).build());
        if (!cardNumberLoginRecordTable
            .containsKey(loginRequest.getBody().getCard().getCardNumber())) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.INVALID_CARD_OR_PIN.getValue()))).build());
        }
        String cardNumber = loginRequest.getBody().getCard().getCardNumber();
        if (cardNumberLoginRecordTable.containsKey(cardNumber) && !loginRequest.getBody().getPin()
            .getPinNumber().equals(cardNumberLoginRecordTable.get(cardNumber).getPinNumber())) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.INVALID_CARD_OR_PIN.getValue()))).build());
        }
        String latestToken = UUID.randomUUID().toString();
        refreshToken(cardNumber, latestToken);
        return CompletableFuture.completedFuture(RemoteResponse.<IAuthRecord>newBuilder().setBody(
            AuthRecord.newBuilder().setAccounts(
                cardNumberAccountNumberAccountRecordTable.get(cardNumber).values().stream().map(
                    iAccountRecord -> AccountRecord.newBuilder()
                        .setAccountNumber(iAccountRecord.getAccountNumber())
                        .setBalance(iAccountRecord.getBalance()).build())
                    .collect(Collectors.toList())).setTokenId(latestToken)
                .setTimeStamp(System.currentTimeMillis()).build()).build());
    }

    @Override public CompletableFuture<RemoteResponse<IAuthRecordV2>> verifyPinV2(
        IRemoteRequest<ILoginRecord> loginRequest) {
        RemoteResponse.Builder<IAuthRecordV2> builder = RemoteResponse.newBuilder();
        if (loginRequest == null || loginRequest.getBody() == null
            || loginRequest.getBody().getCard() == null || loginRequest.getBody().getPin() == null)
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue()))).build());
        if (!cardNumberLoginRecordTable
            .containsKey(loginRequest.getBody().getCard().getCardNumber())) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.INVALID_CARD_OR_PIN.getValue()))).build());
        }
        String cardNumber = loginRequest.getBody().getCard().getCardNumber();
        if (cardNumberLoginRecordTable.containsKey(cardNumber) && !loginRequest.getBody().getPin()
            .getPinNumber().equals(cardNumberLoginRecordTable.get(cardNumber).getPinNumber())) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.INVALID_CARD_OR_PIN.getValue()))).build());
        }
        String latestToken = UUID.randomUUID().toString();
        refreshToken(cardNumber, latestToken);
        return CompletableFuture.completedFuture(RemoteResponse.<IAuthRecordV2>newBuilder()
            .setBody(AuthRecordV2.newBuilder().setTokenId(latestToken)
                .setTimeStamp(System.currentTimeMillis()).build()).build());
    }

    @Override public CompletableFuture<RemoteResponse<IAccountsRecord>> getAccounts(
        IRemoteRequest<String> tokenRequest) {
        RemoteResponse.Builder<IAccountsRecord> builder = RemoteResponse.newBuilder();
        if (tokenRequest == null || tokenRequest.getBody() == null || tokenRequest.getBody()
            .isEmpty()) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue())))
                .build());
        }
        if (!authTokenLastLoginTimeStampTable.containsKey(tokenRequest.getBody()) || isExpiredToken(
            authTokenLastLoginTimeStampTable.get(tokenRequest.getBody()))) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord
                .of(INetworkClientV2.class,
                    Collections.singletonList(Errors.INVALID_TOKEN.getValue()))).build());
        }
        String cardNumber = authTokenCardNumberTable.get(tokenRequest.getBody());
        return CompletableFuture.completedFuture(RemoteResponse.<IAccountsRecord>newBuilder()
            .setBody(AccountsRecord.newBuilder().setAccounts(Collections.unmodifiableList(
                cardNumberAccountNumberAccountRecordTable.get(cardNumber).values().stream().map(
                    iAccountRecord -> AccountRecord.newBuilder()
                        .setAccountNumber(iAccountRecord.getAccountNumber())
                        .setBalance(iAccountRecord.getBalance()).build())
                    .collect(Collectors.toList()))).setTimeStamp(System.currentTimeMillis())
                .build()).build());
    }

    private boolean isExpiredToken(Long lastLoginTimeStamp) {
        return System.currentTimeMillis() - lastLoginTimeStamp > TimeUnit.MINUTES.toMillis(5);
    }

    @Override public CompletableFuture<RemoteResponse<IDepositRecord>> deposit(
        IRemoteRequest<IDepositRecordRequest> depositRequest) {
        RemoteResponse.Builder<IDepositRecord> builder = RemoteResponse.newBuilder();
        if (depositRequest == null || depositRequest.getBody() == null
            || depositRequest.getBody().getTokenId() == null || depositRequest.getBody()
            .getTokenId().isEmpty() || depositRequest.getBody().getAccount() == null
            || depositRequest.getBody().getAccount().getAccountNumber() == null || depositRequest
            .getBody().getAccount().getAccountNumber().isEmpty()) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue()))).build());
        }
        if (!authTokenLastLoginTimeStampTable.containsKey(depositRequest.getBody().getTokenId())
            || isExpiredToken(
            authTokenLastLoginTimeStampTable.get(depositRequest.getBody().getTokenId()))) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.INVALID_TOKEN.getValue()))).build());
        }
        String cardNumber = authTokenCardNumberTable.get(depositRequest.getBody().getTokenId());
        String accountNumber = depositRequest.getBody().getAccount().getAccountNumber();
        IAccountRecord accountRecord =
            cardNumberAccountNumberAccountRecordTable.get(cardNumber).get(accountNumber);
        Integer existingBalance = accountRecord.getBalance();
        Integer depositAmount = depositRequest.getBody().getAmount();
        if (existingBalance.longValue() + depositAmount.longValue() > Integer.MAX_VALUE) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.MAX_BALANCE_REACHED.getValue()))).build());
        }
        AccountRecord latestAccountRecord =
            updateAccountBalance(cardNumber, accountNumber, existingBalance + depositAmount);
        return CompletableFuture.completedFuture(RemoteResponse.<IDepositRecord>newBuilder()
            .setBody(
                DepositRecord.newBuilder().setAccount(latestAccountRecord).setAmount(depositAmount)
                    .setTimeStamp(System.currentTimeMillis()).build()).build());
    }

    private AccountRecord updateAccountBalance(String cardNumber, String accountNumber,
        int latestBalance) {
        AccountRecord accountRecord =
            AccountRecord.newBuilder().setAccountNumber(accountNumber).setBalance(latestBalance)
                .build();
        cardNumberAccountNumberAccountRecordTable.get(cardNumber).put(accountNumber, accountRecord);
        return accountRecord;
    }

    @Override public CompletableFuture<RemoteResponse<IWithdrawRecord>> withdraw(
        IRemoteRequest<IWithdrawRecordRequest> withdrawRequest) {
        RemoteResponse.Builder<IWithdrawRecord> builder = RemoteResponse.newBuilder();
        if (withdrawRequest == null || withdrawRequest.getBody() == null
            || withdrawRequest.getBody().getTokenId() == null || withdrawRequest.getBody()
            .getTokenId().isEmpty() || withdrawRequest.getBody().getAccount() == null
            || withdrawRequest.getBody().getAccount().getAccountNumber() == null || withdrawRequest
            .getBody().getAccount().getAccountNumber().isEmpty()) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.ILLEGAL_PARAMETER_EXCEPTION.getValue()))).build());
        }
        if (!authTokenLastLoginTimeStampTable.containsKey(withdrawRequest.getBody().getTokenId())
            || isExpiredToken(
            authTokenLastLoginTimeStampTable.get(withdrawRequest.getBody().getTokenId()))) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.INVALID_TOKEN.getValue()))).build());
        }
        String cardNumber = authTokenCardNumberTable.get(withdrawRequest.getBody().getTokenId());
        String accountNumber = withdrawRequest.getBody().getAccount().getAccountNumber();
        IAccountRecord accountRecord =
            cardNumberAccountNumberAccountRecordTable.get(cardNumber).get(accountNumber);
        Integer existingBalance = accountRecord.getBalance();
        Integer withdrawAmount = withdrawRequest.getBody().getAmount();
        if (existingBalance.longValue() - withdrawAmount.longValue() < 0) {
            return CompletableFuture.completedFuture(builder.setError(ErrorRecord.of(INetworkClientV2.class,
                Collections.singletonList(Errors.ZERO_BALANCE_REACHED.getValue()))).build());
        }
        AccountRecord latestAccountRecord =
            updateAccountBalance(cardNumber, accountNumber, existingBalance - withdrawAmount);
        return CompletableFuture.completedFuture(RemoteResponse.<IWithdrawRecord>newBuilder()
            .setBody(WithdrawRecord.newBuilder().setAccount(latestAccountRecord)
                .setAmount(withdrawAmount).setTimeStamp(System.currentTimeMillis()).build())
            .build());
    }

    @Override
    public CompletableFuture<RemoteResponse<ILogoutRecord>> logout(IRemoteRequest<ILogoutRecordRequest> logout) {
        return CompletableFuture.completedFuture(RemoteResponse.<ILogoutRecord>newBuilder()
            .setBody(LogoutRecord.newBuilder().setTimeStamp(System.currentTimeMillis()).build())
            .build());
    }

    private void refreshToken(String cardNumber, String latestToken) {
        String lastToken = cardNumberAuthTokenTable.get(cardNumber);
        authTokenLastLoginTimeStampTable.remove(lastToken); // clear existing token for this card
        authTokenCardNumberTable.remove(lastToken);
        cardNumberAuthTokenTable.put(cardNumber, latestToken); // clear existing token for this card
        authTokenCardNumberTable.put(latestToken, cardNumber); // clear existing card for this token
        authTokenLastLoginTimeStampTable
            .put(latestToken, System.currentTimeMillis()); // add to token set for other operations
    }

    public static class Builder extends IBuilder {
        Map<ILoginRecord, IAccountsRecord> accountsRecordTable;

        public IBuilder setAccountsRecordTable(
            Map<ILoginRecord, IAccountsRecord> accountsRecordTable) {
            Objects.requireNonNull(accountsRecordTable);
            this.accountsRecordTable = accountsRecordTable;
            return this;
        }

        public INetworkClientV2 build() {
            return new NonCSNetworkClientV2(accountsRecordTable);
        }
    }
}
