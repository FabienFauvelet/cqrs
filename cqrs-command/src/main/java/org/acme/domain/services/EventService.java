package org.acme.domain.services;

import org.acme.domain.exception.DomainRuleViolationException;
import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Customer;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.CustomerRepository;
import org.acme.out.postgres.repository.EventRepository;
import org.acme.out.postgres.repository.ResourceRepository;
import org.acme.out.postgres.repository.TeacherRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventService {
    @Inject EventRepository eventRepository;
    @Inject ResourceRepository resourceRepository;
    @Inject TeacherRepository teacherRepository;
    @Inject CustomerRepository customerRepository;
    @Inject Publisher publisher;

    public void addEvent(Event event) throws InconsistentDomainDataException {
        UUID eventId = createEmptyEvent(event).getId();
        reserveResources(eventId, event.getReservedResources());
        assignTeacher(eventId, event.getTeacher());
        enrollCustomers(eventId, event.getParticipants());
        /* TODO gérer les règles métiers du type, est ce que le nombre de participants est respecté,
            est ce que les ressources sont libres etc... */
    }
    private Event createEmptyEvent(Event event) throws InconsistentDomainDataException {
        Event emptyEvent = event.toBuilder().participants(null).reservedResources(null).teacher(null).build();
        emptyEvent = eventRepository.createEvent(emptyEvent);
        publisher.publish(emptyEvent);
        return emptyEvent;
    }
    private void enrollCustomers(UUID eventId, List<Customer> customers){
        if(customers!=null && customers.stream().allMatch(customer -> customerRepository.exists(customer.getId()))){
            customers.forEach(customer -> {
                eventRepository.enrollCustomer(eventId,customer);
                publisher.publishNewEnrolment(eventId,customer.getId());
                    }
            );
        }
    }
    private void reserveResources(UUID eventId, List<Resource> resources) {
        if(resources!=null && resources.stream().allMatch(resource -> resourceRepository.exists(resource.getId()))){
            resources.forEach(resource -> {
                eventRepository.reserveResource(eventId,resource);
                publisher.publishResourceReservation(eventId,resource);
            } );
        }
    }
    private void assignTeacher(UUID eventId, Teacher teacher) {
        if(teacher!=null && teacherRepository.exists(teacher.getId())){
            eventRepository.assignTeacher(eventId,teacher);
            publisher.publishTeacherAssignation(eventId,teacher);
        }
    }

    public void deleteEvent(UUID eventId) throws DomainRuleViolationException {
        Event event = eventRepository.getEvent(eventId);
        if(event.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new DomainRuleViolationException("Impossible de supprimer un cours dont la date de début est passée");
        }
        eventRepository.delete(eventId);
        publisher.publishEventDeletion(eventId);
    }

    public void updateEvent(Event event) throws InconsistentDomainDataException {
        if(eventRepository.exists(event.getId())){
            Event oldEvent=eventRepository.getEvent(event.getId());

            List<Customer> addedCustomers= getAddedBetweenTwoLists(oldEvent.getParticipants(),event.getParticipants());
            List<Customer> removedCustomers = getRemovedBetweenTwoLists(oldEvent.getParticipants(),event.getParticipants());
            addedCustomers.forEach(customer ->  publisher.publishNewEnrolment(event.getId(),customer.getId()));
            removedCustomers.forEach(customer -> publisher.publishEnrolmentCancellation(event.getId(),customer.getId()));

            List<Resource> addedResources= getAddedBetweenTwoLists(oldEvent.getReservedResources(),event.getReservedResources());
            List<Resource> removedResources= getRemovedBetweenTwoLists(oldEvent.getReservedResources(),event.getReservedResources());
            addedResources.forEach(resource -> publisher.publishResourceReservation(event.getId(),resource));
            removedResources.forEach(resource -> publisher.publishResourceRelease(event.getId(),resource));

            if(oldEvent.getTeacher()==null || !oldEvent.getTeacher().getId().equals(event.getTeacher().getId())){
                publisher.publishTeacherAssignation(event.getId(),event.getTeacher());
            }
            eventRepository.updateEvent(event);
        }else{
            throw new InconsistentDomainDataException("Cet événement n'existe pas");
        }


    }
    private <T> List<T> getRemovedBetweenTwoLists(List<T> oldList, List<T> newList){
        if(oldList != null){
            if(newList != null){
                return oldList.stream()
                        .filter(o -> !newList.contains(o))
                        .collect(Collectors.toList());
            }else{
                return oldList;
            }
        }else {
            return new ArrayList<>();
        }

    }
    private <T> List<T> getAddedBetweenTwoLists(List<T> oldList, List<T> newList){
        return getRemovedBetweenTwoLists(newList,oldList);
    }
}
