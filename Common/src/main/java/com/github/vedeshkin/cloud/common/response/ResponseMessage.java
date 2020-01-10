package com.github.vedeshkin.cloud.common.response;


public class ResponseMessage extends AbstractResponse {
    private String message;

    public ResponseMessage(String message) {
        super(ResponseType.MESSAGE);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
