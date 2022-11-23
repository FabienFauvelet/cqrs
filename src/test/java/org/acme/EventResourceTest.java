package org.acme;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.domain.model.Resource;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.acme.in.dto.CreateEventQuery;
import org.acme.in.dto.UpdateEventQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@QuarkusTest
public class EventResourceTest {
    @Test
    public void testAddEvent() {
        CreateEventQuery createEventDTO = new CreateEventQuery();
        createEventDTO.setStartDateTime(LocalDateTime.now());
        createEventDTO.setEndDateTime(LocalDateTime.of(createEventDTO.getStartDateTime().toLocalDate(),createEventDTO.getStartDateTime().toLocalTime().plusHours(2)));
        createEventDTO.setTeacherId(UUID.randomUUID());

        createEventDTO.setReservedResources(new ArrayList<UUID>(){{add(UUID.randomUUID());add(UUID.randomUUID());}});
        createEventDTO.setNbMaxParticipant(9);
        given()
          .when().contentType(ContentType.JSON)
          .body(createEventDTO)
          .post("/event")
          .then()
             .statusCode(201)
             .body(is(""));
    }
    @Test
    public void testDeleteEvent() {
        given()
          .when().delete("/event/ce5df902-eb43-4909-b5ac-ed85b08739a2")
          .then()
             .statusCode(204)
             .body(is(""));
    }
    @Test
    public void testUpdateEvent() {
        UpdateEventQuery updateEventDTO = new UpdateEventQuery();
        updateEventDTO.setNbMaxParticipant(9);
        given()
          
          .when().contentType(ContentType.JSON)
          .body(updateEventDTO)
          .put("/event")
          .then()
             .statusCode(200)
             .body(is(""));
    }
}
