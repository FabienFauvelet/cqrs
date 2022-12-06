package org.acme.out.messages.model.shared;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Address {
    private UUID id;
}
