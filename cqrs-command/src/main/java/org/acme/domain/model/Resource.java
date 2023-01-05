package org.acme.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Resource {
    private UUID id;
    private String name;
    private String type;
    private int capacity;
}
