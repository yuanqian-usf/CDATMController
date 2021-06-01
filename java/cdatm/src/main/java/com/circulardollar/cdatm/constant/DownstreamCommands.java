package com.circulardollar.cdatm.constant;

public enum DownstreamCommands {
    UNSPECIFIED(""),
    URL("-url");

    private final String value;

    DownstreamCommands(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Not Null
     * @param value value
     * @return if no matches {@link #UNSPECIFIED} would be returned
     */
    public static DownstreamCommands ofValue(String value) {
        for (DownstreamCommands command : values()) {
            if (command.value.equals(value)) {
                return command;
            }
        }
        return UNSPECIFIED;
    }
}
