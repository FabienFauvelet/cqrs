package org.acme.domain.services;

import org.acme.domain.model.Teacher;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.TeacherRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class TeacherService {
    @Inject Publisher publisher;
    @Inject TeacherRepository teacherRepository;
    public void addTeacher(Teacher teacher){
        teacher=teacherRepository.createTeacher(teacher);
        publisher.publishTeacherCreation(teacher);
    }
    public void updateTeacher(Teacher teacher){
        teacherRepository.updateTeacher(teacher);
        publisher.publishTeacherUpdate(teacher);
    }
    public void deleteTeacher(UUID teacherId){
        //TODO VERIFICATION DE L'ASSIGNATION DU PROF
        teacherRepository.delete(teacherId);
        publisher.publishTeacherDeletion(teacherId);
    }
}
