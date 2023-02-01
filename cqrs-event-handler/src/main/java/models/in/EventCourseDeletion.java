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
public class EventCourseDeletion extends TopicMessage
{
    public EventCourseDeletion(){}
    public EventCourseDeletion(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body, EventCourseDeletion.EventCourseDeletionBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.EVENT_DELETION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCourseDeletion.EventCourseDeletionBody myBody = (EventCourseDeletion.EventCourseDeletionBody) getBody();
        resource.deleteEvent(myBody.eventId);
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCourseDeletionBody
    {
        private UUID eventId;
    }
}
