package org.acme.out.messages.model.enrolment;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.util.UUID;


@Getter
public class CustomerEnrolmentCommonMessage extends CommonMessage {
    public CustomerEnrolmentCommonMessage(CustomerEnrolmentMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CUSTOMER_ENROLMENT;
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
