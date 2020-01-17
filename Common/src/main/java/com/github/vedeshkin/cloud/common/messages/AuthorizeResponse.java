package com.github.vedeshkin.cloud.common.messages;

import com.github.vedeshkin.cloud.common.AuthorizationStatus;

public class AuthorizeResponse  extends AbstractMessage {

    private final AuthorizationStatus status;
    private final String message;

    public AuthorizeResponse(AuthorizationStatus status, String message) {
        super(MessageType.AUTHORIZATION);
        this.status = status;
        this.message = message;
    }


    public AuthorizationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
