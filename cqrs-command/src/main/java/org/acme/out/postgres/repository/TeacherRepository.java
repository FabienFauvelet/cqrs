package org.acme.out.postgres.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.Teacher;
import org.acme.domain.model.Teacher;
import org.acme.out.mapper.EventMapper;
import org.acme.out.mapper.TeacherMapper;
import org.acme.out.postgres.entity.TeacherEntity;
import org.acme.out.postgres.entity.TeacherEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class TeacherRepository implements PanacheRepository<TeacherEntity> {
    @Inject
    TeacherMapper teacherMapper;
    @Transactional
    public Teacher createTeacher(Teacher teacher){
        TeacherEntity teacherEntity = teacherMapper.toTeacherEntity(teacher);
        persist(teacherEntity);
        return teacherMapper.toDomainTeacher(teacherEntity);
    }
    @Transactional
    public void updateTeacher(Teacher teacher){
        TeacherEntity teacherEntity = teacherMapper.toTeacherEntity(teacher);
        persistAndFlush(teacherEntity);
    }
    public void delete(UUID teacherId) {
        delete("id",teacherId);
    }
    @Transactional
    public boolean exists(UUID id) {
        return count("id",id) > 0;
    }
}
