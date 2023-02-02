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
public class EventResourceDeletion extends TopicMessage
{
    public EventResourceDeletion(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body,EventResourceDeletionBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.RESOURCE_DELETION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventResourceDeletionBody myBody = (EventResourceDeletionBody) getBody();
        resource.deleteResource(myBody.getId());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventResourceDeletionBody
    {
        private UUID id;
    }
}
