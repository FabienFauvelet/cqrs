package org.acme.domain.services;

import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.messages.Publisher;
import org.acme.out.repository.EventRepository;
import org.acme.out.repository.ResourceRepository;
import org.acme.out.repository.TeacherRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

@ApplicationScoped
public class EventService {
    @Inject EventRepository eventRepository;
    @Inject
    ResourceRepository resourceRepository;
    @Inject
    TeacherRepository teacherRepository;
    @Inject
    Publisher publisher;


    public void addEvent(Event event) {
        UUID eventId = createEmptyEvent(event).getId();
        reserveResources(eventId, event.getReservedResources());
        assignTeacher(eventId, event.getTeacher());
    }
    private Event createEmptyEvent(Event event) {
        Event emptyEvent = event.toBuilder().reservedResources(null).teacher(null).build();
        emptyEvent = eventRepository.createEvent(emptyEvent);
        publisher.publish(emptyEvent);
        return emptyEvent;
    }
    private void reserveResources(UUID eventId, ArrayList<Resource> resources) {
        if(resources.stream().allMatch(resource -> resourceRepository.exists(resource.getId()))){
            resources.forEach(resource -> {
                eventRepository.reserveResource(eventId,resource);
                publisher.publish(eventId,resource);
            } );
        }
    }
    private void assignTeacher(UUID eventId, Teacher teacher) {
        if(teacherRepository.exists(teacher.getId())){
            eventRepository.assignTeacher(eventId,teacher);
            publisher.publish(eventId,teacher);
        }
    }
}
