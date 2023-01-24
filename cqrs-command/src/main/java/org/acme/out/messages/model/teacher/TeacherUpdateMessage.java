package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherUpdateCommonMessage extends CommonMessage {
    public TeacherUpdateCommonMessage(TeacherUpdateMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEACHER_UPDATE;
    }
    public static TeacherUpdateMessageBody.TeacherUpdateMessageBodyBuilder getBodyBuilder(){
        return TeacherUpdateMessageBody.builder();
    }
    @Builder
    @Getter
    public static class TeacherUpdateMessageBody {
        private UUID id;
        private String lastName;
        private String firstName;
    }
}

