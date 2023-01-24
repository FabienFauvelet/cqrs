package org.acme.out.messages.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EventCreationCommonMessage extends CommonMessage {


    public EventCreationCommonMessage(EventCreationMessageBody body) {
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
        private String type;

    }
}
