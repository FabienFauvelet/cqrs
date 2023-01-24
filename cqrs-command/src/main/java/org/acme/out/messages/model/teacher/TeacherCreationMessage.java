package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherCreationCommonMessage extends CommonMessage {
    public TeacherCreationCommonMessage(TeacherCreationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEACHER_CREATION;
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

