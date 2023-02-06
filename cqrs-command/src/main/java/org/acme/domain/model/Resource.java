package org.acme.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Resource {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String type;
    private int capacity;
}
