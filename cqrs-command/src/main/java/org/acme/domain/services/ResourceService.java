package org.acme.domain.services;

import org.acme.domain.model.Resource;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.ResourceRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class ResourceService {
    @Inject Publisher publisher;
    @Inject ResourceRepository resourceRepository;
    public void addResource(Resource resource){
        resource = resourceRepository.createResource(resource);
        publisher.publishResourceCreation(resource);
    }
    public void updateResource(Resource resource){
        resourceRepository.updateResource(resource);
        publisher.publishResourceUpdate(resource);
    }
    public void deleteResource(UUID resourceId){
        //TODO VERIFICATION DE L'UTILISATION DE LA RESSOURCE
        resourceRepository.delete(resourceId);
        publisher.publishResourceDeletion(resourceId);
    }
}
