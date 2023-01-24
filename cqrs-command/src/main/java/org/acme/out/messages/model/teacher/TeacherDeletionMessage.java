package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherDeletionMessage extends CommonMessage {
    public TeacherDeletionMessage(TeacherDeletionMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEACHER_DELETION;
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

