package org.acme.in.dto.customer;

import lombok.Data;

@Data
public class CreateCustomerCommand {
    private String firstName;
    private String lastName;
    private Address address;

    public static class Address {
        private String street;
        private String zipCode;
        private String city;
    }
}
