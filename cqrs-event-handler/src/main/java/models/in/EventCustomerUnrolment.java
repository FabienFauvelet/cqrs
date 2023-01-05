package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import facade.AgendaResource;

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
        UUID eventId = ((EventCustomerUnrolmentBody)getBody()).eventId;
        UUID customerId = ((EventCustomerUnrolmentBody)getBody()).customerId;
        resource.unrollCustomer(eventId,customerId);
    }

    static private class EventCustomerUnrolmentBody
    {
        private UUID eventId;
        private UUID customerId;
    }
}
