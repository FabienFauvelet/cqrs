package org.acme.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


import lombok.*;
import org.acme.domain.exception.InconsistentDomainDataException;

@Data
@NoArgsConstructor
public class Event {
    private UUID id;
    @Setter(AccessLevel.NONE)
    private LocalDateTime startDateTime;
    @Setter(AccessLevel.NONE)
    private LocalDateTime endDateTime;
    private Teacher teacher;
    private ArrayList<Resource> reservedResources;
    private int nbMaxParticipant;
    private ArrayList<Customer> participants;

    @Builder(toBuilder = true)

    public Event(UUID id, LocalDateTime startDateTime, LocalDateTime endDateTime, Teacher teacher, ArrayList<Resource> reservedResources, int nbMaxParticipant, ArrayList<Customer> participants) throws InconsistentDomainDataException {
        this.id = id;
        this.setStartDateTime(startDateTime);
        this.setEndDateTime(endDateTime);
        this.teacher = teacher;
        this.reservedResources = reservedResources;
        this.nbMaxParticipant = nbMaxParticipant;
        this.participants = participants;
    }

    public void setStartDateTime(LocalDateTime startDateTime) throws InconsistentDomainDataException {
        if(endDateTime!= null && endDateTime.isBefore(startDateTime)){
            throw new InconsistentDomainDataException("Start date must be before end date");
        }
        this.startDateTime=startDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) throws InconsistentDomainDataException{
        if(startDateTime!= null && startDateTime.isAfter(endDateTime)){
            throw new InconsistentDomainDataException("End date must be after start date");
        }
        this.endDateTime=endDateTime;
    }
}
