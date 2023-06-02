package guru.qa.tests;

import guru.qa.models.lombok.*;
import org.junit.jupiter.api.Test;

import static guru.qa.specs.JsonSchemeSpecs.jsonSchemeRequestSpec;
import static guru.qa.specs.JsonSchemeSpecs.jsonSchemeResponseSpec;
import static guru.qa.specs.RegistrationSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ReqresInExtendedTests extends TestBase {

    @Test
    void successfulUserRegistrationWithSpecsTest() {

        Integer userId = 4;
        String userToken = "QpwL5tke4Pnpja7X4";
        RegistrationBodyLombokModel requestBody = new RegistrationBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponseLombokModel response = step("Make request", () ->
                 given()
                .spec(RequestSpec)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .spec(registrationResponseSpec)
                .extract().as(RegistrationResponseLombokModel.class));

        step("Check response");
        assertEquals(userToken, response.getToken());
        assertEquals(userId, response.getId());
    }

    @Test
    void successfulCheckJsonSchemeTest() {

        JsonSchemeBodyLombokModel requestBody = new JsonSchemeBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");
        String pathToScheme = "schemes/user-registration-response-scheme.json";

        step("Make request", () ->
        given()
                .spec(jsonSchemeRequestSpec)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .spec(jsonSchemeResponseSpec)
                .extract());

        step("Check JSON scheme");
        assertThat(matchesJsonSchemaInClasspath(pathToScheme));
    }

    @Test
    void unsuccessfulUserRegistrationWithSpecsTest() {

        String errorMessage = "Missing password";
        RegistrationBodyLombokModel requestBody = new RegistrationBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");

        ErrorRegistrationResponseLombokModel response = step("Make request", () ->
                given()
                        .spec(RequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorRegistrationResponseSpec)
                        .extract().as(ErrorRegistrationResponseLombokModel.class));

        step("Check response");
        assertEquals(errorMessage, response.getError());
    }
    @Test
    void successfulGetUserIdTest() {

        Integer userId = 2;

        UserIdResponseLombokModel response = step("Make request", () ->
                given()
                .spec(UserIdRequestSpec)
                .when()
                .get("/user/2")
                .then()
                .spec(UserIdResponse)
                .extract().as(UserIdResponseLombokModel.class));

        step("Check response");
        assertEquals(userId, response.getData().getId());
    }
    @Test
    void successfulGetListOfUsersIdTest() {

        Integer[] userId = {7, 8, 9, 10, 11, 12};

        UsersIdResponseLombokModel response = step("Make request", () ->
                given()
                .spec(UserIdRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(UserIdResponse)
                .extract().as(UsersIdResponseLombokModel.class));

        step("Check response");
        assertThat(response.getData().contains(userId));
    }

    @Test
    void successfulUpdateUserTest() {
        String userName = "morpheus";
        String userJob = "zion resident";
        UpdateUserBodyLombokModel requestBody = new UpdateUserBodyLombokModel();
        requestBody.setName("morpheus");
        requestBody.setJob("zion resident");

            UpdateUserResponseLombokModel response = step("Make request", () ->
                given()
                .spec(RequestSpec)
                .body(requestBody)
                .when()
                .put("/user/2")
                .then()
                .spec(UpdateUserResponse)
                .extract().as(UpdateUserResponseLombokModel.class));

        step("Check name in response");
        assertEquals(userName, response.getName());

        step("Check job in response");
        assertEquals(userJob, response.getJob());
    }
}
