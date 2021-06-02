package com.circulardollar.cdatm;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static com.circulardollar.cdatm.TestBase.validRandomCardNumber;
import static com.circulardollar.cdatm.TestBase.validRandomPinNumber;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.AuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.error.ErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.business.upstream.response.RemoteResponse;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.constant.APIVersions;
import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.session.IATMSessionController;
import com.circulardollar.cdatm.state.IATMStateController;
import com.circulardollar.cdatm.validator.IATMValidator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;

public class CDATMControllerTest {

  @Test
  public void newBuilder() {
    IATMController cdatmController =
        CDATMController.newBuilder()
            .setAtmStateController(mock(IATMStateController.class))
            .setAtmSessionController(mock(IATMSessionController.class))
            .setAtmValidator(mock(IATMValidator.class))
            .setNetworkClientV2(mock(INetworkClientV2.class))
            .build();
    assertNotNull(cdatmController);
  }

  @Test(expected = NullPointerException.class)
  public void
  CDATMController_atmStateController_null_whenExceptionThrown_thenExpectationSatisfied() {
    new CDATMController(
        null,
        mock(IATMSessionController.class),
        mock(IATMValidator.class),
        mock(INetworkClientV2.class));
  }

  @Test(expected = NullPointerException.class)
  public void
  CDATMController_atmSessionController_null_whenExceptionThrown_thenExpectationSatisfied() {
    new CDATMController(
        mock(IATMStateController.class),
        null,
        mock(IATMValidator.class),
        mock(INetworkClientV2.class));
  }

  @Test(expected = NullPointerException.class)
  public void CDATMController_atmValidator_null_whenExceptionThrown_thenExpectationSatisfied() {
    new CDATMController(
        mock(IATMStateController.class),
        mock(IATMSessionController.class),
        null,
        mock(INetworkClientV2.class));
  }

  @Test(expected = NullPointerException.class)
  public void CDATMController_networkClientV2_null_whenExceptionThrown_thenExpectationSatisfied() {
    new CDATMController(
        mock(IATMStateController.class),
        mock(IATMSessionController.class),
        mock(IATMValidator.class),
        null);
  }

  @Test(expected = NullPointerException.class)
  public void builder_atmStateController_null_whenExceptionThrown_thenExpectationSatisfied() {
    CDATMController.newBuilder().setAtmStateController(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void builder_atmSessionController_null_whenExceptionThrown_thenExpectationSatisfied() {
    CDATMController.newBuilder().setAtmSessionController(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void builder_atmValidator_null_whenExceptionThrown_thenExpectationSatisfied() {
    CDATMController.newBuilder().setAtmValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void builder_networkClientV2_null_whenExceptionThrown_thenExpectationSatisfied_02() {
    CDATMController.newBuilder().setNetworkClientV2(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void validate_StateController() {
    IATMValidator iAtmValidator = mock(IATMValidator.class);
    IError iError = mock(IError.class);
    when(iAtmValidator.validateStateController(any())).thenReturn(Optional.ofNullable(iError));
    CDATMController.newBuilder()
        .setAtmStateController(mock(IATMStateController.class))
        .setAtmSessionController(mock(IATMSessionController.class))
        .setAtmValidator(iAtmValidator)
        .setNetworkClientV2(mock(INetworkClientV2.class))
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void validate_SessionController() {
    IATMValidator iAtmValidator = mock(IATMValidator.class);
    IError iError = mock(IError.class);
    when(iAtmValidator.validateSessionController(any())).thenReturn(Optional.ofNullable(iError));
    CDATMController.newBuilder()
        .setAtmStateController(mock(IATMStateController.class))
        .setAtmSessionController(mock(IATMSessionController.class))
        .setAtmValidator(iAtmValidator)
        .setNetworkClientV2(mock(INetworkClientV2.class))
        .build();
  }

  @Test
  public void insertCard_state_error() {
    Card card =
        Card.newBuilder()
            .setCardNumber(validRandomCardNumber())
            .setHolderName(randomString())
            .setExpirationDate(randomString())
            .setCvc(randomString())
            .build();

    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNotNull(cdatmController.insertCard(card).getError());
  }

  @Test
  public void insertCard_state_no_error_card_error() {
    Card card =
        Card.newBuilder()
            .setCardNumber(validRandomCardNumber())
            .setHolderName(randomString())
            .setExpirationDate(randomString())
            .setCvc(randomString())
            .build();

    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt())).thenReturn(Optional.empty());

    when(atmValidator.validateCard(card)).thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNull(cdatmController.insertCard(card).getBody());

    assertTrue(atmValidator.validateCard(card).isPresent());
  }

  @Test
  public void insertCard_state_no_error_card_no_error() {
    Card card =
        Card.newBuilder()
            .setCardNumber(validRandomCardNumber())
            .setHolderName(randomString())
            .setExpirationDate(randomString())
            .setCvc(randomString())
            .build();

    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt())).thenReturn(Optional.empty());

    when(atmValidator.validateCard(card)).thenReturn(Optional.empty());

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNotNull(cdatmController.insertCard(card).getBody());

    assertFalse(atmValidator.validateCard(card).isPresent());
  }

  @Test
  public void verifyPin_state_error() {
    Pin pin = Pin.newBuilder().setPinNumber(validRandomPinNumber()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    IATMConfigurations iatmConfigurations = mock(IATMConfigurations.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));
    when(atmValidator.getATMConfigurations()).thenReturn(iatmConfigurations);
    when(iatmConfigurations.getAPIVersion()).thenReturn(APIVersions.V1);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNotNull(cdatmController.verifyPin(pin).getError());
  }

  @Test(expected = NullPointerException.class)
  public void verifyPin_state_no_error_card_no_error_card_null() {
    Pin pin = Pin.newBuilder().setPinNumber(validRandomPinNumber()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    IATMConfigurations iatmConfigurations = mock(IATMConfigurations.class);

    when(atmStateController.canGoToNextState(anyInt())).thenReturn(Optional.empty());
    when(atmValidator.getATMConfigurations()).thenReturn(iatmConfigurations);
    when(iatmConfigurations.getAPIVersion()).thenReturn(APIVersions.V1);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNull(cdatmController.verifyPin(pin).getError());

  }

  @Test
  public void verifyPin_state_no_error_card_no_error_card_not_null_pin_error() {
    Pin pin = Pin.newBuilder().setPinNumber(validRandomPinNumber()).build();
    ICard iCard =
        Card.newBuilder()
            .setCardNumber(validRandomCardNumber())
            .setHolderName(randomString())
            .setCvc(randomString())
            .setExpirationDate(randomString())
            .build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    IATMConfigurations iatmConfigurations = mock(IATMConfigurations.class);

    when(atmStateController.canGoToNextState(anyInt())).thenReturn(Optional.empty());
    when(atmValidator.getATMConfigurations()).thenReturn(iatmConfigurations);
    when(iatmConfigurations.getAPIVersion()).thenReturn(APIVersions.V1);
    when(atmSessionController.getCard()).thenReturn(iCard);
    when(atmValidator.validatePin(any())).thenReturn(Optional.of(mock(IError.class)));
    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNotNull(cdatmController.verifyPin(pin).getError());

    verify(atmValidator, times(1)).validatePin(pin);
    verify(networkClientV2, never()).verifyPin(any());
    verify(atmSessionController, never()).setAccounts(anyString(), anyMap());
    verify(atmStateController, never()).nextState(any());
  }

  @Test
  public void verifyPin_state_no_error_card_no_error_card_not_null_pin_no_error_remote_error() {
    Pin pin = Pin.newBuilder().setPinNumber(validRandomPinNumber()).build();
    ICard iCard =
        Card.newBuilder()
            .setCardNumber(validRandomCardNumber())
            .setHolderName(randomString())
            .setCvc(randomString())
            .setExpirationDate(randomString())
            .build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    IATMConfigurations iatmConfigurations = mock(IATMConfigurations.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);

    when(atmStateController.canGoToNextState(anyInt())).thenReturn(Optional.empty());
    when(atmValidator.getATMConfigurations()).thenReturn(iatmConfigurations);
    when(iatmConfigurations.getAPIVersion()).thenReturn(APIVersions.V1);
    when(atmSessionController.getCard()).thenReturn(iCard);
    when(atmValidator.validatePin(pin)).thenReturn(Optional.empty());
    when(networkClientV2.verifyPin(any()))
        .thenReturn(CompletableFuture.completedFuture(remoteResponse));
    when(remoteResponse.getError())
        .thenReturn(
            ErrorRecord.newBuilder()
                .setErrorCode(randomInt())
                .setErrorMessages(randomList())
                .build());
    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();
    IResponse<List<String>, IError> result = cdatmController.verifyPin(pin);
    assertNull(result.getBody());
    assertNotNull(result.getError());

    verify(atmValidator, times(1)).validatePin(pin);
    verify(networkClientV2, times(1)).verifyPin(any());
    verify(atmSessionController, never()).setAccounts(anyString(), anyMap());
    verify(atmStateController, never()).nextState(any());
  }

  @Test
  public void verifyPin_state_no_error_card_no_error_card_not_null_pin_no_error_remote_no_error() {
    Pin pin = Pin.newBuilder().setPinNumber(validRandomPinNumber()).build();
    ICard iCard =
        Card.newBuilder()
            .setCardNumber(validRandomCardNumber())
            .setHolderName(randomString())
            .setCvc(randomString())
            .setExpirationDate(randomString())
            .build();
    String tokenId = randomString();
    AccountRecord accountRecord =
        AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build();
    List<IAccountRecord> accountRecords = Collections.singletonList(accountRecord);

    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    IATMConfigurations iatmConfigurations = mock(IATMConfigurations.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);
    IAuthRecord authRecord =
        AuthRecord.newBuilder()
            .setAccounts(accountRecords)
            .setTokenId(tokenId)
            .setTimeStamp(randomLong())
            .build();

    when(atmStateController.canGoToNextState(anyInt())).thenReturn(Optional.empty());
    when(atmValidator.getATMConfigurations()).thenReturn(iatmConfigurations);
    when(iatmConfigurations.getAPIVersion()).thenReturn(APIVersions.V1);
    when(atmSessionController.getCard()).thenReturn(iCard);
    when(atmValidator.validatePin(pin)).thenReturn(Optional.empty());
    when(networkClientV2.verifyPin(any()))
        .thenReturn(CompletableFuture.completedFuture(remoteResponse));
    when(remoteResponse.getBody()).thenReturn(authRecord);
    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<List<String>, IError> result = cdatmController.verifyPin(pin);
    assertNull(result.getError());
    assertNotNull(result.getBody());
    verify(atmValidator, times(1)).validatePin(pin);
    verify(networkClientV2, times(1)).verifyPin(any());
    verify(atmSessionController, times(1)).setAccounts(anyString(), anyMap());
    verify(atmStateController, times(1)).nextState(ATMStates.VERIFY_PIN.getId());
  }

  @Test
  public void selectAccount_state_error() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> result = cdatmController
        .selectAccount(randomString());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, never()).getAccounts();
    verify(atmSessionController, never()).setSelectedAccountNumber(anyString());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void selectAccount_state_no_error_null_getAccounts() {
    Map<String, IAccount> accountMap = new HashMap<>();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getAccounts()).thenReturn(accountMap);
    when(atmValidator.validateAccount(any())).thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> result = cdatmController
        .selectAccount(randomString());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getAccounts();
    verify(atmSessionController, never()).setSelectedAccountNumber(anyString());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void selectAccount_state_no_error_not_null_getAccounts_error_selectAccount() {
    Map<String, IAccount> accountMap = new HashMap<>();
    accountMap.put(randomString(),
        Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getAccounts()).thenReturn(accountMap);
    when(atmValidator.validateAccount(any())).thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> result = cdatmController
        .selectAccount(randomString());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getAccounts();
    verify(atmSessionController, never()).setSelectedAccountNumber(anyString());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void selectAccount_state_no_error_not_null_getAccounts_no_error_selectAccount() {
    String selectedAccountNumber = randomString();
    Map<String, IAccount> accountMap = new HashMap<>();
    accountMap.put(selectedAccountNumber,
        Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getAccounts()).thenReturn(accountMap);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> result = cdatmController
        .selectAccount(selectedAccountNumber);
    assertNull(result.getError());
    assertNotNull(result.getBody());
    assertTrue(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getAccounts();
    verify(atmSessionController, times(1)).setSelectedAccountNumber(anyString());
    verify(atmStateController, times(1)).nextState(anyInt());
  }

  @Test
  public void checkBalance_state_error() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Integer, IError> result = cdatmController
        .checkBalance();
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, never()).getSelectedAccount();
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test(expected = NullPointerException.class)
  public void checkBalance_state_no_error_selected_account_null() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(null);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Integer, IError> result = cdatmController
        .checkBalance();
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, never()).getSelectedAccount();
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void checkBalance_state_error_selected_account_not_null() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Integer, IError> result = cdatmController
        .checkBalance();
    assertNull(result.getError());
    assertNotNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmStateController, times(1)).nextState(anyInt());
  }

  @Test
  public void deposit_state_error() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IDeposit, IError> result = cdatmController
        .deposit(randomInt());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, never()).getSelectedAccount();
    verify(atmValidator, never()).validateDeposit(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).deposit(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test(expected = NullPointerException.class)
  public void deposit_state_no_error_selected_account_null() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(null);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IDeposit, IError> result = cdatmController
        .deposit(randomInt());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, never()).validateDeposit(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).deposit(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void deposit_state_no_error_selected_account_not_null_error_validate_deposit() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateDeposit(any())).thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IDeposit, IError> result = cdatmController
        .deposit(randomInt());

    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateDeposit(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).deposit(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test(expected = NullPointerException.class)
  public void deposit_state_no_error_selected_account_not_null_no_error_validate_deposit_token_null() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateDeposit(any())).thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(null);
    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IDeposit, IError> result = cdatmController
        .deposit(randomInt());

    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateDeposit(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, never()).deposit(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void deposit_state_no_error_selected_account_not_null_error_validate_deposit_token_not_null_error_remote_response() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateDeposit(any())).thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(randomString());
    when(networkClientV2.deposit(any()))
        .thenReturn(CompletableFuture.completedFuture(remoteResponse));
    when(remoteResponse.getError()).thenReturn(
        ErrorRecord.newBuilder()
            .setErrorCode(randomInt())
            .setErrorMessages(randomList())
            .build());

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IDeposit, IError> result = cdatmController
        .deposit(randomInt());

    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateDeposit(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, times(1)).deposit(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void deposit_state_no_error_selected_account_not_null_error_validate_deposit_token_not_null_no_error_remote_response() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);
    IDepositRecord iDepositRecord = DepositRecord.newBuilder().setAccount(
        AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
        .setAmount(randomInt()).setTimeStamp(randomLong()).build();

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateDeposit(any())).thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(randomString());
    when(networkClientV2.deposit(any()))
        .thenReturn(CompletableFuture.completedFuture(remoteResponse));
    when(remoteResponse.getBody()).thenReturn(iDepositRecord);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IDeposit, IError> result = cdatmController
        .deposit(randomInt());

    assertNull(result.getError());
    assertNotNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateDeposit(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, times(1)).deposit(any());
    verify(atmSessionController,times(1)).updateAccount(any());
    verify(atmStateController, times(1)).nextState(anyInt());
  }

  @Test
  public void withdraw_state_error() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IWithdraw, IError> result = cdatmController
        .withdraw(randomInt());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, never()).getSelectedAccount();
    verify(atmValidator, never()).validateWithdraw(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).withdraw(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test(expected = NullPointerException.class)
  public void withdraw_state_no_error_selected_account_null() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(null);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IWithdraw, IError> result = cdatmController
        .withdraw(randomInt());
    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, never()).validateWithdraw(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).withdraw(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void withdraw_state_no_error_selected_account_not_null_error_validate_withdraw() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateWithdraw(any())).thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IWithdraw, IError> result = cdatmController
        .withdraw(randomInt());

    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateWithdraw(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).withdraw(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test(expected = NullPointerException.class)
  public void withdraw_state_no_error_selected_account_not_null_no_error_validate_withdraw_token_null() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateWithdraw(any())).thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(null);
    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IWithdraw, IError> result = cdatmController
        .withdraw(randomInt());

    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateWithdraw(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, never()).withdraw(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void withdraw_state_no_error_selected_account_not_null_error_validate_withdraw_token_not_null_error_remote_response() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateWithdraw(any())).thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(randomString());
    when(networkClientV2.withdraw(any()))
        .thenReturn(CompletableFuture.completedFuture(remoteResponse));
    when(remoteResponse.getError()).thenReturn(
        ErrorRecord.newBuilder()
            .setErrorCode(randomInt())
            .setErrorMessages(randomList())
            .build());

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IWithdraw, IError> result = cdatmController
        .withdraw(randomInt());

    assertNotNull(result.getError());
    assertNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateWithdraw(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, times(1)).withdraw(any());
    verify(atmSessionController, never()).updateAccount(any());
    verify(atmStateController, never()).nextState(anyInt());
  }

  @Test
  public void withdraw_state_no_error_selected_account_not_null_error_validate_withdraw_token_not_null_no_error_remote_response() {
    IAccount iAccount = Account.newBuilder().setAccountNumber(randomString())
        .setBalance(randomInt()).build();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);
    IWithdrawRecord iWithdrawRecord = WithdrawRecord.newBuilder().setAccount(
        AccountRecord.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build())
        .setAmount(randomInt()).setTimeStamp(randomLong()).build();

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getSelectedAccount()).thenReturn(iAccount);
    when(atmValidator.validateWithdraw(any())).thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(randomString());
    when(networkClientV2.withdraw(any()))
        .thenReturn(CompletableFuture.completedFuture(remoteResponse));
    when(remoteResponse.getBody()).thenReturn(iWithdrawRecord);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<IWithdraw, IError> result = cdatmController
        .withdraw(randomInt());

    assertNull(result.getError());
    assertNotNull(result.getBody());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getSelectedAccount();
    verify(atmValidator, times(1)).validateWithdraw(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, times(1)).withdraw(any());
    verify(atmSessionController,times(1)).updateAccount(any());
    verify(atmStateController, times(1)).nextState(anyInt());
  }

  @Test
  public void ejectCard_state_error() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.of(mock(IError.class)));

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> response = cdatmController.ejectCard();
    assertNotNull(response.getError());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, never()).getTokenId();
    verify(networkClientV2, never()).logout(any());
    verify(atmSessionController, never()).clear();
    verify(atmStateController, never()).nextState(any());
  }

  @Test
  public void ejectCard_state_no_error_tokenId_null() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(null);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> response = cdatmController.ejectCard();
    assertNull(response.getError());
    assertNotNull(response.getBody());

    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, never()).logout(any());
    verify(atmSessionController, times(1)).clear();
    verify(atmStateController, times(1)).nextState(any());
  }

  @Test
  public void ejectCard_state_no_error_tokenId_not_null_logout_error() {
    String tokenId = randomString();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(tokenId);
    when(networkClientV2.logout(any())).thenReturn(CompletableFuture.completedFuture(remoteResponse));

    when(remoteResponse.getError()).thenReturn(
        ErrorRecord.newBuilder()
            .setErrorCode(randomInt())
            .setErrorMessages(randomList())
            .build());

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> response = cdatmController.ejectCard();
    assertNotNull(response.getError());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, times(1)).logout(any());
    verify(atmSessionController, never()).clear();
    verify(atmStateController, never()).nextState(any());
  }

  @Test
  public void ejectCard_state_no_error_tokenId_not_null_logout_no_error() {
    String tokenId = randomString();
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);
    RemoteResponse remoteResponse = mock(RemoteResponse.class);

    when(atmStateController.canGoToNextState(anyInt()))
        .thenReturn(Optional.empty());
    when(atmSessionController.getTokenId()).thenReturn(tokenId);
    when(networkClientV2.logout(any())).thenReturn(CompletableFuture.completedFuture(remoteResponse));

    when(remoteResponse.getError()).thenReturn(null);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    IResponse<Boolean, IError> response = cdatmController.ejectCard();
    assertNull(response.getError());
    verify(atmStateController, times(1)).canGoToNextState(any());
    verify(atmSessionController, times(1)).getTokenId();
    verify(networkClientV2, times(1)).logout(any());
    verify(atmSessionController, times(1)).clear();
    verify(atmStateController, times(1)).nextState(any());
  }

  @Test
  public void availableStates() {
    IATMStateController atmStateController = mock(IATMStateController.class);
    IATMSessionController atmSessionController = mock(IATMSessionController.class);
    IATMValidator atmValidator = mock(IATMValidator.class);
    INetworkClientV2 networkClientV2 = mock(INetworkClientV2.class);

    CDATMController cdatmController =
        (CDATMController)
            CDATMController.newBuilder()
                .setAtmStateController(atmStateController)
                .setAtmSessionController(atmSessionController)
                .setAtmValidator(atmValidator)
                .setNetworkClientV2(networkClientV2)
                .build();

    assertNotNull(cdatmController.availableStates());

  }
}
