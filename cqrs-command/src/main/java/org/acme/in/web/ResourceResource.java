package org.acme.in.web;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resources")
public class ResourceResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createResource(){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateResource(){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteResource(){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
