package org.acme.in.dto.enrolment;

import lombok.Data;

import java.util.UUID;

@Data
public class EnrollCommand {
    private UUID customerId;
    private UUID eventId;
}
