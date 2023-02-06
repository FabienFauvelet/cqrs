package org.acme.out.postgres.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Customer;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.postgres.entity.CustomerEntity;
import org.acme.out.postgres.entity.EventEntity;
import org.acme.out.postgres.entity.ResourceEntity;
import org.acme.out.mapper.EventMapper;

@ApplicationScoped
public class EventRepository implements PanacheRepository<EventEntity>{
    
    @Inject
    EventMapper eventMapper;
    @Transactional
    public Event getEvent(UUID id){
        EventEntity eventEntity = find("id",id).firstResult();
        try {
            return eventMapper.toEventDomain(eventEntity);
        } catch (InconsistentDomainDataException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public Event createEvent(Event event){
        EventEntity eventEntity = eventMapper.toEventEntity(event);
        persist(eventEntity);
        try {
            return eventMapper.toEventDomain((eventEntity));
        } catch (InconsistentDomainDataException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public void updateEvent(Event event){
        EventEntity eventEntity = eventMapper.toEventEntity(event);
        eventEntity=getEntityManager().merge(eventEntity);
        persistAndFlush(eventEntity);
    }

    @Transactional
    public void reserveResource(UUID eventId, Resource resource) {
        EventEntity eventEntity = find("id",eventId).firstResult();
        eventEntity.getReservedResources().add(ResourceEntity.builder().id(resource.getId()).build());
        persistAndFlush(eventEntity);
    }
    @Transactional
    public void assignTeacher(UUID eventId, Teacher teacher) {
        EventEntity eventEntity = find("id",eventId).firstResult();
        eventEntity.setTeacher(eventMapper.toTeacherEntity(teacher));
        persistAndFlush(eventEntity);
    }

    public void delete(UUID eventId) {
        delete("id",eventId);
    }
    public boolean exists(UUID eventId){
        return find("id",eventId).count()>0;
    }

    @Transactional
    public void enrollCustomer(UUID eventId, Customer customer) {
        EventEntity eventEntity = find("id",eventId).firstResult();
        eventEntity.getParticipants().add(CustomerEntity.builder().id(customer.getId()).build());
        persistAndFlush(eventEntity);
    }
    @Transactional
    public List<Event> getEnrolledFutureEventList(UUID customerId) {
        List<EventEntity> enrolledFutureEventList = streamAll()
                .filter(eventEntity -> eventEntity.getStartDateTime().isAfter(LocalDateTime.now()) && eventEntity.getParticipants().stream()
                        .anyMatch(customerEntity -> customerEntity.getId().equals(customerId)))
                .collect(Collectors.toList());
        return eventMapper.toEventDomainList(enrolledFutureEventList);
    }
}
