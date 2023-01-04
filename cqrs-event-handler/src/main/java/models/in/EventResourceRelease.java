package models.in;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.InsertionResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize
public class EventResourceRelease extends TopicMessage
{
    @Override
    public EventType getMessageType()
    {
        return EventType.RESOURCE_RELEASE;
    }

    @Override
    public void insertObject(InsertionResource resource)
    {

    }
}
