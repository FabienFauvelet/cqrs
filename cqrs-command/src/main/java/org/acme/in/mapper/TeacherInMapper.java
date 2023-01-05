package org.acme.in.mapper;

import org.acme.domain.model.Teacher;
import org.acme.in.dto.teacher.CreateTeacherCommand;
import org.acme.in.dto.teacher.UpdateTeacherCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TeacherInMapper {
    Teacher toDomainTeacher(UpdateTeacherCommand updateTeacherQuery);

    Teacher toDomainTeacher(CreateTeacherCommand createTeacherCommand);
}
