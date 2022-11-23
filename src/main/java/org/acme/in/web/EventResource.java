package org.acme.in.web;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.bytebuddy.implementation.bytecode.Throw;
import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.services.EventService;
import org.acme.in.dto.CreateEventQuery;
import org.acme.in.dto.UpdateEventQuery;
import org.acme.in.mapper.EventInMapper;


@Path("/event")
public class EventResource {

    @Inject
    EventService eventService;
    @Inject
    EventInMapper eventInMapper;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvent(CreateEventQuery createEventQuery) throws InconsistentDomainDataException{
        eventService.addEvent(eventInMapper.toEvent(createEventQuery));
        return Response.created(null).build();
    }

    @DELETE
    @Path("/{eventId}")
    public void deleteEvent(@PathParam("eventId") UUID eventId){

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEvent(UpdateEventQuery updateEventQuery){
        return Response.ok().build();
    }
}
