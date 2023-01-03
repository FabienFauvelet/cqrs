package org.acme.in.dto.customer;

import lombok.Data;

@Data
public class CreateCustomerCommand {
    private String firstName;
    private String lastName;
}
