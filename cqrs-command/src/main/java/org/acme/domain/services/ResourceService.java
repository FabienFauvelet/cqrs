package org.acme.domain.services;

import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.EventRepository;
import org.acme.out.postgres.repository.ResourceRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ResourceService {
    @Inject Publisher publisher;
    @Inject ResourceRepository resourceRepository;
    @Inject
    EventRepository eventRepository;
    public Resource addResource(Resource resource){
        resource = resourceRepository.createResource(resource);
        publisher.publishResourceCreation(resource);
        return resource;
    }
    public void updateResource(Resource resource){
        resourceRepository.updateResource(resource);
        publisher.publishResourceUpdate(resource);
    }
    public void deleteResource(UUID resourceId){
        if(resourceRepository.exists(resourceId)){
            List<Event> resourceReservationsInFutureEvents = eventRepository.getResourceReservationsInFutureEvents(resourceId);
            resourceReservationsInFutureEvents.forEach(event -> {
                event.getReservedResources().removeIf(resource -> resource.getId().equals(resourceId));
                eventRepository.updateEvent(event);
                publisher.publishResourceRelease(event.getId(), Resource.builder().id(resourceId).build());
            });
            //resourceRepository.delete(resourceId);
            publisher.publishResourceDeletion(resourceId);
        }

    }
}
