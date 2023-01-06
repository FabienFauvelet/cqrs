package org.acme.resources;

import org.acme.services.TeacherSlotsService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/teacher-slots")
public class TeacherSlotsResource
{

    @Inject
    TeacherSlotsService teacherSlotsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTeacherAgenda(@QueryParam("id") String id, @QueryParam("start") String startDate, @QueryParam("end") String endDate)
    {

        return null;
    }
}
