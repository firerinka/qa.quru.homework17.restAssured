package qa.quru.reqresIn;

import qa.quru.models.ErrorResponsePojoModel;
import qa.quru.models.RegistrationBodyPojoModel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.quru.models.RegistrationResponsePojoModel;

import static io.restassured.RestAssured.given;
import static qa.quru.specs.RegistrationSpecs.*;

public class RegistrationTests {
    @Test
    public void registrationSuccessfulTest() {
        RegistrationBodyPojoModel registration = new RegistrationBodyPojoModel();
        registration
                .setEmail("eve.holt@reqres.in")
                .setPassword("123");

        RegistrationResponsePojoModel response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .extract()
                .as(RegistrationResponsePojoModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo(4);
    }

    @Test
    public void negativeMissingPasswordRegistrationTest() {
        RegistrationBodyPojoModel registration = new RegistrationBodyPojoModel();
        registration
                .setEmail("eve.holt@reqres.in");

        ErrorResponsePojoModel response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponsePojoModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    public void negativeMissingEmailRegistrationTest() {
        RegistrationBodyPojoModel registration = new RegistrationBodyPojoModel();
        registration
                .setPassword("123");

        ErrorResponsePojoModel response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponsePojoModel.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }

    @Test
    public void negativeWrongEmailRegistrationTest() {
        RegistrationBodyPojoModel registration = new RegistrationBodyPojoModel();
        registration
                .setEmail("test@test.io")
                .setPassword("123");

        ErrorResponsePojoModel response = given()
                .spec(registrationRequestSpec)
                .body(registration)
                .when()
                .post()
                .then()
                .spec(errorResponseSpec)
                .extract()
                .as(ErrorResponsePojoModel.class);

        assertThat(response.getError()).isEqualTo("Note: Only defined users succeed registration");
    }
}
