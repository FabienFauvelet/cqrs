package org.acme.out.messages.model.customer;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class CustomerCreationCommonMessage extends CommonMessage {
    public CustomerCreationCommonMessage(CustomerCreationCommonMessage.CustomerCreationMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CUSTOMER_CREATION;
    }
    public static CustomerCreationCommonMessage.CustomerCreationMessageBody.CustomerCreationMessageBodyBuilder getBodyBuilder(){
        return CustomerCreationCommonMessage.CustomerCreationMessageBody.builder();
    }

    @Builder
    @Getter
    public static class CustomerCreationMessageBody {
        private UUID id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private AddressMessage address;
    }
    @Builder
    @Getter
    public static class AddressMessage{
        private UUID id;
        private String street;
        private String zipCode;
        private String city;
    }

}
