package guru.qa.tests;

import guru.qa.models.lombok.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static guru.qa.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ReqresInExtendedTests extends TestBase {

    String email = "eve.holt@reqres.in";
    String password = "pistol";
    String userName = "morpheus";
    String userJob = "zion resident";
    String errorMessage = "Missing password";


    @Test
    void successfulUserRegistrationWithSpecsTest() {

        Integer userId = 4;
        String userToken = "QpwL5tke4Pnpja7X4";
        UserRegistration requestBody = new UserRegistration();
        requestBody.setEmail(email);
        requestBody.setPassword(password);

        UserRegistrationResponse response = step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(userRegistrationResponseSpec)
                        .extract().as(UserRegistrationResponse.class));

        step("Check response", () ->
        {
            assertEquals(userToken, response.getToken());
            assertEquals(userId, response.getId());
        });
    }

    @Test
    void successfulCheckJsonSchemeTest() {

        UserRegistration requestBody = new UserRegistration();
        requestBody.setEmail(email);
        requestBody.setPassword(password);
        String pathToScheme = "schemes/user-registration-response-scheme.json";

        step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(response200Spec)
                        .extract());

        step("Check JSON scheme", () ->
                assertThat(matchesJsonSchemaInClasspath(pathToScheme)));
    }

    @Test
    void unsuccessfulUserRegistrationWithSpecsTest() {

        UserRegistration requestBody = new UserRegistration();
        requestBody.setEmail(email);

        ErrorResponse response = step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(ErrorResponse.class));

        step("Check response", () ->
                assertEquals(errorMessage, response.getError()));
    }

    @Test
    void successfulGetUserIdTest() {

        Integer userId = 2;

        UserIdCheckResponse response = step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/user/2")
                        .then()
                        .spec(userIdCheckResponseSpec)
                        .extract().as(UserIdCheckResponse.class));

        step("Check response", () ->
                assertEquals(userId, response.getData().getId()));
    }

    @Test
    void successfulGetListOfUsersIdTest() {

        Integer[] userId = {7, 8, 9, 10, 11, 12};

        UsersIdCheckResponse response = step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(userIdCheckResponseSpec)
                        .extract().as(UsersIdCheckResponse.class));

        HashSet<Integer> idsFromResponse = new HashSet<>();
        LinkedList<User> usersFromResponse = response.getData();
        //for each user in list loop
        for (User user : usersFromResponse) {
            idsFromResponse.add(user.getId()); //get id
        }

        //2. check each element of array contains in hashset

        step("Check response", () -> {
            for (int id : userId) {
                assertTrue(idsFromResponse.contains(id));
            }
            assertEquals(idsFromResponse.size(), userId.length);
        });
    }

    @Test
    void successfulUpdateUserTest() {

        UserUpdate requestBody = new UserUpdate();
        requestBody.setName(userName);
        requestBody.setJob(userJob);

        UserUpdateResponse response = step("Make request", () ->
                given()
                        .spec(requestSpec)
                        .body(requestBody)
                        .when()
                        .put("/user/2")
                        .then()
                        .spec(userUpdateResponseSpec)
                        .extract().as(UserUpdateResponse.class));

        step("Check name in response", () ->
                assertEquals(userName, response.getName()));

        step("Check job in response", () ->
                assertEquals(userJob, response.getJob()));
    }
}
