package org.acme.out.messages.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EventCreationMessage extends Message {


    public EventCreationMessage(EventCreationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.EVENT_CREATION;
    }
    public static EventCreationMessageBody.EventCreationMessageBodyBuilder getBodyBuilder(){
        return EventCreationMessageBody.builder();
    }
    @Builder
    @Getter
    @Setter
    static class EventCreationMessageBody {
        private UUID id;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int nbMaxParticipant;
    }
}
