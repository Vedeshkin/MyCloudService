package com.github.vedeshkin.cloud.common.response;

import com.github.vedeshkin.cloud.common.AuthorizationStatus;
import com.github.vedeshkin.cloud.common.response.AbstractResponse;
import com.github.vedeshkin.cloud.common.response.ResponseType;

public class AuthorizeResponse  extends AbstractResponse {

    private final AuthorizationStatus status;
    private final String messsage;

    public AuthorizeResponse(AuthorizationStatus status, String message) {
        super(ResponseType.AUTHORIZATION);
        this.status = status;
        this.messsage = message;
    }


    public AuthorizationStatus getStatus() {
        return status;
    }

    public String getMesssage() {
        return messsage;
    }
}
