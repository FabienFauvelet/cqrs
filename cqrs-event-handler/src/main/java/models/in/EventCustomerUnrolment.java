package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class EventCustomerUnrolment extends TopicMessage
{

    public EventCustomerUnrolment(){}
    public EventCustomerUnrolment(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body, EventCustomerUnrolment.EventCustomerUnrolmentBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_ENROLMENT_CANCELLATION;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        EventCustomerUnrolmentBody myBody = (EventCustomerUnrolmentBody) super.getBody();
        resource.unrollCustomer(myBody.getEventId(),myBody.getCustomerId());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static private class EventCustomerUnrolmentBody
    {
        private UUID eventId;
        private UUID customerId;
    }
}
