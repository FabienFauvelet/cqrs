package org.acme.out.messages.model.customer;

import lombok.Builder;
import lombok.Getter;
import org.acme.out.messages.model.CommonMessage;
import org.acme.out.messages.model.MessageType;
import org.acme.out.messages.model.shared.Address;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class CustomerUpdateMessage extends CommonMessage {
    public CustomerUpdateMessage(CustomerUpdateMessageBody body) {
        super.setBody(body);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CUSTOMER_UPDATE;
    }
    public static CustomerUpdateMessageBody.CustomerUpdateMessageBodyBuilder getBodyBuilder(){
        return CustomerUpdateMessageBody.builder();
    }

    @Builder
    @Getter
    public static class CustomerUpdateMessageBody {
        private UUID id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private Address address;
    }

}
