package org.acme.out.mapper;

import org.acme.domain.model.Teacher;
import org.acme.out.postgres.entity.TeacherEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TeacherMapper {
    TeacherEntity toTeacherEntity(Teacher teacher);

    Teacher toDomainTeacher(TeacherEntity teacherEntity);
}
