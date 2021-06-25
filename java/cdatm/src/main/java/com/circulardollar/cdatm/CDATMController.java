package com.circulardollar.cdatm;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.auth.IAuth;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.model.withdraw.Withdraw;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.downstream.response.Response;
import com.circulardollar.cdatm.business.mapper.account.AccountMapper;
import com.circulardollar.cdatm.business.mapper.card.CardMapper;
import com.circulardollar.cdatm.business.mapper.deposit.DepositMapper;
import com.circulardollar.cdatm.business.mapper.error.ErrorMapper;
import com.circulardollar.cdatm.business.mapper.pin.PinMapper;
import com.circulardollar.cdatm.business.mapper.withdraw.WithdrawMapper;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.auth.LoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.LogoutRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecordRequest;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecordRequest;
import com.circulardollar.cdatm.business.upstream.request.RemoteRequest;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.constant.APIVersions;
import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.network.ExceptionHandler;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.session.IATMSessionController;
import com.circulardollar.cdatm.state.IATMStateController;
import com.circulardollar.cdatm.utils.network.NetworkUtils;
import com.circulardollar.cdatm.validator.IATMValidator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CDATMController implements IATMController {

  private final IATMStateController atmStateController;
  private final IATMSessionController atmSessionController;
  private final IATMValidator atmValidator;
  private final INetworkClientV2 networkClientV2;

  CDATMController(
      IATMStateController atmStateController,
      IATMSessionController atmSessionController,
      IATMValidator atmValidator,
      INetworkClientV2 networkClientV2)
      throws IllegalArgumentException {

    Objects.requireNonNull(atmStateController);
    Objects.requireNonNull(atmSessionController);
    Objects.requireNonNull(atmValidator);
    Objects.requireNonNull(networkClientV2);
    this.atmStateController = atmStateController;
    this.atmSessionController = atmSessionController;
    this.atmValidator = atmValidator;
    this.networkClientV2 = networkClientV2;
    validate();
  }

  public static IBuilder newBuilder() {
    return new Builder();
  }

  void validate() {
    Optional<IError> configError;
    configError = atmValidator.validateStateController(atmStateController);
    if (configError.isPresent()) throw new IllegalArgumentException(configError.toString());
    configError = atmValidator.validateSessionController(atmSessionController);
    if (configError.isPresent()) throw new IllegalArgumentException(configError.toString());
  }

  @Override
  public synchronized IResponse<Boolean, IError> insertCard(ICard card) {
    Integer stateId = ATMStates.INSERT_CARD.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<Boolean>newBuilder().setError(stateError.get()).build();
    Optional<IError> error = atmValidator.validateCard(card);
    if (error.isPresent()) return Response.<Boolean>newBuilder().setError(error.get()).build();
    atmSessionController.setCard(card);
    atmStateController.nextState(stateId);
    return Response.<Boolean>newBuilder().setBody(true).build();
  }

  @Override
  public synchronized IResponse<List<String>, IError> verifyPin(IPin pin) {
    APIVersions apiVersions = atmValidator.getATMConfigurations().getAPIVersion();
    Integer stateId = ATMStates.VERIFY_PIN.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<List<String>>newBuilder().setError(stateError.get()).build();
    ICard card = atmSessionController.getCard();
    Objects.requireNonNull(card);
    Optional<IError> error = atmValidator.validatePin(pin);
    if (error.isPresent()) return Response.<List<String>>newBuilder().setError(error.get()).build();
    RemoteRequest<ILoginRecord> request =
        RemoteRequest.<ILoginRecord>newBuilder()
            .setBody(
                LoginRecord.newBuilder()
                    .setCard(CardMapper.up(card))
                    .setPin(PinMapper.up(pin))
                    .build())
            .build();
    IResponse<IAuth, IError> response =
        NetworkUtils.getVerifyPinResponseByApiVersion(networkClientV2, apiVersions, request);
    if (response.getError() != null) return Response.<List<String>>newBuilder().setError(response.getError()).build();
    atmSessionController.setAccounts(
        response.getBody().getTokenId(),
        AccountMapper.mapAccounts(response.getBody().getAccounts()));
    atmStateController.nextState(stateId);
    List<String> accountNumbers =
        response.getBody().getAccounts().stream()
            .map(IAccount::getAccountNumber)
            .collect(Collectors.toList());
    return Response.<List<String>>newBuilder().setBody(accountNumbers).build();
  }

  @Override
  public synchronized IResponse<Boolean, IError> selectAccount(String accountNumber) {
    Integer stateId = ATMStates.SELECT_ACCOUNT.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<Boolean>newBuilder().setError(stateError.get()).build();
    Map<String, IAccount> accounts = atmSessionController.getAccounts();
    Objects.requireNonNull(accounts);
    Optional<IError> error =
        atmValidator.validateAccount(
            accounts.getOrDefault(accountNumber, null));
    if (error.isPresent()) return Response.<Boolean>newBuilder().setError(error.get()).build();
    atmSessionController.setSelectedAccountNumber(accountNumber);
    atmStateController.nextState(stateId);
    return Response.<Boolean>newBuilder().setBody(true).build();
  }

  @Override
  public synchronized IResponse<Integer, IError> checkBalance() {
    Integer stateId = ATMStates.CHECK_BALANCE.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<Integer>newBuilder().setError(stateError.get()).build();
    IAccount selectedAccount = atmSessionController.getSelectedAccount();
    Objects.requireNonNull(selectedAccount);
    atmStateController.nextState(stateId);
    return Response.<Integer>newBuilder()
        .setBody(selectedAccount.getBalance())
        .build();
  }

  @Override
  public synchronized IResponse<IDeposit, IError> deposit(Integer amount) {
    Integer stateId = ATMStates.DEPOSIT.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<IDeposit>newBuilder().setError(stateError.get()).build();
    IAccount selectedAccount = atmSessionController.getSelectedAccount();
    Objects.requireNonNull(selectedAccount);
    IDeposit deposit =
        Deposit.newBuilder()
            .setAccount(selectedAccount)
            .setAmount(amount)
            .setTimeStamp(System.currentTimeMillis())
            .build();
    Optional<IError> error = atmValidator.validateDeposit(deposit);
    if (error.isPresent()) return Response.<IDeposit>newBuilder().setError(error.get()).build();
    String tokenId = atmSessionController.getTokenId();
    Objects.requireNonNull(tokenId);
    IDepositRecordRequest request =
        DepositMapper.parse(DepositMapper.up(deposit), tokenId);

    CompletableFuture<RemoteResponse<IDepositRecord>> response =
        networkClientV2.deposit(
            RemoteRequest.<IDepositRecordRequest>newBuilder().setBody(request).build());
    RemoteResponse<IDepositRecord> remoteDepositResponse = ExceptionHandler.handleFuture(response);
    if (remoteDepositResponse.getError() != null) {
      return Response.<IDeposit>newBuilder()
          .setError(ErrorMapper.down(remoteDepositResponse.getError()))
          .build();
    }
    atmSessionController.updateAccount(
        AccountMapper.down(remoteDepositResponse.getBody().getAccount()));
    atmStateController.nextState(stateId);
    return Response.<IDeposit>newBuilder()
        .setBody(DepositMapper.down(remoteDepositResponse.getBody()))
        .build();
  }

  @Override
  public synchronized IResponse<IWithdraw, IError> withdraw(Integer amount) {
    Integer stateId = ATMStates.WITHDRAW.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<IWithdraw>newBuilder().setError(stateError.get()).build();
    IAccount selectedAccount = atmSessionController.getSelectedAccount();
    Objects.requireNonNull(selectedAccount);
    IWithdraw withdraw =
        Withdraw.newBuilder()
            .setAccount(selectedAccount)
            .setAmount(amount)
            .setTimeStamp(System.currentTimeMillis())
            .build();
    Optional<IError> error = atmValidator.validateWithdraw(withdraw);
    if (error.isPresent()) return Response.<IWithdraw>newBuilder().setError(error.get()).build();
    String tokenId = atmSessionController.getTokenId();
    Objects.requireNonNull(tokenId);
    IWithdrawRecordRequest request =
        WithdrawMapper.parse(WithdrawMapper.up(withdraw), tokenId);
    CompletableFuture<RemoteResponse<IWithdrawRecord>> response =
        networkClientV2.withdraw(
            RemoteRequest.<IWithdrawRecordRequest>newBuilder().setBody(request).build());

    RemoteResponse<IWithdrawRecord> remoteWithdrawResponse =
        ExceptionHandler.handleFuture(response);
    if (remoteWithdrawResponse.getError() != null) {
      return Response.<IWithdraw>newBuilder()
          .setError(ErrorMapper.down(remoteWithdrawResponse.getError()))
          .build();
    }
    atmSessionController.updateAccount(
        AccountMapper.down(remoteWithdrawResponse.getBody().getAccount()));
    atmStateController.nextState(stateId);
    return Response.<IWithdraw>newBuilder()
        .setBody(WithdrawMapper.down(remoteWithdrawResponse.getBody()))
        .build();
  }

  @Override
  public synchronized IResponse<Boolean, IError> ejectCard() {
    Integer stateId = ATMStates.ACTIVE.getId();
    Optional<IError> stateError = atmStateController.canGoToNextState(stateId);
    if (stateError.isPresent())
      return Response.<Boolean>newBuilder().setError(stateError.get()).build();
    String tokenId = atmSessionController.getTokenId();
    if (tokenId != null) {
      LogoutRecordRequest request =
          LogoutRecordRequest.newBuilder()
              .setTokenId(tokenId)
              .setTimeStamp(System.currentTimeMillis())
              .build();
      CompletableFuture<RemoteResponse<ILogoutRecord>> response =
          networkClientV2.logout(
              RemoteRequest.<ILogoutRecordRequest>newBuilder().setBody(request).build());
      RemoteResponse<ILogoutRecord> remoteLogoutResponse = ExceptionHandler.handleFuture(response);
      if (remoteLogoutResponse.getError() != null) {
        return Response.<Boolean>newBuilder()
            .setError(ErrorMapper.down(remoteLogoutResponse.getError()))
            .build();
      }
    }

    atmSessionController.clear();
    atmStateController.nextState(stateId);
    return Response.<Boolean>newBuilder().setBody(true).build();
  }

  @Override
  public Set<ATMStates> availableStates() {
    return atmStateController.availableStates();
  }

  public static class Builder implements IBuilder {
    private IATMStateController atmStateController;
    private IATMSessionController atmSessionController;
    private INetworkClientV2 networkClientV2;
    private IATMValidator atmValidator;

    private Builder() {}

    @Override
    public IBuilder setAtmStateController(IATMStateController atmStateController) {
      Objects.requireNonNull(atmStateController);
      this.atmStateController = atmStateController;
      return this;
    }

    @Override
    public IBuilder setAtmSessionController(IATMSessionController atmSessionController) {
      Objects.requireNonNull(atmSessionController);
      this.atmSessionController = atmSessionController;
      return this;
    }

    @Override
    public IBuilder setNetworkClientV2(INetworkClientV2 networkClientV2) {
      Objects.requireNonNull(networkClientV2);
      this.networkClientV2 = networkClientV2;
      return this;
    }

    @Override
    public IBuilder setAtmValidator(IATMValidator atmValidator) {
      Objects.requireNonNull(atmValidator);
      this.atmValidator = atmValidator;
      return this;
    }

    @Override
    public IATMController build() {
      return new CDATMController(
          atmStateController, atmSessionController, atmValidator, networkClientV2);
    }
  }
}
