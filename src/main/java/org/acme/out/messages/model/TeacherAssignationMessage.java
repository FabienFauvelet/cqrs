package org.acme.out.messages.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
public class TeacherAssignationMessage extends Message{
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
