package org.acme.out.messages.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class CommonMessage {
    protected MessageType messageType;
    protected LocalDateTime localDateTime;
    private Object body;
    public CommonMessage() {
        localDateTime = LocalDateTime.now();
        messageType = getMessageType();
    }

    public abstract MessageType getMessageType();

    protected void setBody(Object body) {
        this.body=body;
    }
}
