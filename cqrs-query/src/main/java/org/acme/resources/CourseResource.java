package org.acme.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Builder;
import lombok.Getter;
import org.acme.models.CourseElement;
import org.acme.services.CourseSlotService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Inject
    CourseSlotService courseService;

    @GET
    @Path("/getAll")
    public String getCourses()
    {
        List<CourseElement> courses = courseService.getAllCourses();
        try
        {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(courses);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return "error :/";
        }
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
