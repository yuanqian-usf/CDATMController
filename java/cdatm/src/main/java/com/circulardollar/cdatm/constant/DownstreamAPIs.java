package com.circulardollar.cdatm.constant;

public enum DownstreamAPIs {
    INSERT_CARD("insertCard", 2, "i"),
    VERIFY_PIN("verifyPin", 2, "v"),
    SELECT_ACCOUNT("selectAccount", 2, "s"),
    CHECK_BALANCE("checkBalance", 1, "c"),
    DEPOSIT("deposit", 2, "d"),
    WITHDRAW("withdraw", 2, "w"),
    EJECT_CARD("ejectCard", 1, "e"),
    UNSPECIFIED("", 0, "");

    private final String command;
    private final int argSize;
    private final String shortcut;

    DownstreamAPIs(String command, int argSize, String shortcut) {
        this.command = command;
        this.argSize = argSize;
        this.shortcut = shortcut;
    }

    public String getCommand() {
        return command;
    }

    public String getShortcut() {
        return shortcut;
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

    public static DownstreamAPIs ofShortcut(String shortcut) {
        for (DownstreamAPIs api: DownstreamAPIs.values()) {
            if (api.shortcut.equalsIgnoreCase(shortcut)) {
                return api;
            }
        }
        return UNSPECIFIED;
    }
}
