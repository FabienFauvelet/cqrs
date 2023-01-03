package org.acme.in.dto.resource;

import lombok.Data;

@Data
public class CreateResourceCommand {
    private String name;
    private String type;
    private int capacity;

}
