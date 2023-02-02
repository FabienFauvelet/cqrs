package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonDeserialize
public class EventTeacherDeletion extends TopicMessage
{

    public EventTeacherDeletion(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body,EventTeacherDeletionBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.TEACHER_DELETION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventTeacherDeletionBody myBody = (EventTeacherDeletionBody) getBody();
        resource.deleteTeacher(myBody.getId());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventTeacherDeletionBody
    {
        private UUID id;
    }
}
