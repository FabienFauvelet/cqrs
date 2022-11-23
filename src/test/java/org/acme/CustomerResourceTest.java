package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.in.dto.CreateCustomerQuery;
import org.acme.in.dto.UpdateCustomerQuery;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CustomerResourceTest {
    @Test
    public void testAddCustomer() {
        CreateCustomerQuery createCustomerQuery = new CreateCustomerQuery();
        createCustomerQuery.setFirstName("Joe");
        createCustomerQuery.setLastName("Average");
        given()
                .when().contentType(ContentType.JSON)
                .body(createCustomerQuery)
                .post("/customer")
                .then()
                .statusCode(201)
                .body(is(""));
    }
    @Test
    public void testDeleteCustomer() {
        given()
                .when().delete("/customer/ce5df902-eb43-4909-b5ac-ed85b08739a2")
                .then()
                .statusCode(204)
                .body(is(""));
    }
    @Test
    public void testUpdateCustomer() {
        UpdateCustomerQuery updateCustomerQuery = new UpdateCustomerQuery();
        given()
                .when().contentType(ContentType.JSON)
                .body(updateCustomerQuery)
                .put("/customer")
                .then()
                .statusCode(200)
                .body(is(""));
    }
}
