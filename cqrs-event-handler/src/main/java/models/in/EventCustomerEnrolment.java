package models.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import facade.AgendaResource;
import lombok.Getter;
import lombok.Setter;

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
        EventCustomerEnrolmentBody myBody = (EventCustomerEnrolmentBody) super.getBody();
        resource.enrollCustomer(myBody.getEventId(),myBody.getCustomerId());
    }

    @Getter
    @Setter
    @JsonDeserialize
    static private class EventCustomerEnrolmentBody
    {
        private UUID eventId;
        private UUID customerId;
    }
}
