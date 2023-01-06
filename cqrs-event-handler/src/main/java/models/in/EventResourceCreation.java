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
public class EventResourceCreation extends TopicMessage
{

    public EventResourceCreation(){}
    public EventResourceCreation(EventType type, Object body)
    {
        super.setMessageType(EventType.RESOURCE_CREATION);
        super.setBody(new ObjectMapper().convertValue(body,EventResourceCreationBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.RESOURCE_CREATION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventResourceCreationBody body = (EventResourceCreationBody) getBody();
        resource.createResource(body.getId(), body.getName());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventResourceCreationBody
    {
        private UUID id;
        private String name;

    }
}
