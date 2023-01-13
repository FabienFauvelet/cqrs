package org.acme.out.messages.model.resource;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class ResourceUpdateCommonMessage extends CommonMessage {
    public ResourceUpdateCommonMessage(ResourceUpdateMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_UPDATE;
    }
    public static ResourceUpdateMessageBody.ResourceUpdateMessageBodyBuilder getBodyBuilder(){
        return ResourceUpdateMessageBody.builder();
    }
    @Builder
    @Getter
    public static class ResourceUpdateMessageBody {
        private UUID id;
        private String name;
    }
}

