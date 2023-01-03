package org.acme.out.messages.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.acme.out.messages.model.Message;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;

public class EventDeletionMessage extends Message {
    public EventDeletionMessage(EventDeletionMessageBody body) {
        super.setBody(body);
    }
    @Override
    public MessageType getMessageType() {
        return null;
    }
    public static EventDeletionMessageBody.EventDeletionMessageBodyBuilder getBodyBuilder(){
        return EventDeletionMessageBody.builder();
    }
    @Builder
    @Getter
    @Setter
    static class EventDeletionMessageBody {
        private UUID id;
    }
}
