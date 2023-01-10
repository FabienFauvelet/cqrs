package org.acme.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.models.CustomerAgendaElement;
import org.acme.services.CustomerSlotsService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers-slots")
public class CustomerSlotsResource
{

    @Inject
    CustomerSlotsService cuslotservice;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomerAgenda(@QueryParam("id") String id, @QueryParam("start") String startDate, @QueryParam("end") String endDate)
    {
        List<CustomerAgendaElement> slotList = cuslotservice.getAgenda(id, startDate, endDate);

        try
        {
            System.out.println(new ObjectMapper().findAndRegisterModules().writeValueAsString(slotList));
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(slotList);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return "TH15154N3RR0R";
        }
    }
}
