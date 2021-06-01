package com.circulardollar.cdatm.constant;

public enum APIVersions {
    UNSPECIFIED(0),
    V1(1),
    V2(2);

    private final int version;
    APIVersions(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    APIVersions ofVersion(Integer version) {
        for (APIVersions apiVersions : values()) {
            if (apiVersions.getVersion() == version) {
                return apiVersions;
            }
        }
        return UNSPECIFIED;
    }
}
