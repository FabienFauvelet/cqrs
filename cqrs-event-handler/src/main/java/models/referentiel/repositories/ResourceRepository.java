package models.referentiel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Resource;
import models.referentiel.entities.ResourceEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class ResourceRepository implements PanacheRepository<ResourceEntity>
{
    @Transactional
    public void createResource(ResourceEntity resource)
    {
        persistAndFlush(resource);
    }

    @Transactional
    public void deleteResource(UUID resourceId)
    {
        delete("id", resourceId);
    }
}
