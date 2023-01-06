package models.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class EventCustomerCreation extends TopicMessage
{
    public EventCustomerCreation(){}
    public EventCustomerCreation(EventType type,Object body)
    {
        setMessageType(type);
        setBody(new ObjectMapper().findAndRegisterModules().convertValue(body, EventCustomerCreation.EventCustomerCreationBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_CREATION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCustomerCreationBody body = (EventCustomerCreationBody) getBody();
        resource.createCustomer(body.getId(),body.getFirstName(),body.getLastName(),body.getBirthDate(),body.getAddress());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static class EventCustomerCreationBody
    {
        private UUID id;
        private String firstName;
        private String lastName;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date birthDate;
        private CustomerAddress address;
        //More infos ?


    }
}
