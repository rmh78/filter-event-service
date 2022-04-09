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
        assertThat(response.jsonPath().getList("endWeight")).containsExactlyInAnyOrder(140, 145);
    }

    @Test
    public void testCreateFilterEvent() {
        // create a new filter-event
        given()
                .when()
                .body("{\"startWeight\" : 10, \"endWeight\" : 200, \"duration\" : 50, \"ppm\" : 6 }")
                .contentType("application/json")
                .post("/filter-events")
                .then()
                .statusCode(201);

        // list all filter-events and look for new created
        Response response = given()
                .when()
                .get("/filter-events")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getList("endWeight"))
                .containsExactlyInAnyOrder(140, 145, 200);
    }
}
