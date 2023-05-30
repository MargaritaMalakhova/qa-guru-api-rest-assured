package guru.qa;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class ReqresInTests {

    @Test
    void successfulUserRegistrationTest() {

        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        Integer userId = 4;
        String userToken = "QpwL5tke4Pnpja7X4";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(userId))
                .body("token", is(userToken));
    }

    @Test
    void successfulCheckJsonSchemeTest() {

        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemes/user-registration-response-scheme.json"));
    }

    @Test
    void unsuccessfulUserRegistrationTest() {

        String requestBody = "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}";
        String errorMessage = "Missing password";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is(errorMessage));
    }

    @Test
    void successfulGetUserIdTest() {

        Integer userId = 2;

        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/user/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(userId));
    }

    @Test
    void successfulUpdateUserTest() {

        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        String userName = "morpheus";
        String userJob = "zion resident";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .put("https://reqres.in/api/user/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is(userName))
                .body("job ", is(userJob));
    }
}
