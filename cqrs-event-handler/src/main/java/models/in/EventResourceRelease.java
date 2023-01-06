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
public class EventResourceRelease extends TopicMessage
{
    public EventResourceRelease(){}
    public EventResourceRelease(EventType type, Object body)
    {
        super.setMessageType(EventType.RESOURCE_RELEASE);
        super.setBody(new ObjectMapper().convertValue(body,EventResourceReleaseBody.class));
    }
    @Override
    public EventType getMessageType()
    {
        return EventType.RESOURCE_RELEASE;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {

    }


    @Getter
    @Setter
    @JsonDeserialize
    static class EventResourceReleaseBody
    {
        private UUID eventId;
        private UUID resourceId;
    }
}
