package org.acme.in.web;

import org.acme.domain.services.CustomerService;
import org.acme.in.dto.customer.CreateCustomerCommand;
import org.acme.in.dto.customer.UpdateCustomerCommand;
import org.acme.in.mapper.CustomerInMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Path("/customers")
public class CustomerResource {
    @Inject
    CustomerService customerService;
    @Inject
    CustomerInMapper mapper;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(CreateCustomerCommand createCustomerCommand){
        customerService.addCustomer(mapper.toCustomer(createCustomerCommand));
        return Response.created(null).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(UpdateCustomerCommand updateCustomerCommand){
        customerService.updateCustomer(mapper.toCustomer(updateCustomerCommand));
        return Response.ok().build();
    }

    @DELETE
    @Path("/{customerId}")
    public void deleteCustomer(@PathParam("customerId") UUID customerId){
        customerService.deleteCustomer(customerId);
    }
}
