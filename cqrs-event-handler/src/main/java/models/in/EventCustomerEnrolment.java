package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import facade.AgendaResource;

import java.util.UUID;

public class EventCustomerEnrolment extends TopicMessage
{
    public EventCustomerEnrolment(){}
    public EventCustomerEnrolment(EventType type, Object body)
    {
        super.setMessageType(type);
        super.setBody(new ObjectMapper().convertValue(body, EventCustomerEnrolment.EventCustomerEnrolmentBody.class));
    }

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_ENROLMENT;
    }

    @Override
    public void insertObject(AgendaResource resource)
    {
        UUID eventId = ((EventCustomerEnrolmentBody)getBody()).eventId;
        UUID customerId = ((EventCustomerEnrolmentBody)getBody()).customerId;
        resource.enrollCustomer(eventId,customerId);
    }

    static private class EventCustomerEnrolmentBody
    {
        private UUID eventId;
        private UUID customerId;


    }
}
