package guru.qa.tests;

import guru.qa.models.lombok.RegistrationBodyLombokModel;
import guru.qa.models.lombok.RegistrationResponseLombokModel;
import guru.qa.models.pojo.RegistrationBodyPojoModel;
import guru.qa.models.pojo.RegistrationResponsePojoModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class ReqresInExtendedTests extends TestBase {

    @Test
    void successfulUserRegistrationBadPracticeTest() {

        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        Integer userId = 4;
        String userToken = "QpwL5tke4Pnpja7X4";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(userId))
                .body("token", is(userToken));
    }

    @Test
    void successfulUserRegistrationWithPojoModelsTest() {

        Integer userId = 4;
        String userToken = "QpwL5tke4Pnpja7X4";
        RegistrationBodyPojoModel requestBody = new RegistrationBodyPojoModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponsePojoModel response = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegistrationResponsePojoModel.class);
        assertEquals(userToken, response.getToken());
        assertEquals(userId, response.getId());
    }

    @Test
    void successfulUserRegistrationWithLombokModelsTest() {

        Integer userId = 4;
        String userToken = "QpwL5tke4Pnpja7X4";
        RegistrationBodyLombokModel requestBody = new RegistrationBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponseLombokModel response = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegistrationResponseLombokModel.class);
        assertEquals(userToken, response.getToken());
        assertEquals(userId, response.getId());
    }

}
