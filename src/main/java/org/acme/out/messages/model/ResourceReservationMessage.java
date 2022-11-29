package org.acme.out.messages.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;



@Getter
public class ResourceReservationMessage extends Message{
    public ResourceReservationMessage(ResourceReservationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_RESERVATION;
    }
    public static ResourceReservationMessageBody.ResourceReservationMessageBodyBuilder getBodyBuilder(){
        return ResourceReservationMessageBody.builder();
    }
    @Builder
    @Getter
    static class ResourceReservationMessageBody {
        private UUID eventId;
        private UUID resourceId;
    }
}

