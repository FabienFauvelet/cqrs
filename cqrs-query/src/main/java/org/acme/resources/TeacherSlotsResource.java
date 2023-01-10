package org.acme.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.models.TeacherAgendaElement;
import org.acme.services.TeacherSlotsService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/teacher-slots")
public class TeacherSlotsResource
{

    @Inject
    TeacherSlotsService teacherSlotsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTeacherAgenda(@QueryParam("id") String id, @QueryParam("start") String startDate, @QueryParam("end") String endDate)
    {
        List<TeacherAgendaElement> slotList = teacherSlotsService.getAgenda(id,startDate,endDate);

        try
        {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(slotList);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return "TH15154N3RR0R";
        }
    }
}
