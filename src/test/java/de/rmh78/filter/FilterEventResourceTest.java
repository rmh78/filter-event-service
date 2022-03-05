package de.rmh78.filter;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
public class FilterEventResourceTest {
    @Test
    public void testListAll() {
        Response response = given()
                .when()
                .get("/filter-events")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("name")).containsExactlyInAnyOrder("test-name-1", "test-name-2");
    }

    @Test
    public void testCreateFilterEvent() {
        // create a new filter-event
        given()
                .when()
                .body("{\"name\" : \"my-filter-event\", \"message\" : \"created by unit-test\"}")
                .contentType("application/json")
                .post("/filter-events")
                .then()
                .statusCode(201)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"my-filter-event\""));

        // list all filter-events and look for new created
        Response response = given()
                .when()
                .get("/filter-events")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getList("name"))
                .containsExactlyInAnyOrder("test-name-1", "test-name-2", "my-filter-event");
    }
}
