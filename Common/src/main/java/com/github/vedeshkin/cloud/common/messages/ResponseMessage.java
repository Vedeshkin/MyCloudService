package com.github.vedeshkin.cloud.common.messages;


public class ResponseMessage extends AbstractMessage {
    private String message;

    public ResponseMessage(String message) {
        super(MessageType.MESSAGE);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
