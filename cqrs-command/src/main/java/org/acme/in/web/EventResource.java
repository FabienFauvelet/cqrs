package org.acme.in.web;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.services.EventService;
import org.acme.in.dto.event.CreateEventCommand;
import org.acme.in.dto.event.UpdateEventCommand;
import org.acme.in.mapper.EventInMapper;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/events")
public class EventResource {

    @Inject
    EventService eventService;
    @Inject
    EventInMapper eventInMapper;

    @POST
    public Response addEvent(CreateEventCommand createEventCommand) throws InconsistentDomainDataException{
        eventService.addEvent(eventInMapper.toEvent(createEventCommand));
        return Response.created(null).build();
    }

    @DELETE
    @Path("/{eventId}")
    public void deleteEvent(@PathParam("eventId") UUID eventId){
        eventService.deleteEvent(eventId);
    }

    @PUT
    public Response updateEvent(UpdateEventCommand updateEventQuery) throws InconsistentDomainDataException {
        eventService.updateEvent(eventInMapper.toEvent(updateEventQuery));
        return Response.ok().build();
    }
}
