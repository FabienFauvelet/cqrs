package org.acme.out.mapper;

import org.acme.domain.model.Resource;
import org.acme.out.postgres.entity.ResourceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ResourceMapper {
    ResourceEntity toResourceEntity(Resource resource);

    Resource toDomainResource(ResourceEntity resourceEntity);
}
