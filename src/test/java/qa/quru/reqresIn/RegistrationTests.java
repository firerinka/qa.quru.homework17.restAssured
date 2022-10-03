package qa.quru.reqresIn;

import qa.quru.domain.Registration;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RegistrationTests {
    String baseUrl = "https://reqres.in/api/register";

    @Test
    public void registrationSuccessfulTest() {
        Registration registration = new Registration();
        registration
                .setEmail("eve.holt@reqres.in")
                .setPassword("123");

        given()
                .contentType(JSON)
                .body(registration)
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("token"));
    }

    @Test
    public void negativeMissingPasswordRegistrationTest() {
        Registration registration = new Registration();
        registration
                .setEmail("eve.holt@reqres.in");

        given()
                .contentType(JSON)
                .body(registration)
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void negativeMissingEmailRegistrationTest() {
        Registration registration = new Registration();
        registration
                .setPassword("123");

        given()
                .contentType(JSON)
                .body(registration)
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    public void negativeWrongEmailRegistrationTest() {
        Registration registration = new Registration();
        registration
                .setEmail("test@test.io")
                .setPassword("123");

        given()
                .contentType(JSON)
                .body(registration)
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }
}
