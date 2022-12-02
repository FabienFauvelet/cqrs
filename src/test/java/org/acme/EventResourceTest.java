package org.acme;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.acme.domain.model.Event;
import org.acme.in.dto.CreateEventCommand;
import org.acme.in.dto.UpdateEventCommand;
import org.acme.in.mapper.EventInMapper;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.EventRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.acme.TestUtils.getValue;
import static org.hamcrest.CoreMatchers.is;

import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;


@QuarkusTest
public class EventResourceTest {
    @Inject EventRepository eventRepository;
    @Inject Publisher publisher;
    @Inject EventInMapper eventInMapper;
    @BeforeAll
    public static void setup(){
        EventRepository eventRepository = Mockito.mock(EventRepository.class);
        Mockito.when(eventRepository.createEvent(Event.builder().build()))
                .thenReturn(Event.builder().build());

        Mockito.when(eventRepository.createEvent(Mockito.any(Event.class)))
                .thenReturn(Event.builder().id(UUID.fromString("874345e1-fb34-4524-8e25-11b118235fca")).build());
        QuarkusMock.installMockForType(eventRepository, EventRepository.class);

        Publisher publisher = Mockito.mock(Publisher.class);
        QuarkusMock.installMockForType(publisher, Publisher.class);
    }
    @Test
    public void testAddEvent() throws IOException, ClassNotFoundException {

        CreateEventCommand createEventCommand = getValue("/json/event/create-event-command.json", CreateEventCommand.class);
        given()
          .when().contentType(ContentType.JSON)
          .body(createEventCommand)
          .post("/events")
          .then()
             .statusCode(201)
             .body(is(""));
    }
    @Test
    public void testAddEmptyEvent() throws IOException, ClassNotFoundException {
        CreateEventCommand createEventCommand = getValue("/json/event/create-empty-event-command.json", CreateEventCommand.class);
        given()
                .when().contentType(ContentType.JSON)
                .body(createEventCommand)
                .post("/events")
                .then()
                .statusCode(201)
                .body(is(""));
    }

    @Test
    public void testDeleteEvent() {
        given()
          .when().delete("/events/ce5df902-eb43-4909-b5ac-ed85b08739a2")
          .then()
             .statusCode(204)
             .body(is(""));
    }
    @Test
    public void testUpdateEvent() throws IOException, ClassNotFoundException {
        UpdateEventCommand updateEventDTO = getValue("/json/event/update-event-command.json", UpdateEventCommand.class);
        Mockito.when(eventRepository.exists(updateEventDTO.getId()))
                .thenReturn(true);
        Mockito.when(eventRepository.getEvent(updateEventDTO.getId()))
                .thenReturn(getValue("/json/event/update-event-old-event.json",Event.class));

        given()
          .when().contentType(ContentType.JSON)
          .body(updateEventDTO)
          .put("/events")
          .then()
             .statusCode(200)
             .body(is(""));
    }
}
