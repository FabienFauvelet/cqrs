package org.acme.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
import org.acme.domain.exception.InconsistentDomainDataException;

@Data
@Builder(toBuilder = true)
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
