package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.in.dto.CreateCustomerCommand;
import org.acme.in.dto.UpdateCustomerCommand;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CustomerResourceTest {
    @Test
    public void testAddCustomer() throws IOException, ClassNotFoundException {
        CreateCustomerCommand createCustomerCommand = TestUtils.getValue("/json/customer/create-customer-command.json",CreateCustomerCommand.class);
        given()
                .when().contentType(ContentType.JSON)
                .body(createCustomerCommand)
                .post("/customers")
                .then()
                .statusCode(201)
                .body(is(""));
    }
    @Test
    public void testDeleteCustomer() {
        given()
                .when().delete("/customers/ce5df902-eb43-4909-b5ac-ed85b08739a2")
                .then()
                .statusCode(204)
                .body(is(""));
    }
    @Test
    public void testUpdateCustomer() throws IOException, ClassNotFoundException {
        UpdateCustomerCommand updateCustomerCommand = TestUtils.getValue("/json/customer/update-customer-command.json",UpdateCustomerCommand.class);
        given()
                .when().contentType(ContentType.JSON)
                .body(updateCustomerCommand)
                .put("/customers")
                .then()
                .statusCode(200)
                .body(is(""));
    }
}
