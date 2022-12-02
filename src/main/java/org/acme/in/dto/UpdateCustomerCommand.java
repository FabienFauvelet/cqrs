package org.acme.in.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCustomerCommand {
    private UUID customerId;
    private String firstName;
    private String lastName;
}
