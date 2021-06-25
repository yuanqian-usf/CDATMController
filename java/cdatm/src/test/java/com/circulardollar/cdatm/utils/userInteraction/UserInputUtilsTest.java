package com.circulardollar.cdatm.utils.userInteraction;

import static com.circulardollar.cdatm.TestBase.randomBoolean;
import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomList;
import static com.circulardollar.cdatm.TestBase.randomSet;
import static com.circulardollar.cdatm.TestBase.randomString;
import static com.circulardollar.cdatm.constant.ATMStates.INSERT_CARD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.business.downstream.response.Response;
import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.constant.DownstreamAPIs;
import com.circulardollar.cdatm.constant.UserInterface;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.junit.Test;

public class UserInputUtilsTest {

  @Test(expected = NullPointerException.class)
  public void printAvailableStates_not_null_whenExceptionThrown_thenExpectationSatisfied() {
    UserInputUtils.printAvailableStates(null);
  }

  @Test
  public void printAvailableStates() {
    UserInputUtils.printAvailableStates(
        new IATMController() {
          @Override
          public IResponse<Boolean, IError> insertCard(ICard card) {
            return null;
          }

          @Override
          public IResponse<List<String>, IError> verifyPin(IPin card) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> selectAccount(String accountNumber) {
            return null;
          }

          @Override
          public IResponse<Integer, IError> checkBalance() {
            return null;
          }

          @Override
          public IResponse<IDeposit, IError> deposit(Integer amount) {
            return null;
          }

          @Override
          public IResponse<IWithdraw, IError> withdraw(Integer amount) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> ejectCard() {
            return null;
          }

          @Override
          public Set<ATMStates> availableStates() {
            return new HashSet<>(Collections.singletonList(INSERT_CARD));
          }
        });
  }

  @Test(expected = NullPointerException.class)
  public void handleUserInput_not_null_whenExceptionThrown_thenExpectationSatisfied_01() {
    UserInputUtils.handleUserInput(null, new Scanner(System.in));
  }

  @Test(expected = NullPointerException.class)
  public void handleUserInput_not_null_whenExceptionThrown_thenExpectationSatisfied_02() {
    UserInputUtils.handleUserInput(
        new IATMController() {
          @Override
          public IResponse<Boolean, IError> insertCard(ICard card) {
            return null;
          }

          @Override
          public IResponse<List<String>, IError> verifyPin(IPin card) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> selectAccount(String accountNumber) {
            return null;
          }

          @Override
          public IResponse<Integer, IError> checkBalance() {
            return null;
          }

          @Override
          public IResponse<IDeposit, IError> deposit(Integer amount) {
            return null;
          }

          @Override
          public IResponse<IWithdraw, IError> withdraw(Integer amount) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> ejectCard() {
            return null;
          }

          @Override
          public Set<ATMStates> availableStates() {
            return null;
          }
        },
        null);
  }

  @Test(expected = NullPointerException.class)
  public void handleUserInput_not_null_whenExceptionThrown_thenExpectationSatisfied_03() {
    UserInputUtils.handleUserInput(
        new IATMController() {
          @Override
          public IResponse<Boolean, IError> insertCard(ICard card) {
            return null;
          }

          @Override
          public IResponse<List<String>, IError> verifyPin(IPin card) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> selectAccount(String accountNumber) {
            return null;
          }

          @Override
          public IResponse<Integer, IError> checkBalance() {
            return null;
          }

          @Override
          public IResponse<IDeposit, IError> deposit(Integer amount) {
            return null;
          }

          @Override
          public IResponse<IWithdraw, IError> withdraw(Integer amount) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> ejectCard() {
            return null;
          }

          @Override
          public Set<ATMStates> availableStates() {
            return null;
          }
        },
        null);
  }

  @Test
  public void handleUserInput_01() {
    UserInputUtils.handleUserInput(
        new IATMController() {
          @Override
          public IResponse<Boolean, IError> insertCard(ICard card) {
            return null;
          }

          @Override
          public IResponse<List<String>, IError> verifyPin(IPin card) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> selectAccount(String accountNumber) {
            return null;
          }

          @Override
          public IResponse<Integer, IError> checkBalance() {
            return null;
          }

          @Override
          public IResponse<IDeposit, IError> deposit(Integer amount) {
            return null;
          }

          @Override
          public IResponse<IWithdraw, IError> withdraw(Integer amount) {
            return null;
          }

          @Override
          public IResponse<Boolean, IError> ejectCard() {
            return null;
          }

          @Override
          public Set<ATMStates> availableStates() {
            return randomSet();
          }
        },
        new Scanner(System.in));
  }

  @Test
  public void handleUserInput_02() {
    IATMController controller = mock(IATMController.class);
    String input = UserInterface.EXIT.getValue();
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_03() {
    IATMController controller = mock(IATMController.class);
    String input = "";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_04() {
    IATMController controller = mock(IATMController.class);
    String input = UserInterface.EXIT.getValue();
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_05() {
    IATMController controller = mock(IATMController.class);
    when(controller.insertCard(any(ICard.class)))
        .thenReturn(Response.<Boolean>newBuilder().setBody(true).build());
    String input =
        String.join(" ", Collections.singletonList(DownstreamAPIs.INSERT_CARD.getCommand()));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_insertCard() {
    IATMController controller = mock(IATMController.class);
    when(controller.insertCard(any()))
        .thenReturn(Response.<Boolean>newBuilder().setBody(randomBoolean()).build());
    String value = "1234";
    String input = String.join(" ", Arrays.asList(DownstreamAPIs.INSERT_CARD.getCommand(), value));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, times(1)).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_verifyPin() {
    IATMController controller = mock(IATMController.class);
    when(controller.verifyPin(any()))
        .thenReturn(Response.<List<String>>newBuilder().setBody(randomList()).build());
    String value = "1234";
    String input = String.join(" ", Arrays.asList(DownstreamAPIs.VERIFY_PIN.getCommand(), value));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, times(1)).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
  }

  @Test
  public void handleUserInput_selectAccount() {
    IATMController controller = mock(IATMController.class);
    when(controller.selectAccount(any()))
        .thenReturn(Response.<Boolean>newBuilder().setBody(randomBoolean()).build());
    String value = "1234";
    String input = String.join(" ", Arrays.asList(DownstreamAPIs.SELECT_ACCOUNT.getCommand(), value));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, times(1)).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
  }

  @Test
  public void handleUserInput_checkBalance() {
    IATMController controller = mock(IATMController.class);
    when(controller.checkBalance())
        .thenReturn(Response.<Integer>newBuilder().setBody(randomInt()).build());
    String input = String.join(" ",
        Collections.singletonList(DownstreamAPIs.CHECK_BALANCE.getCommand()));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, times(1)).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }


  @Test
  public void handleUserInput_deposit_shortcut() {
    IATMController controller = mock(IATMController.class);
    when(controller.deposit(any()))
        .thenReturn(Response.<IDeposit>newBuilder().setBody(new IDeposit() {
          @Override
          public IAccount getAccount() {
            return null;
          }

          @Override
          public Integer getAmount() {
            return null;
          }

          @Override
          public Long getTimeStamp() {
            return null;
          }
        }).build());
    String value = "1234";
    String input = String.join(" ", Arrays.asList(DownstreamAPIs.DEPOSIT.getShortcut(), value));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, times(1)).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_deposit() {
    IATMController controller = mock(IATMController.class);
    when(controller.deposit(any()))
        .thenReturn(Response.<IDeposit>newBuilder().setBody(new IDeposit() {
          @Override
          public IAccount getAccount() {
            return null;
          }

          @Override
          public Integer getAmount() {
            return null;
          }

          @Override
          public Long getTimeStamp() {
            return null;
          }
        }).build());
    String value = "1234";
    String input = String.join(" ", Arrays.asList(DownstreamAPIs.DEPOSIT.getCommand(), value));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, times(1)).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test
  public void handleUserInput_withdraw() {
    IATMController controller = mock(IATMController.class);
    when(controller.withdraw(any()))
        .thenReturn(Response.<IWithdraw>newBuilder().setBody(new IWithdraw() {
          @Override
          public IAccount getAccount() {
            return null;
          }

          @Override
          public Integer getAmount() {
            return null;
          }

          @Override
          public Long getTimeStamp() {
            return null;
          }
        }).build());
    String value = "1234";
    String input = String.join(" ", Arrays.asList(DownstreamAPIs.WITHDRAW.getCommand(), value));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, times(1)).withdraw(any());
    verify(controller, never()).ejectCard();
  }


  @Test
  public void handleUserInput_ejectCard() {
    IATMController controller = mock(IATMController.class);
    when(controller.ejectCard())
        .thenReturn(Response.<Boolean>newBuilder().setBody(randomBoolean()).build());
    String input = String.join(" ",
        Collections.singletonList(DownstreamAPIs.EJECT_CARD.getCommand()));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, times(1)).ejectCard();
  }

  @Test
  public void handleUserInput_unknown() {
    IATMController controller = mock(IATMController.class);
    String input = String.join(" ",
        Collections.singletonList("Unknown"));
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    Scanner scanner = new Scanner(in);
    UserInputUtils.handleUserInput(controller, scanner);
    verify(controller, never()).insertCard(any());
    verify(controller, never()).verifyPin(any());
    verify(controller, never()).selectAccount(any());
    verify(controller, never()).checkBalance();
    verify(controller, never()).deposit(any());
    verify(controller, never()).withdraw(any());
    verify(controller, never()).ejectCard();
  }

  @Test public void handleCommand_01() {
    IError mockError = mock(IError.class);
    UserInputUtils.handleCommand(new IResponse<Object, IError>() {
      @Override
      public Object getBody() {
        return null;
      }

      @Override
      public IError getError() {
        return mockError;
      }
    });
    verify(mockError, times(1)).getErrorMessages();
  }


  @Test public void handleCommand_02() {
    IResponse<Boolean, IError> mockResponse = mock(IResponse.class);
    UserInputUtils.handleCommand(mockResponse);
    verify(mockResponse, times(1)).getBody();
  }
}
