package org.acme.in.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import lombok.Data;


@Data
public class UpdateEventQuery {
    private UUID eventId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private UUID teacherId;
    private ArrayList<UUID> reservedResources;
    private int nbMaxParticipant;
}
