package org.acme.out.messages.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Message {
    protected MessageType messageType;
    protected LocalDateTime localDateTime;
    private Object body;
    public Message() {
        localDateTime = LocalDateTime.now();
        messageType = getMessageType();
    }

    public abstract MessageType getMessageType();

    protected void setBody(Object body) {
        this.body=body;
    }
}
