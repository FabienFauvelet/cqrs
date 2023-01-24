package org.acme.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.models.ResourceAgendaElement;
import org.acme.services.ResourceSlotsService;
import org.jboss.resteasy.reactive.RestQuery;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/resources-slots")
public class ResourceSlotsResource
{

    @Inject ResourceSlotsService resourceSlotsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getResourceAgenda(@RestQuery("id") String id, @RestQuery("start") String startDate, @RestQuery("end") String endDate)
    {
        List<ResourceAgendaElement> res = resourceSlotsService.getAgenda(id,startDate,endDate);

        try
        {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(res);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return "TH15154N3RR0R";
        }
    }
}
