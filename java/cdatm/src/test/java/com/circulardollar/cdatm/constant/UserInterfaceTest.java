package com.circulardollar.cdatm.constant;

import org.junit.Test;

import static com.circulardollar.cdatm.constant.UserInterface.*;
import static org.junit.Assert.*;

public class UserInterfaceTest {

    @Test public void getValue() {
        assertEquals(UserInterface.WELCOME.getValue(), "Welcome to Circular Dollar ATM controller!");
        assertEquals(UserInterface.CMD_EXIT.getValue(), "q");
        assertEquals(UserInterface.CMD_INSERT_CARD.getValue(), "insertCard <cardNumber>");
        assertEquals(UserInterface.CMD_VERIFY_PIN.getValue(), "verifyPin <pinNumber>");
        assertEquals(UserInterface.CMD_SELECT_ACCOUNT.getValue(), "selectAccount <accountNumber>");
        assertEquals(UserInterface.CMD_CHECK_BALANCE.getValue(), "checkBalance");
        assertEquals(CMD_DEPOSIT.getValue(), "deposit <amount>");
        assertEquals(CMD_WITHDRAW.getValue(), "withdraw <amount>");
        assertEquals(CMD_EJECT_CARD.getValue(), "ejectCard");
        assertEquals(UserInterface.INSTRUCTION.getValue(), "Supported commands are listed below or "
            + CMD_EXIT.getValue() + " to quit." + System.lineSeparator()
            + CMD_INSERT_CARD.getValue() + System.lineSeparator()
            + CMD_VERIFY_PIN.getValue() + System.lineSeparator()
            + CMD_SELECT_ACCOUNT.getValue() + System.lineSeparator()
            + CMD_CHECK_BALANCE.getValue() + System.lineSeparator()
            + CMD_DEPOSIT.getValue() + System.lineSeparator()
            + CMD_WITHDRAW.getValue() + System.lineSeparator()
            + CMD_EJECT_CARD.getValue() + System.lineSeparator()
            + "*********************************************************");
        assertEquals(UserInterface.AVAILABLE_COMMANDS.getValue(), "Your current available command(s): %s"  + System.lineSeparator());
        assertEquals(UserInterface.SEE_YOU.getValue(), "see you!");
        assertEquals(UserInterface.TESTING_WITH_LOCALHOST.getValue(), "testing with localhost");
        assertEquals(UserInterface.CONNECT_TO_ENDPOINT.getValue(), "connect to endpoint: %s" + System.lineSeparator());
        assertEquals(UserInterface.INVALID_COMMAND.getValue(), "Invalid command!");
        assertEquals(UserInterface.INVALID_ARGUMENT_SIZE.getValue(), "Invalid argument!");
        assertEquals(UserInterface.OPERATION_NOT_ALLOWED.getValue(), "Operation not allowed!");

    }
}
