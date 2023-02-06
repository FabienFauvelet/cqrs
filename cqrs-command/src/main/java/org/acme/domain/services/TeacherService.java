package org.acme.domain.services;

import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.EventRepository;
import org.acme.out.postgres.repository.TeacherRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TeacherService {
    @Inject Publisher publisher;
    @Inject TeacherRepository teacherRepository;
    @Inject
    EventRepository eventRepository;
    public Teacher addTeacher(Teacher teacher){
        teacher=teacherRepository.createTeacher(teacher);
        publisher.publishTeacherCreation(teacher);
        return teacher;
    }
    public void updateTeacher(Teacher teacher){
        teacherRepository.updateTeacher(teacher);
        publisher.publishTeacherUpdate(teacher);
    }
    public void deleteTeacher(UUID teacherId) {
        if (teacherRepository.exists(teacherId)) {
            List<Event> teacherAssignationsInFutureEvents = eventRepository.getTeacherAssignationsInFutureEvents(teacherId);
            teacherAssignationsInFutureEvents.forEach(event -> {
                Teacher teacher = event.getTeacher();
                event.setTeacher(null);
                eventRepository.updateEvent(event);
                publisher.publishTeacherUnassignation(event.getId(), teacher);
            });
            //teacherRepository.delete(teacherId);
            publisher.publishTeacherDeletion(teacherId);
        }
    }
}
