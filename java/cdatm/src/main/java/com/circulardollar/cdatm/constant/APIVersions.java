package com.circulardollar.cdatm.constant;

public enum APIVersions {
    UNSPECIFIED(0), V1(1), V2(2);

    private final Integer version;

    APIVersions(Integer version) {
        this.version = version;
    }

    public static APIVersions ofVersion(Integer version) {
        for (APIVersions apiVersions : values()) {
            if (apiVersions.getVersion().equals(version)) {
                return apiVersions;
            }
        }
        return UNSPECIFIED;
    }

    public Integer getVersion() {
        return version;
    }
}
