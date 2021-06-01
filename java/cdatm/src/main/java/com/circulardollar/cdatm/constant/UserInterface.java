package com.circulardollar.cdatm.constant;

public enum UserInterface {
  WELCOME("Welcome to Circular Dollar ATM controller!"),
  CMD_EXIT("q"),
  CMD_INSERT_CARD("insertCard <cardNumber>"),
  CMD_VERIFY_PIN("verifyPin <pinNumber>"),
  CMD_SELECT_ACCOUNT("selectAccount <accountNumber>"),
  CMD_CHECK_BALANCE("checkBalance"),
  CMD_DEPOSIT("deposit <amount>"),
  CMD_WITHDRAW("withdraw <amount>"),
  CMD_EJECT_CARD("ejectCard"),
  INSTRUCTION("Supported commands are listed below or "
      + CMD_EXIT.getValue() + " to quit." + System.lineSeparator()
      + CMD_INSERT_CARD.getValue() + System.lineSeparator()
      + CMD_VERIFY_PIN.getValue() + System.lineSeparator()
      + CMD_SELECT_ACCOUNT.getValue() + System.lineSeparator()
      + CMD_CHECK_BALANCE.getValue() + System.lineSeparator()
      + CMD_DEPOSIT.getValue() + System.lineSeparator()
      + CMD_WITHDRAW.getValue() + System.lineSeparator()
      + CMD_EJECT_CARD.getValue() + System.lineSeparator()
      + "*********************************************************"),
  AVAILABLE_COMMANDS("Your current available command(s): %s"  + System.lineSeparator()),
  SEE_YOU("see you!"),
  TESTING_WITH_LOCALHOST("testing with localhost"),
  CONNECT_TO_ENDPOINT("connect to endpoint: %s" + System.lineSeparator()),
  INVALID_COMMAND("Invalid command!"),
  INVALID_ARGUMENT_SIZE("Invalid argument!"),
  OPERATION_NOT_ALLOWED("Operation not allowed!");
  private final String value;

  UserInterface(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
