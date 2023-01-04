package models.in;

import facade.InsertionResource;

import java.util.UUID;

public class EventCustomerEnrolment extends TopicMessage
{

    @Override
    public EventType getMessageType()
    {
        return EventType.CUSTOMER_ENROLMENT;
    }

    @Override
    public void insertObject(InsertionResource resource)
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
