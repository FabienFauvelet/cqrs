package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.Message;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherCreationMessage extends Message {
    public TeacherCreationMessage(TeacherCreationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESOURCE_CREATION;
    }
    public static TeacherCreationMessageBody.TeacherCreationMessageBodyBuilder getBodyBuilder(){
        return TeacherCreationMessageBody.builder();
    }
    @Builder
    @Getter
    public static class TeacherCreationMessageBody {
        private UUID id;
        private String lastName;
        private String firstName;
    }
}

