package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.InsertionResource;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonDeserialize
public class EventTeacherAssignation extends TopicMessage{

    public EventTeacherAssignation(){}
    public EventTeacherAssignation(EventType type, Object body)
    {
        setMessageType(type);
        setBody(new ObjectMapper().convertValue(body,EventTeachAssignationBody.class));
    }

    @Override
    public EventType getMessageType() {
        return EventType.TEACHER_ASSIGNATION;
    }

    @Override
    protected void setBody(Object body) {
        super.setBody(body);
    }

    @Override
    protected void setMessageType(EventType eventType) {
        super.setMessageType(eventType);
    }

    @Override
    protected Object getBody() {
        return super.getBody();
    }

    @Override
    public void insertObject(InsertionResource resource) {
        EventTeachAssignationBody myBody = (EventTeachAssignationBody) getBody();
        resource.assignTeacher(myBody.getEventId(), myBody.getTeacherId());

    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventTeachAssignationBody
    {
        private UUID eventId;
        private UUID teacherId;
    }
}
