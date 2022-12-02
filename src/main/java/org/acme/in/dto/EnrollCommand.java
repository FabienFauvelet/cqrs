package org.acme.in.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EnrollCommand {
    private UUID customerId;
    private UUID eventId;
}
