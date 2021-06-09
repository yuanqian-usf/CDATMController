package com.circulardollar.cdatm.utils.userInteraction;

import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.constant.DownstreamAPIs;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.state.StateMapper;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class UserInputUtils {

  public static final String DELIMITER = ",";
  public static final String REGEX_SPACES = "\\s+";

  private UserInputUtils() {}

  static void printAvailableStates(IATMController controller) {
    Objects.requireNonNull(controller);
    Objects.requireNonNull(controller.availableStates());
    System.out.printf(
        UserInterface.AVAILABLE_COMMANDS.getValue(),
        controller.availableStates().stream()
            .map(state -> StateMapper.map(state).getCommand())
            .collect(Collectors.joining(DELIMITER)));
  }

  public static void handleUserInput(IATMController controller, Scanner scanner) {
    Objects.requireNonNull(controller);
    Objects.requireNonNull(scanner);
    printAvailableStates(controller);
    while (scanner.hasNextLine()) {
      String userInput = scanner.nextLine();
      if (UserInterface.EXIT.getValue().equals(userInput)) break;
      String[] inputArr = userInput.split(REGEX_SPACES);
      DownstreamAPIs api = DownstreamAPIs.ofShortcut(inputArr[0]);
      if (DownstreamAPIs.UNSPECIFIED == api) api = DownstreamAPIs.ofCommand(inputArr[0]);
      if (inputArr.length < api.getArgSize()) {
        System.out.println(UserInterface.INVALID_ARGUMENT_SIZE.getValue());
      } else {
        switch (api) {
          case INSERT_CARD:
            handleCommand(
                controller.insertCard(
                    Card.newBuilder()
                        .setCardNumber(inputArr[1])
                        .setCvc("")
                        .setExpirationDate("")
                        .setHolderName("")
                        .build()));
            break;
          case VERIFY_PIN:
            handleCommand(
                controller.verifyPin(Pin.newBuilder().setPinNumber(inputArr[1].trim()).build()));
            break;
          case SELECT_ACCOUNT:
            handleCommand(controller.selectAccount(inputArr[1]));
            break;
          case CHECK_BALANCE:
            handleCommand(controller.checkBalance());
            break;
          case DEPOSIT:
            handleCommand(controller.deposit(Integer.valueOf(inputArr[1])));
            break;
          case WITHDRAW:
            handleCommand(controller.withdraw(Integer.valueOf(inputArr[1])));
            break;
          case EJECT_CARD:
            handleCommand(controller.ejectCard());
            break;
          default:
            System.out.println(UserInterface.INVALID_COMMAND.getValue());
            break;
        }
        printAvailableStates(controller);
      }
    }
    System.out.println(UserInterface.SEE_YOU.getValue());
  }

  static <T> void handleCommand(IResponse<T, IError> response) {
    Objects.requireNonNull(response);
    Optional.ofNullable(response.getBody()).ifPresent(System.out::println);
    Optional.ofNullable(response.getError())
        .ifPresent(iError -> iError.getErrorMessages().forEach(System.out::println));
  }
}
