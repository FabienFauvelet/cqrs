package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.Message;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherDeletionMessage extends Message {
    public TeacherDeletionMessage(TeacherDeletionMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_UPDATE;
    }
    public static TeacherDeletionMessageBody.TeacherDeletionMessageBodyBuilder getBodyBuilder(){
        return TeacherDeletionMessageBody.builder();
    }
    @Builder
    @Getter
    public static class TeacherDeletionMessageBody {
        private UUID id;
    }
}

