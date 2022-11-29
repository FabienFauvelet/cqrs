package org.acme.out.mapper;

import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.postgres.entity.EventEntity;
import org.acme.out.postgres.entity.ResourceEntity;
import org.acme.out.postgres.entity.TeacherEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface EventMapper {
    Event toEventDomain(EventEntity eventEntity) throws InconsistentDomainDataException;
    EventEntity toEventEntity(Event event);

    Teacher toTeacher(TeacherEntity teacherEntity);
    TeacherEntity toTeacherEntity(Teacher teacher);

    Resource toResource(ResourceEntity resourceEntity);
    ResourceEntity toResourceEntity(Resource resource);
}
