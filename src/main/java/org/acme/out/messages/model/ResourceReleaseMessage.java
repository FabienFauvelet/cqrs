package org.acme.out.messages.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
public class ResourceReleaseMessage extends Message{
    public ResourceReleaseMessage(ResourceReservationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_RELEASE;
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

