package org.acme.in.mapper;

import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.in.dto.CreateEventCommand;
import org.acme.in.dto.UpdateEventCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "cdi")
public interface EventInMapper {
    @Mapping(source = "teacherId", target = "teacher")
    Event toEvent(CreateEventCommand createEventCommand) throws InconsistentDomainDataException;
    @Mapping(source = "teacherId", target = "teacher")
    Event toEvent(UpdateEventCommand updateEventQuery) throws InconsistentDomainDataException;

    @Mapping(target = "id", source = ".")
    Resource toResource(UUID uuid);
    @Mapping(target = "id", source = ".")
    Teacher toTeacher(UUID uuid);
}
