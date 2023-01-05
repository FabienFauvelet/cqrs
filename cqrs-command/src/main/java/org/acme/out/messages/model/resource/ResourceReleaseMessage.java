package org.acme.out.messages.model.resource;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.Message;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class ResourceReleaseMessage extends Message {
    public ResourceReleaseMessage(ResourceReleaseMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_RELEASE;
    }
    public static ResourceReleaseMessageBody.ResourceReleaseMessageBodyBuilder getBodyBuilder(){
        return ResourceReleaseMessageBody.builder();
    }
    @Builder
    @Getter
    static class ResourceReleaseMessageBody {
        private UUID eventId;
        private UUID resourceId;
    }
}

