package org.acme.out.messages.model;

import lombok.Getter;

@Getter
public abstract class Message {
    protected MessageType messageType;
    private Object body;
    public Message() {
        messageType = getMessageType();
    }

    public abstract MessageType getMessageType();

    protected void setBody(Object body) {
        this.body=body;
    }
}
