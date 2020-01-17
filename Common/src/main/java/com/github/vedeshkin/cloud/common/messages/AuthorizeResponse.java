package com.github.vedeshkin.cloud.common.messages;


public class AuthorizeResponse extends AbstractMessage {

    private final boolean isAuthorized;
    private final String message;

    public AuthorizeResponse(boolean isAuthorized, String message) {
        super(MessageType.AUTHORIZATION_RESPONSE);
        this.isAuthorized = isAuthorized;
        this.message = message;
    }

    public boolean isAuthorized() {
        return this.isAuthorized;
    }

    public String getMessage() {
        return message;
    }
}
