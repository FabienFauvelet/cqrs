package org.acme.out.messages.model.teacher;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class TeacherUnassignationMessage extends CommonMessage {
    public TeacherUnassignationMessage(TeacherUnassignationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEACHER_UNASSIGNATION;
    }
    public static TeacherUnassignationMessageBody.TeacherUnassignationMessageBodyBuilder getBodyBuilder(){
        return TeacherUnassignationMessageBody.builder();
    }
    @Builder
    @Getter
    static class TeacherUnassignationMessageBody {
        private UUID eventId;
        private UUID teacherId;
    }
}
