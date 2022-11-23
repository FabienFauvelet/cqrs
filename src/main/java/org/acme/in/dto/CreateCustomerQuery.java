package org.acme.in.dto;

import lombok.Data;

@Data
public class CreateCustomerQuery {
    private String firstName;
    private String lastName;
}
