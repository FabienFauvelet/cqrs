package org.acme.out.postgres.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.postgres.entity.EventEntity;
import org.acme.out.postgres.entity.ResourceEntity;
import org.acme.out.mapper.EventMapper;

@ApplicationScoped
public class EventRepository implements PanacheRepository<EventEntity>{
    
    @Inject
    EventMapper eventMapper;

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
}
