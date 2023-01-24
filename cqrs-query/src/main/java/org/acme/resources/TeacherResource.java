package org.acme.resources;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Builder;
import lombok.Getter;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.UUID;


@Path("/teachers")
public class TeacherResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @GET
    public Multi<Teacher> getResources() {
        return client.query("SELECT * FROM teacher").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(Teacher::from);
    }
    @Getter
    @Builder
    private static class Teacher{
        private UUID id;
        private String lastname;
        private String firstname;

        public static Teacher from(Row row) {
            return new Teacher.TeacherBuilder()
                    .id(row.getUUID("id"))
                    .lastname(row.getString("teacher_firstname"))
                    .firstname(row.getString("teacher_lastname"))
                    .build();
        }
    }
}
