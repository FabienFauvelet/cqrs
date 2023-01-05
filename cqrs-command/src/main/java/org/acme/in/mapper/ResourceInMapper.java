package org.acme.in.mapper;

import org.acme.domain.model.Resource;
import org.acme.in.dto.resource.CreateResourceCommand;
import org.acme.in.dto.resource.UpdateResourceCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;
@Mapper(componentModel = "cdi")
public interface ResourceInMapper {

    Resource toDomainResource(CreateResourceCommand createResourceCommand);

    Resource toDomainResource(UpdateResourceCommand updateResourceCommand);
}
