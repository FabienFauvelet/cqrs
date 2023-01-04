package org.acme.in.dto.resource;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateResourceCommand {
    private UUID resourceId;
}
