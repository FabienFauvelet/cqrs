package org.acme.in.web;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/enrolments")
public class EnrolmentResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enroll(){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cancelEnrolment(){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
