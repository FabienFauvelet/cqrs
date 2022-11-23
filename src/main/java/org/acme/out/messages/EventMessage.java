package org.acme.out.messages;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventMessage {
    private Object object;
}
