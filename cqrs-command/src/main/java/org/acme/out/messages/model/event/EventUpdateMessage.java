package org.acme.out.messages.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EventUpdateMessage extends CommonMessage {


    public EventUpdateMessage(EventUpdateMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.EVENT_UPDATE;
    }
    public static EventUpdateMessageBody.EventUpdateMessageBodyBuilder getBodyBuilder(){
        return EventUpdateMessageBody.builder();
    }
    @Builder
    @Getter
    @Setter
    static class EventUpdateMessageBody {
        private UUID id;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int nbMaxParticipant;
        private String type;

    }
}
