package org.acme.in.dto;

import lombok.Data;

@Data
public class CreateCustomerCommand {
    private String firstName;
    private String lastName;
}
