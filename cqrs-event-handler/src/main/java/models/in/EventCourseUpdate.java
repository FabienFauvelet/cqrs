package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import jdk.jfr.Event;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonDeserialize
public class EventCourseUpdate extends TopicMessage
{
    public EventCourseUpdate(EventType type, Object body)
    {
        setMessageType(type);
        setBody(new ObjectMapper().findAndRegisterModules().convertValue(body,EventCourseUpdateBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.EVENT_UPDATE;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCourseUpdateBody myBody = (EventCourseUpdateBody) getBody();
        resource.updateEvent(myBody.getEventId());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCourseUpdateBody
    {
        private UUID eventId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }
}
