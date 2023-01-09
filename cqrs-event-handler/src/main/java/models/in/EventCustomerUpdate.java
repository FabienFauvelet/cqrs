package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

public class EventCustomerUpdate extends TopicMessage
{
    public EventCustomerUpdate(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body,EventCustomerUpdateBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_UPDATE;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCustomerUpdateBody myBody = (EventCustomerUpdateBody) getBody();
        resource.updateCustomer(myBody.getId(),myBody.getFirstName(),myBody.getLastName(),myBody.getBirthDate(),myBody.getAddress());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCustomerUpdateBody
    {
        private UUID id;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private CustomerAddress address;
    }
}
