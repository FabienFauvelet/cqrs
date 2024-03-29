package org.acme.out.messages.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;

public class EventDeletionMessage extends CommonMessage {
    public EventDeletionMessage(EventDeletionMessageBody body) {
        super.setBody(body);
    }
    @Override
    public MessageType getMessageType() {
        return MessageType.EVENT_DELETION;
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
