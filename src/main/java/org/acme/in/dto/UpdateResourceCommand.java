package org.acme.in.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateResourceCommand {
    private UUID resourceId;
}
