package qa.quru.reqresIn;

import qa.quru.models.ErrorResponse;
import qa.quru.models.RegistrationBody;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.quru.models.RegistrationResponse;

import static io.restassured.RestAssured.given;
import static qa.quru.specs.RegistrationSpecs.*;

public class RegistrationTests {
    @Test
    public void registrationSuccessfulTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setEmail("eve.holt@reqres.in");
        registration.setPassword("123");

        RegistrationResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .extract()
                .as(RegistrationResponse.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo(4);
    }

    @Test
    public void negativeMissingPasswordRegistrationTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setEmail("eve.holt@reqres.in");

        ErrorResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    public void negativeMissingEmailRegistrationTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setPassword("123");

        ErrorResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }

    @Test
    public void negativeWrongEmailRegistrationTest() {
        RegistrationBody registration = new RegistrationBody();
        registration.setEmail("test@test.io");
        registration.setPassword("123");

        ErrorResponse response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Note: Only defined users succeed registration");
    }
}
