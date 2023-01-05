package models.in;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class EventCustomerCreation extends TopicMessage
{

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_CREATION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCustomerCreationBody body = (EventCustomerCreationBody) getBody();
        resource.createCustomer(body.customerId, body.firstname, body.lastname);
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCustomerCreationBody
    {
        private UUID customerId;
        private String firstname;
        private String lastname;
        //More infos ?


    }
}
