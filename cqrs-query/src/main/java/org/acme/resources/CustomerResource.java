package org.acme.resources;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Builder;
import lombok.Getter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Path("/customers")
public class CustomerResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @GET
    public Multi<Customer> getCustomers() {
        return client.query("SELECT * FROM customer").execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(Customer::from);
    }

    @Getter
    @Builder
    private static class Customer{
        private UUID id;
        private String lastname;
        private String firstname;
        private LocalDate birthdate;
        private String address;

        public static Customer from(Row row) {
            return new CustomerBuilder()
                    .id(row.getUUID("id"))
                    .lastname(row.getString("last_name"))
                    .firstname(row.getString("first_name"))
                    .birthdate(row.getLocalDate("birthdate"))
                    .address(row.getString("address"))
                    .build();
        }
    }
}
