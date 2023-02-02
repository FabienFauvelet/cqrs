package org.acme.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Builder;
import lombok.Getter;
import org.jboss.resteasy.reactive.RestQuery;
import org.acme.models.CourseElement;
import org.acme.services.CourseSlotService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Path("/courses")
public class CourseResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @GET
    public Multi<Course> getCourses() {
        return client.query("SELECT * FROM course ORDER BY startdatetime DESC").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(Course::from);
    }
    @GET
    @Path("/overlapping")
    public Multi<Course> getOverlappingCourses(@RestQuery("start") LocalDateTime startDate, @RestQuery("end") LocalDateTime endDate) {
        String sqlDateTimeFormat="YYYYMMDD\"T\"HH24MISS";
        return client.query("SELECT *  FROM course" +
                        "WHERE NOT(" +
                        "to_timestamp('"+ endDate.format(DateTimeFormatter.ISO_DATE_TIME) +"','"+sqlDateTimeFormat+"') < startdatetime \n" +
                        "OR to_timestamp('"+ startDate.format(DateTimeFormatter.ISO_DATE_TIME) +"','"+sqlDateTimeFormat+"') > enddatetime\n" +
                        ")").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(Course::from);
    }
    @Inject
    CourseSlotService courseService;

    @GET
    @Path("/getAll")
    public String getCoursesFromMongo()
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
