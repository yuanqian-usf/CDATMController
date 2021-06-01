package com.circulardollar.cdatm.business.upstream.model.auth;

import java.util.Objects;

public class LogoutRecordRequest extends LogoutRecord implements ILogoutRecordRequest {

    private final String tokenId;

    LogoutRecordRequest(Long timeStamp, String tokenId) {
        super(timeStamp);
        Objects.requireNonNull(tokenId);
        this.tokenId = tokenId;
    }

    @Override public String getTokenId() {
        return tokenId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends LogoutRecord.Builder {
        private String tokenId;

        private Builder() {}

        @Override
        public Builder setTimeStamp(Long timeStamp) {
            super.setTimeStamp(timeStamp);
            return this;
        }

        public Builder setTokenId(String tokenId) {
            this.tokenId = tokenId;
            return this;
        }

        public LogoutRecordRequest build() {
            return new LogoutRecordRequest(
                super.getTimeStamp(), tokenId);
        }
    }
}
