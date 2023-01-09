package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@JsonDeserialize
public class EventCourseCreation extends TopicMessage {

    public EventCourseCreation(EventType type, Object body) throws IOException {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().findAndRegisterModules().convertValue(body,EventCreationBody.class));
    }

    @Override
    public EventType getMessageType() {
        return EventType.EVENT_CREATION;
    }

    public EventCreationBody getBody()
    {
        return (EventCreationBody) super.getBody();
    }

    @Override
    public String toString() {
        return getMessageType().toString() + "\n" + super.getBody().toString();
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCreationBody myBody = getBody();
        resource.createEvent(myBody.getId(),myBody.getType(),myBody.getStartDateTime(),myBody.getEndDateTime(),myBody.getNbMaxParticipant());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCreationBody
    {
        private UUID id;
        private String type;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int nbMaxParticipant;
    }
}