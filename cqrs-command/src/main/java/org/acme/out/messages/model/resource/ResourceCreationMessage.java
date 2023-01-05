package org.acme.out.messages.model.resource;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.Message;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class ResourceCreationMessage extends Message {
    public ResourceCreationMessage(ResourceCreationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_CREATION;
    }
    public static ResourceCreationMessageBody.ResourceCreationMessageBodyBuilder getBodyBuilder(){
        return ResourceCreationMessageBody.builder();
    }
    @Builder
    @Getter
    public static class ResourceCreationMessageBody {
        private UUID id;
        private String name;
    }
}

