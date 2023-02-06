package org.acme.domain.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Resource {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String type;
    private int capacity;
}
