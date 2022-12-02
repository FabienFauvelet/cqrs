package org.acme.out.messages.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
public class CustomerEnrolmentCancellationMessage extends Message{
    public CustomerEnrolmentCancellationMessage(CustomerEnrolmentMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CUSTOMER_ENROLMENT_CANCELLATION;
    }
    public static CustomerEnrolmentMessageBody.CustomerEnrolmentMessageBodyBuilder getBodyBuilder(){
        return CustomerEnrolmentMessageBody.builder();
    }
    @Builder
    @Getter
    static class CustomerEnrolmentMessageBody {
        private UUID eventId;
        private UUID customerId;
    }
}

