package org.acme.out.messages.model.resource;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;



@Getter
public class ResourceReservationCommonMessage extends CommonMessage {
    public ResourceReservationCommonMessage(ResourceReservationMessageBody body) {
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

