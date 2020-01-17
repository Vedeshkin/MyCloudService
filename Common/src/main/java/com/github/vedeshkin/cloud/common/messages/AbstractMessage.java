package com.github.vedeshkin.cloud.common.messages;

import java.io.Serializable;

public class AbstractMessage implements Serializable {

    protected MessageType messageType;

    public AbstractMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
