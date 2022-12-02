package org.acme.in.dto;

import lombok.Data;

@Data
public class CreateResourceCommand {
    private String name;
    private String type;
    private int capacity;

}
