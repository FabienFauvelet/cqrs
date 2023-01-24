package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherAssignationMessage extends CommonMessage {
    public TeacherAssignationMessage(TeacherAssignationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEACHER_ASSIGNATION;
    }
    public static TeacherAssignationMessageBody.TeacherAssignationMessageBodyBuilder getBodyBuilder(){
        return TeacherAssignationMessageBody.builder();
    }
    @Builder
    @Getter
    static class TeacherAssignationMessageBody {
        private UUID eventId;
        private UUID teacherId;
    }
}
