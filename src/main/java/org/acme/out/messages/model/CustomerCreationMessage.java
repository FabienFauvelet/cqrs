package org.acme.out.messages.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class CustomerCreationMessage extends Message{
    public CustomerCreationMessage(CustomerCreationMessage.CustomerCreationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CUSTOMER_CREATION;
    }
    public static CustomerCreationMessage.CustomerCreationMessageBody.CustomerCreationMessageBodyBuilder getBodyBuilder(){
        return CustomerCreationMessage.CustomerCreationMessageBody.builder();
    }

    @Builder
    @Getter
    public static class CustomerCreationMessageBody {
        private UUID id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private UUID addressId;
    }

}
