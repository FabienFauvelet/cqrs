package org.acme.resources;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Builder;
import lombok.Getter;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.UUID;


@Path("/resources")
public class ResourceResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @GET
    public Multi<Resource> getResources() {
        return client.query("SELECT * FROM resource").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(Resource::from);
    }
    @Getter
    @Builder
    private static class Resource{
        private UUID id;
        private String name;

        public static Resource from(Row row) {
            return new Resource.ResourceBuilder()
                    .id(row.getUUID("id"))
                    .name(row.getString("name"))
                    .build();
        }
    }
}
