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
public class EventCustomerDeletion extends TopicMessage
{
    public EventCustomerDeletion(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body,EventCustomerDeletionBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_DELETION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCustomerDeletionBody myBody = (EventCustomerDeletionBody) getBody();
        resource.deleteCustomer(myBody.getId());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCustomerDeletionBody
    {
        private UUID id;
    }
}
