package org.acme.in.web;

import org.acme.domain.services.TeacherService;
import org.acme.in.dto.teacher.CreateTeacherCommand;
import org.acme.in.dto.teacher.UpdateTeacherCommand;
import org.acme.in.mapper.TeacherInMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/teachers")
public class TeacherResource {
    @Inject
    TeacherInMapper teacherInMapper;
    @Inject
    TeacherService teacherService;
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeacher(CreateTeacherCommand createTeacherCommand){
        teacherService.addTeacher(teacherInMapper.toDomainTeacher(createTeacherCommand));
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeacher(UpdateTeacherCommand updateTeacherQuery){
        teacherService.updateTeacher(teacherInMapper.toDomainTeacher(updateTeacherQuery));
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{teacherId}")
    public Response deleteTeacher(@PathParam("teacherId") UUID teacherId){
        teacherService.deleteTeacher(teacherId);
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
