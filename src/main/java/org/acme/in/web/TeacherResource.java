package org.acme.in.web;

import org.acme.in.dto.CreateTeacherCommand;
import org.acme.in.dto.UpdateTeacherCommand;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/teachers")
public class TeacherResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeacher(CreateTeacherCommand createTeacherCommand){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeacher(UpdateTeacherCommand updateTeacherQuery){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(){
        //TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
