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
        super.setBody(new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(body, EventCreationBody.class));
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
        resource.createEvent(myBody.id,myBody.startDateTime,myBody.endDateTime,myBody.nbMaxParticipant);
        System.out.println(myBody.id + " - " + myBody.startDateTime + "-" + myBody.endDateTime + "-" + myBody.nbMaxParticipant);
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCreationBody
    {
        private UUID id;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int nbMaxParticipant;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public LocalDateTime getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public LocalDateTime getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public int getNbMaxParticipant() {
            return nbMaxParticipant;
        }

        public void setNbMaxParticipant(int nbMaxParticipant) {
            this.nbMaxParticipant = nbMaxParticipant;
        }
    }
}