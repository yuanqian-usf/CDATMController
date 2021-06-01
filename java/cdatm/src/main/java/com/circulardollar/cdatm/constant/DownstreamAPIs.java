package com.circulardollar.cdatm.constant;

public enum DownstreamAPIs {
    INSERT_CARD("insertCard", 2),
    VERIFY_PIN("verifyPin", 2),
    SELECT_ACCOUNT("selectAccount", 2),
    CHECK_BALANCE("checkBalance", 1),
    DEPOSIT("deposit", 2),
    WITHDRAW("withdraw", 2),
    EJECT_CARD("ejectCard", 1),
    UNSPECIFIED("", 0);

    private final String command;
    private final int argSize;

    DownstreamAPIs(String command, int argSize) {
        this.command = command;
        this.argSize = argSize;
    }

    public String getCommand() {
        return command;
    }

    public int getArgSize() {
        return argSize;
    }

    public static DownstreamAPIs ofCommand(String command) {
        for (DownstreamAPIs api: DownstreamAPIs.values()) {
            if (api.command.equalsIgnoreCase(command)) {
                return api;
            }
        }
        return UNSPECIFIED;
    }
}
