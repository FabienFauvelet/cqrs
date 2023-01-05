package models.in;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize
public class EventResourceCreation extends TopicMessage
{


    @Override
    public EventType getMessageType()
    {
        return EventType.RESOURCE_CREATION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {

    }
}
