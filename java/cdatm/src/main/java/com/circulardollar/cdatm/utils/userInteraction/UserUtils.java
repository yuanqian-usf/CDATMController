package com.circulardollar.cdatm.utils.userInteraction;

import com.circulardollar.cdatm.CDATMController;
import com.circulardollar.cdatm.IATMController;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.downstream.response.IResponse;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.constant.DownstreamAPIs;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.session.ATMSessionController;
import com.circulardollar.cdatm.state.ATMStateController;
import com.circulardollar.cdatm.state.StateMapper;
import com.circulardollar.cdatm.validator.ATMValidator;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserUtils {

    public static final String DELIMITER = ",";
    public static final String REGEX_SPACES = "\\s+";

    private static void printAvailableInput(IATMController controller) {
        System.out.printf(UserInterface.AVAILABLE_COMMANDS.getValue(),
            controller.availableStates().stream().map(state -> StateMapper.map(state).getCommand())
                .collect(Collectors.joining(DELIMITER)));
    }

    public static void handleUserInput(IATMController controller, Scanner scanner) {
        printAvailableInput(controller);
        String userInput = null;
        while (!UserInterface.CMD_EXIT.getValue().equalsIgnoreCase(userInput) && scanner
            .hasNextLine()) {
            userInput = scanner.nextLine();
            if (UserInterface.CMD_EXIT.getValue().equalsIgnoreCase(userInput))
                break;
            String[] inputArr = userInput.split(REGEX_SPACES);
            DownstreamAPIs api = DownstreamAPIs.ofCommand(inputArr[0]);
            if (inputArr.length < api.getArgSize()) {
                System.out.println(UserInterface.INVALID_ARGUMENT_SIZE.getValue());
            } else {
                switch (api) {
                    case INSERT_CARD:
                        handleCommand(controller.insertCard(
                            Card.newBuilder().setCardNumber(inputArr[1]).setCvc("")
                                .setExpirationDate("").setHolderName("").build()));
                        break;
                    case VERIFY_PIN:
                        handleCommand(controller
                            .verifyPin(Pin.newBuilder().setPinNumber(inputArr[1].trim()).build()));
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
                    case UNSPECIFIED:
                        System.out.println(UserInterface.INVALID_COMMAND.getValue());
                        break;
                }
                printAvailableInput(controller);
            }
        }
        System.out.println(UserInterface.SEE_YOU.getValue());
    }

    private static <T> void handleCommand(IResponse<T, IError> response) {
        Optional.ofNullable(response.getBody()).ifPresent(System.out::println);
        Optional.ofNullable(response.getError())
            .ifPresent(iError -> iError.getErrorMessages().forEach(System.out::println));
    }

    public static IATMController createATMController(
        INetworkClientV2.IBuilder networkClientBuilder) {
        return createATMControllerBuilder(networkClientBuilder).build();
    }

    public static IATMController.IBuilder createATMControllerBuilder(
        INetworkClientV2.IBuilder networkClientBuilder) {
        return CDATMController.newBuilder().setAtmStateController(new ATMStateController())
            .setAtmSessionController(new ATMSessionController())
            .setNetworkClientV2(networkClientBuilder.build()).setAtmValidator(
                ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build());
    }
}
