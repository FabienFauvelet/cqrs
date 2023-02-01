package org.acme.in.dto.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerCommand {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;

    @Data
    public static class Address {
        private String street;
        private String zipCode;
        private String city;
    }
}
