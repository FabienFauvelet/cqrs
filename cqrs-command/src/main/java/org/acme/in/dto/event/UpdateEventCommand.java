package org.acme.in.dto.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UpdateEventCommand {
    private UUID id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private UUID teacherId;
    private ArrayList<UUID> reservedResources;
    private int nbMaxParticipant;
    private String type;
    private ArrayList<UUID> participants;
}
