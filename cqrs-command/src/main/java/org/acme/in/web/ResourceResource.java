package org.acme.in.web;

import org.acme.domain.services.ResourceService;
import org.acme.in.dto.resource.CreateResourceCommand;
import org.acme.in.dto.resource.UpdateResourceCommand;
import org.acme.in.mapper.ResourceInMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/resources")
public class ResourceResource {
    @Inject
    ResourceService resourceService;
    @Inject
    ResourceInMapper resourceInMapper;
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createResource(CreateResourceCommand createResourceCommand){
        resourceService.addResource(resourceInMapper.toDomainResource(createResourceCommand));
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateResource(UpdateResourceCommand updateResourceCommand){
        resourceService.updateResource(resourceInMapper.toDomainResource(updateResourceCommand));
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{resourceId}")
    public Response deleteResource(@PathParam("resourceId") UUID resourceId){
        resourceService.deleteResource(resourceId);
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
