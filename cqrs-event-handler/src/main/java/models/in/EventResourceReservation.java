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
public class EventResourceReservation extends TopicMessage
{

    public EventResourceReservation(){}
    public EventResourceReservation(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body,EventResourceReservationBody.class));
    }

    @Override
    public EventType getMessageType() {
        return EventType.RESOURCE_RESERVATION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        UUID eventId = ((EventResourceReservationBody)getBody()).getEventId();
        EventResourceReservationBody myBody = (EventResourceReservationBody) getBody();
        //System.out.println(eventId.toString());

        resource.addResourceToEvent(eventId,myBody.resourceId);

    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventResourceReservationBody
    {
        private UUID eventId;
        private UUID resourceId;

        public UUID getEventId() {
            return eventId;
        }

        public void setEventId(UUID eventId) {
            this.eventId = eventId;
        }

        public UUID getResourceId() {
            return resourceId;
        }

        public void setResourceId(UUID resourceId) {
            this.resourceId = resourceId;
        }
    }
}
