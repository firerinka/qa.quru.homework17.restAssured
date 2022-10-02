package qa.quru.reqresIn;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import qa.quru.domain.Registration;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RegistrationTests {
    ObjectMapper mapper = new ObjectMapper();
    String baseUrl = "https://reqres.in/api/register";

    @Test
    public void registrationSuccessfulTest() {
        Registration registration = new Registration();
        registration
                .setEmail("eve.holt@reqres.in")
                .setPassword("123");

        JsonNode node = mapper.valueToTree(registration);

        given()
                .contentType(JSON)
                .body(node.toString())
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

        JsonNode node = mapper.valueToTree(registration);

        given()
                .contentType(JSON)
                .body(node.toString())
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));;
    }

    @Test
    public void negativeMissingEmailRegistrationTest() {
        Registration registration = new Registration();
        registration
                .setPassword("123");

        JsonNode node = mapper.valueToTree(registration);

        given()
                .contentType(JSON)
                .body(node.toString())
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));;
    }

    @Test
    public void negativeWrongEmailRegistrationTest() {
        Registration registration = new Registration();
        registration
                .setEmail("test@test.io")
                .setPassword("123");

        JsonNode node = mapper.valueToTree(registration);

        given()
                .contentType(JSON)
                .body(node.toString())
                .log().body()
                .when()
                .post(baseUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));;
    }
}
