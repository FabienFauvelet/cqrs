package org.acme.out.postgres.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Resource;
import org.acme.out.mapper.ResourceMapper;
import org.acme.out.postgres.entity.ResourceEntity;
import org.acme.out.postgres.entity.ResourceEntity;
import org.acme.out.postgres.entity.ResourceEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class ResourceRepository implements PanacheRepository<ResourceEntity> {
    @Inject
    ResourceMapper resourceMapper;
    @Transactional
    public Resource createResource(Resource resource){
        ResourceEntity resourceEntity = resourceMapper.toResourceEntity(resource);
        persist(resourceEntity);
        return resourceMapper.toDomainResource(resourceEntity);
    }
    @Transactional
    public void updateResource(Resource resource){
        ResourceEntity resourceEntity = resourceMapper.toResourceEntity(resource);
        persistAndFlush(resourceEntity);
    }
    public void delete(UUID resourceId) {
        delete("id",resourceId);
    }
    public boolean exists(UUID id) {
        return count("id",id) > 0;
    }
}
