package org.acme;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.in.dto.CreateCustomerCommand;
import org.acme.in.dto.UpdateCustomerCommand;
import org.acme.out.messages.Publisher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CustomerResourceTest {
    @Inject Publisher publisher;
    @BeforeAll
    public static void setup() {
        Publisher publisher = Mockito.mock(Publisher.class);
        QuarkusMock.installMockForType(publisher, Publisher.class);
    }
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
                .when().delete("/customers/c9c5b7e3-cc42-4b03-bbae-073e5ce2a954")
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
