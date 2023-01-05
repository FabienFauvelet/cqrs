package org.acme.out.messages.model.resource;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.Message;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class ResourceDeletionMessage extends Message {
    public ResourceDeletionMessage(ResourceDeletionMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_UPDATE;
    }
    public static ResourceDeletionMessageBody.ResourceDeletionMessageBodyBuilder getBodyBuilder(){
        return ResourceDeletionMessageBody.builder();
    }
    @Builder
    @Getter
    public static class ResourceDeletionMessageBody {
        private UUID id;
    }
}

