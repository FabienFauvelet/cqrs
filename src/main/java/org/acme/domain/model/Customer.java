package org.acme.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class Customer {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;

}
