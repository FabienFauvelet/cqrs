package org.acme.resources;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Builder;
import lombok.Getter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Path("/courses")
public class CourseResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @GET
    public Multi<Course> getCustomers() {
        return client.query("SELECT * FROM course ORDER BY startdatetime DESC").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(Course::from);
    }

    @Getter
    @Builder
    private static class Course{
        private UUID id;
        private String type;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int nbMaxParticipant;

        public static Course from(Row row) {
            return new CourseBuilder()
                    .id(row.getUUID("id"))
                    .type(row.getString("type"))
                    .startDateTime(row.getLocalDateTime("startdatetime"))
                    .endDateTime(row.getLocalDateTime("enddatetime"))
                    .nbMaxParticipant(row.getInteger("nbmaxparticipant"))
                    .build();
        }
    }
}
